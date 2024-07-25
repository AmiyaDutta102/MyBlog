package com.ard.myblog.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String home(Model model,
			@RequestParam(required=false, name="sort_by", defaultValue="createdAt") String sort_by,
			@RequestParam(required=false, name="per_page", defaultValue="2") String per_page,
			@RequestParam(required=false, name="page", defaultValue="1") String page) {
		
		//List all categories
		List<Category> cats= catService.findAll();
		
		model.addAttribute("categories", cats);
		
		Page<Post> posts = postService.findAll(Integer.parseInt(page)-1,Integer.parseInt(per_page),sort_by);
		
		Optional<Post> posts_1 = posts.stream().findFirst();
		if(posts_1.isPresent()) {
			model.addAttribute("post_head", posts_1.get());
		}
		
		Optional<Post> posts_2=posts.stream()
				.filter(p -> p.getCategory().getId()==1)
				.findFirst();
		if(posts_2.isPresent()) {
			model.addAttribute("post_cat1", posts_2.get());
		}
		else
			model.addAttribute("post_cat1", posts_1.get());
		
		Optional<Post> posts_3=posts.stream()
				.filter(p -> p.getCategory().getId()==2)
				.findFirst();
		if(posts_3.isPresent()) {
			model.addAttribute("post_cat2", posts_3.get());
		}
		else
			model.addAttribute("post_cat2", posts_1.get());
		
		int total_pages=posts.getTotalPages();
		List<Integer> pages=new ArrayList<>();
		if(total_pages>0) {
			pages=IntStream.rangeClosed(0, total_pages-1).boxed().collect(Collectors.toList());
		}
		List<String> links=new ArrayList<>();
		if(pages!=null) {
			for(int pno:pages) {
				String active="";
				if(pno==posts.getNumber()) {
					active="active";
				}
				String _temp_link = "home?per_page="+per_page+"&page="+(pno+1)+"&sort_by="+sort_by;
                links.add("<li class=\"page-item "+active+"\"><a href=\""+_temp_link+"\" class='page-link'>"+(pno+1)+"</a></li>");
			}
			model.addAttribute("links", links);
		}
		model.addAttribute("posts", posts);
		
		return "home";
	}
}
