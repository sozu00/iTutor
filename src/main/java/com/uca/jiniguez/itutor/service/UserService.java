package com.uca.jiniguez.itutor.service;

import java.util.List;

import com.uca.jiniguez.itutor.model.User;

import exception.NotFoundException;

public interface UserService {

	List<User> findAll(Integer page, Integer size);
	User findById(String id) throws NotFoundException;
	User create(User user);
	void update(String id, User user) throws NotFoundException;
	void delete(String id) throws NotFoundException;

}