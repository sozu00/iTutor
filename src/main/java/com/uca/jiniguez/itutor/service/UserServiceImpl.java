package com.uca.jiniguez.itutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uca.jiniguez.itutor.config.Constants;
import com.uca.jiniguez.itutor.dao.UserDAO;
import com.uca.jiniguez.itutor.model.User;

import exception.NotFoundException;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public List<User> findAll() {
		final Iterable<User> findAll = userDAO.findAll();
		final List<User> finalList = new ArrayList<>();
		findAll.forEach(a->finalList.add(a));
		
		return finalList;
	}

	@Override
	public User findById(String id) throws NotFoundException {
		final User a = userDAO.findById(id).orElseThrow(NotFoundException::new);
		return a;
	}

	@Override
	public User create(User user) {
		final User result = userDAO.save(user);
		return result;
	}

	@Override
	public void update(String id, User user) throws NotFoundException {
		user.setId(id);
		if(userDAO.existsById(id))
			userDAO.save(user);
		else
			throw new NotFoundException();
	}

	@Override
	public void delete(String id) throws NotFoundException {
		if (userDAO.existsById(id))
			userDAO.deleteById(id);
		else
			throw new NotFoundException();
	}

	@Override
	public List<User> validateEmail(String email, String pwd) {
		User a = userDAO.findByEmail(email);
		final List<User> finalList = new ArrayList<>();
		
		if(a.getPassword() == null || a.getPassword().equals(pwd))
			finalList.add(a);
		
		return finalList;
	}
}
