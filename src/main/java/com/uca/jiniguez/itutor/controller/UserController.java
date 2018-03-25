package com.uca.jiniguez.itutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uca.jiniguez.itutor.model.User;
import com.uca.jiniguez.itutor.service.UserService;

import exception.NotFoundException;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<User> findUsers(
			@RequestParam(required=false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String pwd
			) throws NotFoundException{
		
		
		if(page!=null || size!=null) {
			log.info(String.format("Buscando todos los users de la pagina %d de tamaño %d",page,size));
			return findAll(page,size);
		}
		
		else if(email!=null) {
			return findByEmail(email, pwd);
		}
		
		else
			throw new NotFoundException();
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/{userID}")
	public User findById(@PathVariable String userID) throws NotFoundException {
		return userService.findById(userID);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public User create(@RequestBody User user) {
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
	
	private List<User> findAll(Integer page, Integer size){
		return userService.findAll(page, size);
	}
	
	private List<User> findByEmail(String email, String pwd){
		return userService.validateEmail(email, pwd);
	}
}
