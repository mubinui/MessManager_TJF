package com.example.messManager.myConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.messManager.Dao.managerRepository;
import com.example.messManager.entities.Manager;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private managerRepository repository; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching from database
		Manager manager = 	repository.getUserByUserName(username);
		
		if (manager == null) {
			throw new UsernameNotFoundException("couldn't found any user");
		}
		
		customUserDetails userDetails = new customUserDetails(manager);
		
		return userDetails;
	}

}
