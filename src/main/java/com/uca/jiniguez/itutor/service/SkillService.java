package com.uca.jiniguez.itutor.service;

import java.util.Set;

import com.uca.jiniguez.itutor.model.Skill;
import com.uca.jiniguez.itutor.model.User;

import exception.NotFoundException;

public interface SkillService {
	Set<Skill> findAll();
	Skill findById(String id) throws NotFoundException;
	Skill create(Skill skill) throws NotFoundException;
	void update(String id, Skill skill) throws NotFoundException;
	void delete(String id) throws NotFoundException;
	Skill findBySkillName(String skillName);
	Set<Skill> findByRegexSkillName(String skillName);
	void addUser(Skill skill, User alumn);
}
