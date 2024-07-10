package com.ard.myblog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ard.myblog.model.Post;
import com.ard.myblog.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public Optional<Post> getById(Long id){
		return postRepository.findById(id);
		
	}
	public List<Post> findAll(){
		return postRepository.findAll();
	}
	public Post save(Post post) {
		if(post.getId()==0) {
			post.setCreatedAt(LocalDateTime.now());
		}
		post.setUpdatedAt(LocalDateTime.now());
		return postRepository.save(post);
		
	}
}
