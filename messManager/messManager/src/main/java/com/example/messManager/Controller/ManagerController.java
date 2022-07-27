package com.example.messManager.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.messManager.Dao.BazarSequenceRepository;
import com.example.messManager.entities.BazarSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.messManager.Dao.managerRepository;
import com.example.messManager.Dao.memberRepository;
import com.example.messManager.entities.Manager;
import com.example.messManager.entities.Member;
import com.example.messManager.helper.messages;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private managerRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private memberRepository memberRepository;

	@Autowired
	private BazarSequenceRepository bazarSequenceRepository;

	@ModelAttribute
	public void commonData(Model model, Principal principal) {
		String nameString = principal.getName();
		
		Manager manager = repository.getUserByUserName(nameString);
		
		model.addAttribute("manager", manager);
	}
	
	//dashboard handler
	
	@RequestMapping("/index")
	public String dasboard(Model model, Principal principal) {
		
		return "manager/manager_dashboard";
		
	}
	
	//add member view handler
	
	@GetMapping("/add-member")
	public String openAddMember(Model model) {
		
		model.addAttribute("title", "Add Member");
		model.addAttribute("member", new Member());
		
		return "manager/add_member";
	}
	
	@PostMapping(value = "/member_add")
	public String registerMember(@ModelAttribute("member") Member member, BindingResult result, 
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, 
			Model model, HttpSession session, Principal principal) {
		try {
			
			if (!agreement) {
				System.out.println("agreement not accepted!!");
				throw new Exception("agreement is not accepted");
			}
			if (result.hasErrors()) {
				model.addAttribute("member", member);
				return "manager/add_member";
			}
			else {
				member.setRole("ROLE_Member");
				member.setPassword(passwordEncoder.encode(member.getPassword()));
				
				String managerNameString = principal.getName();
				Manager manager = repository.getUserByUserName(managerNameString);
				
				member.setManager(manager);
				member.setMessName(manager.getMessName());
				manager.getMembers().add(member);
				this.repository.save(manager);
				
				model.addAttribute("member", new Member());
				session.setAttribute("message",  new messages("Successfully registered" , "alert-success"));
				return "manager/add_member";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("member", member);
			session.setAttribute("message",  new messages("something went wrong!! " + e.getMessage(), "alert-danger"));
			return "manager/add_member";
		}
	}

	@GetMapping("/view-member")
	public String showMember(Model model, Principal principal){

		String managerEmail = principal.getName();
		Manager manager = repository.getUserByUserName(managerEmail);
		List<Member> members = this.memberRepository.findMemberByManager(manager.getId());

		model.addAttribute("title","show-member");
		model.addAttribute("members",members);

		return "manager/view_member";
	}

	@GetMapping("/add_bazar_sequence")
	public String addBazarSequenceView(Principal principal, Model model){

		String managerEmail = principal.getName();
		Manager manager = repository.getUserByUserName(managerEmail);
		List<Member> members = this.memberRepository.findMemberByManager(manager.getId());

		model.addAttribute("title","show-member");
		model.addAttribute("members",members);
		model.addAttribute("BazarSequence",new BazarSequence());

		return "manager/add_bazar_sequence";
	}

	@PostMapping(value = "/bazar_sequence_add")
	public String addBazarSequence(Principal principal,@ModelAttribute("BazarSequence")BazarSequence bazarSequence, Model model){

		String managerEmail = principal.getName();
		Manager manager = repository.getUserByUserName(managerEmail);
		List<Member> members = this.memberRepository.findMemberByManager(manager.getId());

		bazarSequence.setMessName(manager.getMessName());
		this.bazarSequenceRepository.save(bazarSequence);

		model.addAttribute("title","show-member");
		model.addAttribute("members",members);
		model.addAttribute("BazarSequence",new BazarSequence());

		return "manager/add_bazar_sequence";
	}

}
