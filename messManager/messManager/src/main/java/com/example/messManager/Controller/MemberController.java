package com.example.messManager.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.messManager.Dao.memberRepository;
import com.example.messManager.entities.Member;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private memberRepository repository;
	
	@RequestMapping("/index")
	public String dasboard(Model model, Principal principal) {
		
		String nameString = principal.getName();
		
		Member member = repository.getUserByUserName(nameString);
		
		model.addAttribute("manager", member);
		
		return "member/member_dashboard";
	}
}
