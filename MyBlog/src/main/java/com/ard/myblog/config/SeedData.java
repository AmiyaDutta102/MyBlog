package com.ard.myblog.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ard.myblog.model.Account;
import com.ard.myblog.model.Category;
import com.ard.myblog.model.Post;
import com.ard.myblog.service.AccountService;
import com.ard.myblog.service.CategoryService;
import com.ard.myblog.service.PostService;

@Component
public class SeedData implements CommandLineRunner{

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService catService;
	
	@Override
	public void run(String... args) throws Exception {

		Account ac1=new Account();
		ac1.setEmail("admin@myblog.in");
		ac1.setPassword("1234");
		ac1.setFirstName("Amiya");
		ac1.setLastName("Dutta");
		ac1.setDateOfBirth(LocalDate.parse("1996-06-03"));
		ac1.setRole("ADMIN");
		accountService.save(ac1);
		
		Account ac2=new Account();
		ac2.setEmail("user1@myblog.in");
		ac2.setPassword("1234");
		ac2.setFirstName("User");
		ac2.setLastName("");
		ac2.setDateOfBirth(LocalDate.parse("1996-06-03"));
		ac2.setRole("USER");
		accountService.save(ac2);
		
		List<Post> posts=postService.findAll();
		if(posts.size()==0) {
			List<Category> cats=catService.findAll();
			if(cats.size()==0) {
				Category cat1=new Category();
				cat1.setTitle("Tech");
				cat1.setDescription("Technology");
				catService.save(cat1);
				
				Category cat2=new Category();
				cat2.setTitle("Health");
				cat2.setDescription("Health");
				catService.save(cat2);
				
				Category cat3=new Category();
				cat3.setTitle("Travel");
				cat3.setDescription("Travel");
				catService.save(cat3);
				
				Post post1= new Post();
				post1.setTitle("Post 1");
				post1.setBody("Post 1 body ...");
				post1.setCategory(cat1);
				post1.setAccount(ac1);
				postService.save(post1);
				
				Post post2= new Post();
				post2.setTitle("Post 2");
				post2.setBody("Post 2 body ...");
				post2.setCategory(cat2);
				post2.setAccount(ac2);
				postService.save(post2);
				
				Post post3= new Post();
				post3.setTitle("Post 3");
				post3.setBody("Post 3 body ...");
				post3.setCategory(cat3);
				post3.setAccount(ac2);
				postService.save(post3);
			}
		}
		
		
	}

}
