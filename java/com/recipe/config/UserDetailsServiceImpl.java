package com.recipe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.recipe.entities.User;
import com.recipe.repo.IUserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepo userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		//fetching user form data
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("Could not found user  !!");
		}
		else {
			return new CustomeUserDetails(user);
		}
		
		
	}

}
