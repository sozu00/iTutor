package com.uca.jiniguez.itutor.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uca.jiniguez.itutor.model.User;
import com.uca.jiniguez.itutor.service.UserService;

import exception.InvalidDataException;
import exception.NotFoundException;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public Set<User> findUsers(
			@RequestParam(required=false)String skillName,
			@RequestParam(required=false)Integer minimumRating,
			@RequestParam(required=false)Double maxPrice,
			@RequestParam(required=false)Integer formation,
			@RequestParam(required=false)List<Boolean> levels
			) throws NotFoundException{
		return userService.findFiltered(skillName, minimumRating, maxPrice, formation, levels);
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/{userID}")
	public User findById(@PathVariable String userID) throws NotFoundException {
		return userService.findById(userID);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public User create(@RequestBody User user) throws NotFoundException {
		return userService.create(user);
	}
	
	@RequestMapping(method = {RequestMethod.PUT}, value = "/{userID}")
	public void update(@PathVariable String userID, @RequestBody User user) throws NotFoundException {
		userService.update(userID, user);
	}
	
	@RequestMapping(method = {RequestMethod.DELETE}, value = "/{userID}")
	public void delete(@PathVariable String userID) throws NotFoundException {
		userService.delete(userID);
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/login")
	private Set<User> findByEmail(
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String pwd) throws InvalidDataException, NotFoundException{
		Optional.ofNullable(email).orElseThrow(()->new InvalidDataException("email not present"));
		Optional.ofNullable(pwd).orElseThrow(()->new InvalidDataException("pwd not present"));
		return userService.validateEmail(email, pwd);
	}
	
	
	@RequestMapping(method = {RequestMethod.PUT}, value = "/{userID}/addTeacher/{teacherID}")
	public void addTeacher(@PathVariable String userID, @PathVariable String teacherID) throws NotFoundException {
		userService.addTeacher(userID, teacherID);
	}
	
	
	@RequestMapping(method = {RequestMethod.PUT}, value = "/{userID}/removeTeacher/{teacherID}")
	public void removeTeacher(@PathVariable String userID, @PathVariable String teacherID) throws NotFoundException {
		userService.removeTeacher(userID, teacherID);
	}
	
	@RequestMapping(method = {RequestMethod.PUT}, value = "/{userID}/addSkill/{skillName}")
	public void addSkill(@PathVariable String userID, @PathVariable String skillName) throws NotFoundException {
		userService.addSkill(userID, skillName);
	}
	@RequestMapping(method = {RequestMethod.PUT}, value = "/{userID}/removeSkill/{skillName}")
	public void removeSkill(@PathVariable String userID, @PathVariable String skillName) throws NotFoundException {
		userService.removeSkill(userID, skillName);
	}
	
}
