package com.ard.myblog.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ard.myblog.model.Account;
import com.ard.myblog.model.Post;
import com.ard.myblog.service.AccountService;
import com.ard.myblog.service.PostService;

import jakarta.validation.Valid;

@Controller
public class PostController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {
        Optional<Post> optionalPost = postService.getById(id);
        String authUser = "email";
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            if (principal != null) {
                authUser = principal.getName();
            }
            if (authUser.equals(post.getAccount().getEmail())){
                model.addAttribute("isOwner", true);
            }else{
                model.addAttribute("isOwner", false);
            }

            return "post";
        } else {
            return "404";
        }
    }

	@GetMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
	public String post_add(Model model, Principal principal) {
		String userid = "email";
		if(principal != null){
		   userid = principal.getName();
		}
	   
		Optional<Account> optionalAccount = accountService.findByEmail(userid);
		if(optionalAccount.isPresent()) {
			Post post=new Post();
			 post.setAccount(optionalAccount.get());

			model.addAttribute("post",post);
			//model.addAttribute("account",account);
		}
		else {
			 return "redirect:/home";

		}
		
		return "post_add";
	}
	
	@PostMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@Valid @ModelAttribute Post post, BindingResult bindingResult ,Principal principal){

        if (bindingResult.hasErrors()){
            return "post_add";
        }
        String authUser = "email";
        if(principal != null){
            authUser = principal.getName();
        }
        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0){
            return "redirect:/?error";
        }
        postService.save(post);
        return "redirect:/home";
    }

}
