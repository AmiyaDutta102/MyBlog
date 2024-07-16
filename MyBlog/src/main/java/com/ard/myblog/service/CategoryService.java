package com.ard.myblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ard.myblog.model.Category;
import com.ard.myblog.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catRepository;
	
	public List<Category> findAll(){
		return catRepository.findAll();
	}

	public Category save(Category cat) {
		return catRepository.save(cat);
	}

	public Optional<Category> findById(long id) {
		return catRepository.findById(id);
	}
	
}
