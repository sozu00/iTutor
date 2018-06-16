package com.uca.jiniguez.itutor.service;

import java.util.Set;

import com.uca.jiniguez.itutor.model.User;

import exception.NotFoundException;

public interface UserService {

	Set<User> findAll();
	User findById(String id) throws NotFoundException;
	User create(User user) throws NotFoundException;
	void update(String id, User user) throws NotFoundException;
	void delete(String id) throws NotFoundException;
	Set<User> validateEmail(String email, String pwd);
	void addTeacher(String userID, String teacherID) throws NotFoundException;
	void removeTeacher(String userID, String teacherID) throws NotFoundException;
	void addSkill(String userID, String skillName) throws NotFoundException;
	Set<User> findFiltered(String skillName, double lat, double lon, double distance);
}
