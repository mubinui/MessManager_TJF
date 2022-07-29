package com.example.messManager.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.example.messManager.Dao.BazarSequenceRepository;
import com.example.messManager.Dao.MealRepository;
import com.example.messManager.entities.BazarSequence;
import com.example.messManager.entities.Manager;
import com.example.messManager.entities.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.messManager.Dao.memberRepository;
import com.example.messManager.entities.Member;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private memberRepository repository;
	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private BazarSequenceRepository bazarSequenceRepository;

	public Member getMember(Principal principal, Model model){
		String nameString = principal.getName();
		Member member =  repository.getUserByUserName(nameString);
		model.addAttribute("member", member);

		return member;
	}

	@RequestMapping("/index")
	public String dasboard(Model model, Principal principal) {

		getMember(principal,model);
		return "member/member_dashboard";
	}

	@RequestMapping("/viewMeal")
	public String viewMeal(Model model, Principal principal) {

		Member member = getMember(principal, model);
		List<Meal> meal = this.mealRepository.getMealByMessName(member.getMessName());
		model.addAttribute("meal", meal);

		return "member/view_meals";
	}

	@RequestMapping("/bazar_dates")
	public String viewBazarDates(Model model, Principal principal) {

		Member member = getMember(principal,model);
		List<BazarSequence> bazarSequences = this.bazarSequenceRepository.getUserByName(member.getName());
		model.addAttribute("bazarSequences", bazarSequences);

		return "member/view_bazar_sequence";
	}

	@RequestMapping("/update_bazar_expences/{id}")
	public String updateSpecificBazarExpencesView(Model model, @PathVariable("id") Integer id, Principal principal) {

		Member member = getMember(principal,model);
		BazarSequence bazarSequence = this.bazarSequenceRepository.getById(id);

		model.addAttribute("title", "Update-Bazaar-Expenses");
		model.addAttribute("bazar", bazarSequence);

		return "member/update_bazar_expenses";
	}

	@RequestMapping(value = "/update_expenses/{id}", method = RequestMethod.POST)
	public String updateBazarExpenses(Principal principal,@PathVariable("id") Integer id, @ModelAttribute("meal")BazarSequence bazarSequence, Model model){

		getMember(principal,model);
		Optional<BazarSequence> bazar = this.bazarSequenceRepository.findById(id);
		BazarSequence singlebazarSequence = bazar.get();

		singlebazarSequence.setExpenses(bazarSequence.getExpenses());
		singlebazarSequence.setIsDone("done");
		this.bazarSequenceRepository.save(singlebazarSequence);


		return "member/member_dashboard";
	}

}
