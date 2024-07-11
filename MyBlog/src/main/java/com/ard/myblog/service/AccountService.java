package com.ard.myblog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ard.myblog.model.Account;
import com.ard.myblog.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService{
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Optional<Account> findById(Long id){
		return accountRepository.findById(id);
	}

	public Account save(Account ac1) {
		
		ac1.setPassword(passwordEncoder.encode(ac1.getPassword()));
		if(ac1.getRole()==null) {
			ac1.setRole("USER");
		}
		return accountRepository.save(ac1);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> optionalAccount = accountRepository.findByEmail(username);
		if(!optionalAccount.isPresent()) {
			throw new UsernameNotFoundException("Invalid user!");
		}
		
		Account account = optionalAccount.get();
		
		List<GrantedAuthority> grantedAuthority=new ArrayList<>();
		grantedAuthority.add(new SimpleGrantedAuthority(account.getRole()));
		
		return new User(account.getEmail(), account.getPassword(), grantedAuthority);
	}
	
	
}
