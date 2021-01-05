package com.mobile.application.controller;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.application.dto.UserDto;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.User;
import com.mobile.application.repository.UserRepository;

/**
 * Admin Login Controller
 * 
 * @author Nanda sagar
 *
 */
//@SessionAttributes("Admin")
@Controller
@ResponseBody
public class AdminLoginController {

	/**
	 * Admin Session Setup
	 * 
	 * @return
	 */
	@ModelAttribute("Admin")
	public User setUp() {
		return new User();
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * maps to admin register page
	 * 
	 * @return
	 */
	@RequestMapping("register1")
	public String register1() {
		return "register1";
	}

	/**
	 * creating post mapping that post the new user detail in the database
	 * 
	 * @param users
	 * @return
	 */
	@PostMapping("/saveAdmin")
	private UserDto saveAdmin(@RequestBody UserDto users) {
		String email = users.getEmail();
		User userEntity = modelMapper.map(users, User.class);
		User user = null;
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(regex) && users.getRolename().equals("Admin")) {
			user = userRepository.save(userEntity);
			if (Objects.isNull(users.getId())) {
				throw new UserNotfoundException("Error while Admin Registration..!");
			}
		}
		return modelMapper.map(user, UserDto.class);
	}

	/**
	 * creating put mapping that updates the Admin details
	 * 
	 * @param users
	 * @return
	 */
	@PutMapping("/updateAdmin")
	private UserDto updateAdmin(@RequestBody UserDto users) {
		String email = users.getEmail();
		User userEntity = modelMapper.map(users, User.class);
		User user = null;
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(regex)) {
			user = userRepository.save(userEntity);
		}
		if (Objects.isNull(user)) {
			throw new UserNotfoundException("Error while Admin Updation..!");
		}
		return modelMapper.map(user, UserDto.class);
	}

	/**
	 * Validates Admin Credentials
	 * 
	 * @param user
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@PostMapping("/validateAdmin/{email}/{password}")
	public UserDto LoginAdmin(@PathVariable String email, @PathVariable String password) {
		User userList = userRepository.findByEmailAndPassword(email, password);
		if (Objects.isNull(userList)) {
			throw new UserNotfoundException("email or Password is incorrect for this email id " + email);
		}
		if (userList.getRolename().equals("Admin")) {
			return modelMapper.map(userList, UserDto.class);
		} else
			throw new UserNotfoundException("email or Password is incorrect for this email id " + email);
	}
}