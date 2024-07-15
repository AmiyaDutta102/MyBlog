package com.ard.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ard.myblog.model.Account;
import com.ard.myblog.service.AccountService;

import jakarta.validation.Valid;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/login")
	public String login(Model model) {
		
		return "login";
	}
	
	@GetMapping("/registration")
	public String registration(Model model) {
		
		Account account=new Account();
		model.addAttribute("account", account);
		
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registrationPost(@Valid @ModelAttribute Account account, BindingResult result) {
		if(result.hasErrors()) {
			return "registration?error";
		}
		accountService.save(account);
		
		return "redirect:/registration?success";
	}

}
