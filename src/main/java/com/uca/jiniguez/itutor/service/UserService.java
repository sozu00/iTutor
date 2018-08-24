package com.uca.jiniguez.itutor.service;

import java.util.List;
import java.util.Set;

import com.uca.jiniguez.itutor.model.User;
import com.uca.jiniguez.itutor.model.Vote;

import exception.NotFoundException;

public interface UserService {

	Set<User> findAll();
	User findById(String id) throws NotFoundException;
	User create(User user) throws NotFoundException;
	void update(String id, User user) throws NotFoundException;
	void delete(String id) throws NotFoundException;
	Set<User> validateEmail(String email, String pwd) throws NotFoundException;
	void addTeacher(String userID, String teacherID) throws NotFoundException;
	void removeTeacher(String userID, String teacherID) throws NotFoundException;
	void addSkill(String userID, String skillName) throws NotFoundException;
	void removeSkill(String userID, String skillName) throws NotFoundException;
	Set<User> findFiltered(String skillName, Integer minimumRating, Double maxPrice, Integer formation, List<Boolean> levels);

}
