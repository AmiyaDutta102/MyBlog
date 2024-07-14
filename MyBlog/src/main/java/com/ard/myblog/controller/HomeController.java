package com.ard.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ard.myblog.model.Category;
import com.ard.myblog.service.CategoryService;
import com.ard.myblog.service.PostService;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService catService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/home")
	public String home(Model model) {
		
		//List all categories
		List<Category> cats= catService.findAll();
		model.addAttribute("categories", cats);
		
		return "home";
	}
}
