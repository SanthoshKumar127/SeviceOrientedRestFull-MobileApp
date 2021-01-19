package com.mobile.application.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobile.application.model.User;
import com.mobile.application.repository.UserServiceImpl;

@Service
@Transactional
public class UserServicesAdmin {
	@Autowired
	UserServiceImpl userRepository;

	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public Page<User> getAllUser(Pageable pageable) {
		// TODO Auto-generated method stub
		return (Page<User>) userRepository.findAll(pageable);

	}

}
