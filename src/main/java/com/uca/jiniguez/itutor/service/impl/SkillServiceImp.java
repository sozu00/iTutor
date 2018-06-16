package com.uca.jiniguez.itutor.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uca.jiniguez.itutor.dao.SkillDAO;
import com.uca.jiniguez.itutor.model.Skill;
import com.uca.jiniguez.itutor.model.User;
import com.uca.jiniguez.itutor.service.SkillService;

import exception.NotFoundException;

@Service
public class SkillServiceImp implements SkillService {

	@Autowired
	private SkillDAO skillDAO;
	
	@Override
	public Set<Skill> findAll() {
		final Iterable<Skill> findAll = skillDAO.findAll();
		final Set<Skill> finalList = new HashSet<>();
		findAll.forEach(a->finalList.add(a));
		
		return finalList;
	}

	@Override
	public Skill findById(String id) throws NotFoundException {
		final Skill a = skillDAO.findById(id).orElseThrow(NotFoundException::new);
		return a;
	}

	@Override
	public Skill create(Skill skill) throws NotFoundException {
		final Skill result = skillDAO.save(skill);
		return result;
	}

	@Override
	public void update(String id, Skill skill) throws NotFoundException {
		skill.setId(id);
		if(skillDAO.existsById(id)) {
			skillDAO.save(skill);
		}
		else
			throw new NotFoundException();
	}

	@Override
	public void delete(String id) throws NotFoundException {
		if (skillDAO.existsById(id)) {
			skillDAO.deleteById(id);
		}
		else
			throw new NotFoundException();
	}

	@Override
	public Set<Skill> findByRegexSkillName(String skillName) {
		Set<Skill> skills = new HashSet<>();
		skills = skillDAO.findBySkillNameLike(skillName);
		return skills;
	}
	
	@Override
	public Skill findBySkillName(String skillName) {
		Skill skill = skillDAO.findBySkillName(skillName);
		return skill;
	}

	@Override
	public void addUser(Skill skill, User user) {
		Set<User> teachers = skill.getTeachers(); 
		if(!teachers.contains(user)) {
			teachers.add(user);
			skill.setTeachers(teachers);
			skillDAO.save(skill);
		}
	}
}
