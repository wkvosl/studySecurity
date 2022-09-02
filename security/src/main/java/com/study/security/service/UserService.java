package com.study.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.security.model.Role;
import com.study.security.model.User;
import com.study.security.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//비즈니스 로직, 굳이 service/ serviceimpl과 같이 2개 만드는건 번거롭다
	
	public User save(User user) {
		
		Role role = new Role();
		role.setId(1l); //DB에서 가져오려면 레파지토리를 만들어야되서 간편하게 데리고 오기.
		
		String encodedPassword = passwordEncoder.encode(user.getPassword()) ;
		user.setPassword(encodedPassword);
		user.getRoles().add(role);
		user.setEnabled(true);
		return userRepository.save(user);
		
	}
	
	public String login(long id, String password) {
		Optional<User> user = userRepository.findById(id);
		if(user.get().getPassword().equals(password)) {
			return "success";
		}
		return "failed";
	}

	
}
