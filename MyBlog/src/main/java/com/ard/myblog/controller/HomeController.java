package com.ard.myblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ard.myblog.model.Category;
import com.ard.myblog.model.Post;
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
		
		List<Post> posts = postService.findAll();
		
		Optional<Post> posts_1 = posts.stream().findFirst();
		if(posts_1.isPresent()) {
			model.addAttribute("post_head", posts_1.get());
		}
		
		Optional<Post> posts_2=posts.stream()
				.filter(p -> p.getCategory().getId()==1)
				.findFirst();
		
		model.addAttribute("post_cat1", posts_2.get());
		
		Optional<Post> posts_3=posts.stream()
				.filter(p -> p.getCategory().getId()==2)
				.findFirst();
		
		model.addAttribute("post_cat2", posts_3.get());
		model.addAttribute("posts", posts);
		
		return "home";
	}
}
