package com.ard.myblog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ard.myblog.model.Account;
import com.ard.myblog.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	public Optional<Account> findById(Long id){
		return accountRepository.findById(id);
	}

	public Account save(Account ac1) {
		return accountRepository.save(ac1);
	}
}
