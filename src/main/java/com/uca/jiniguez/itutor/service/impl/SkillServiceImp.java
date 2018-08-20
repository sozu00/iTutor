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
	public Skill create(Skill skill) throws NotFoundException {
		final Skill result = skillDAO.save(skill);
		return result;
	}

	@Override
	public Set<Skill> findByRegexSkillName(String skillName) {
		Set<Skill> skills = new HashSet<>();
		skills = skillDAO.findBySkillNameLike(skillName.toLowerCase());
		return skills;
	}
	
	@Override
	public Skill findBySkillName(String skillName) {
		Skill skill = skillDAO.findBySkillName(skillName.toLowerCase());
		return skill;
	}

	@Override
	public void addUser(Skill skill, User user) {
		Set<String> teachers = skill.getTeachers(); 
		if(!teachers.contains(user.getId())) {
			teachers.add(user.getId());
			skill.setTeachers(teachers);
			skillDAO.save(skill);
		}
	}

	@Override
	public void removeUser(Skill skill, User user) {
		Set<String> teachers = skill.getTeachers();
		if(teachers.contains(user.getId())) {
			teachers.remove(user.getId());
			skill.setTeachers(teachers);
			if(teachers.size() == 0)
				skillDAO.delete(skill);
			else
				skillDAO.save(skill);
		}
		
	}
}
