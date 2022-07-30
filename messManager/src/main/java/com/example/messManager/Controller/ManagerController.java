package com.example.messManager.Controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.messManager.Dao.BazarSequenceRepository;
import com.example.messManager.Dao.MealRepository;
import com.example.messManager.entities.BazarSequence;
import com.example.messManager.entities.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
	private MealRepository mealRepository;

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
			Member Db_member =  memberRepository.getUserByUserName(member.getEmail());
			if (Objects.equals(Db_member.getEmail(), member.getEmail())){
				System.out.println("check");
				session.setAttribute("message",  new messages("something went wrong!! Email already existed.. " + e.getMessage(), "alert-danger"));
				return "manager/add_member";
			}else {
				e.printStackTrace();
				model.addAttribute("member", member);
				session.setAttribute("message", new messages("something went wrong!! " + e.getMessage(), "alert-danger"));
				return "manager/add_member";
			}
		}
	}

	@RequestMapping(value = "/member_register", method = RequestMethod.POST)
	public String registerManager(@Valid @ModelAttribute("manager") Manager manager, BindingResult result,
							   @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
							   Model model, HttpSession session) {
		try {

			if (!agreement) {
				throw new Exception("agreement is not accepted");
			}
			if (result.hasErrors()) {
				model.addAttribute("manager", manager);
				return "signup";
			}
			else {
				manager.setRole("ROLE_Manager");
				manager.setPassword(passwordEncoder.encode(manager.getPassword()));
				this.repository.save(manager);
				model.addAttribute("manager", new Manager());
				session.setAttribute("message",  new messages("Successfully registered" , "alert-success"));
				return "signup";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("manager", manager);
			session.setAttribute("message",  new messages("something went wrong!! " + e.getMessage(), "alert-danger"));
			return "signup";
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
	public String addBazarSequence(Principal principal,
								   @ModelAttribute("BazarSequence")BazarSequence bazarSequence,
								   Model model, HttpSession session)throws Exception{

		String managerEmail = principal.getName();
		Manager manager = repository.getUserByUserName(managerEmail);
		List<Member> members = this.memberRepository.findMemberByManager(manager.getId());

		String end = bazarSequence.getEndDate();
		String start = bazarSequence.getStartDate();


		Date date_end=new SimpleDateFormat("yyyy-MM-dd").parse(end);
		Date date_start=new SimpleDateFormat("yyyy-MM-dd").parse(start);



		long end_time = date_end.getTime();
		long start_time = date_start.getTime();


		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		if (end_time>start_time){
			for (long i = start_time; i<=end_time; i+=86400000){
				String currentDate = dateFormat.format(i);

				bazarSequence.setMessName(manager.getMessName());
				bazarSequence.setCurrent(currentDate);
				bazarSequence.setIsDone("not done");
				bazarSequence.setId(bazarSequence.getId()+1);
				if (Objects.equals(bazarSequence.getPairMember_one(), bazarSequence.getPairMember_two())){

					session.setAttribute("message",  new messages("2 member can not be same" , "alert-danger"));

				}else {
					this.bazarSequenceRepository.save(bazarSequence);
					session.setAttribute("message",  new messages("successful" , "alert-success"));

				}

			}
		}
		model.addAttribute("title","show-member");
		model.addAttribute("members",members);
		model.addAttribute("BazarSequence",new BazarSequence());

		return "manager/add_bazar_sequence";
	}

	@GetMapping("/add-meal")
	public String addMealsView(Principal principal, Model model){

		model.addAttribute("title","add-meal");
		model.addAttribute("meal",new Meal());

		return "manager/add_meal";
	}

	@PostMapping(value = "/meals_add")
	public String addMeals(Principal principal,@ModelAttribute("meal")Meal meal, Model model){

		String managerEmail = principal.getName();
		Manager manager = repository.getUserByUserName(managerEmail);

		meal.setMessName(manager.getMessName());
		this.mealRepository.save(meal);

		model.addAttribute("title","add-meal");
		model.addAttribute("meal",new Meal());

		return "manager/add_meal";
	}

	@RequestMapping("/view_bazar_sequence")
	public String viewBazarDates(Model model, Principal principal) {

		String managerEmail = principal.getName();
		Manager manager = repository.getUserByUserName(managerEmail);

		List<BazarSequence> bazarSequences = this.bazarSequenceRepository.getBazarSequenceByMess(manager.getMessName());
		model.addAttribute("bazarSequences", bazarSequences);
		model.addAttribute("title","view-bazaar");
		return "manager/view_bazar_sequence";
	}

}
