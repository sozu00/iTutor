package com.uca.jiniguez.itutor.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uca.jiniguez.itutor.config.MathFunctions;
import com.uca.jiniguez.itutor.dao.UserDAO;
import com.uca.jiniguez.itutor.model.Skill;
import com.uca.jiniguez.itutor.model.User;
import com.uca.jiniguez.itutor.service.SkillService;
import com.uca.jiniguez.itutor.service.UserService;

import exception.NotFoundException;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SkillService skillService;
	
	@Override
	public Set<User> findAll() {
		final Iterable<User> findAll = userDAO.findAll();
		final Set<User> finalSet = new HashSet<>();
		findAll.forEach(a->finalSet.add(a));
		
		return finalSet;
	}

	@Override
	public User findById(String id) throws NotFoundException {
		final User a = userDAO.findById(id).orElseThrow(NotFoundException::new);
		return a;
	}

	@Override
	public User create(User user) throws NotFoundException {
		final User result = userDAO.save(user);
		return result;
	}
	
	@Override
	public void update(String id, User user) throws NotFoundException {
		user.setId(id);
		if(userDAO.existsById(id)) {
			userDAO.save(user);
		}
		else
			throw new NotFoundException();
	}

	@Override
	public void delete(String id) throws NotFoundException {
		if (userDAO.existsById(id)) {
			userDAO.deleteById(id);
		}
		else
			throw new NotFoundException();
	}

	@Override
	public Set<User> validateEmail(String email, String pwd) {
		User a = userDAO.findByEmail(email);
		final Set<User> finalSet = new HashSet<>();
		
		if(a.getPassword() == null || a.getPassword().equals(pwd))
			finalSet.add(a);
		
		return finalSet;
	}

	@Override
	public void addTeacher(String userID, String teacherID) throws NotFoundException {
		User alumn = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		User teacher = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		Set<User> teachers = alumn.getTeachers();
		if(!teachers.contains(teacher)) {
			teachers.add(teacher);
			alumn.setTeachers(teachers);
			userDAO.save(alumn);
		}
	}
	
	@Override
	public void removeTeacher(String userID, String teacherID) throws NotFoundException {
		User alumn = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		User teacher = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		Set<User> teachers = alumn.getTeachers();
		if(teachers.contains(teacher)) {
			teachers.remove(teacher);
			alumn.setTeachers(teachers);
			userDAO.save(alumn);
		}
	}
	
	@Override
	public void addSkill(String userID, String skillName) throws NotFoundException {
		User user = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		Skill skill = skillService.findBySkillName(skillName);
		
		if(skill == null)
			skill = skillService.create(new Skill(skillName));
		
		Set<Skill> skills = user.getSkills();
		if(!skills.contains(skill)) {
			skills.add(skill);
			user.setSkills(skills);
			userDAO.save(user);
			skillService.addUser(skill, user);
		}
	}

	@Override
	public Set<User> findFiltered(String skillName, double lat, double lon, double distance) {
		Set<User> skillFilter = new HashSet<>();
		if(skillName!= null) {
			Skill skill = skillService.findBySkillName(skillName);
			if(skill != null)
				skillFilter = skill.getTeachers(); 
		}else
			skillFilter = findAll();
		
		final Set<User> finalSet = new HashSet<>();
		
		for(User u : skillFilter) {
			if(lat==0 || lon== 0)
				finalSet.add(u);
			else 
				if (MathFunctions.distance(
						lat, 
						lon, 
						u.getLatitude(), 
						u.getLongitude()
					) < distance)
					finalSet.add(u);
		}
		
		return finalSet;
	}
	
}
