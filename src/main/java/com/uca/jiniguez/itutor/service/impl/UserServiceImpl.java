package com.uca.jiniguez.itutor.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uca.jiniguez.itutor.config.MathFunctions;
import com.uca.jiniguez.itutor.dao.UserDAO;
import com.uca.jiniguez.itutor.model.Skill;
import com.uca.jiniguez.itutor.model.User;
import com.uca.jiniguez.itutor.service.SkillService;
import com.uca.jiniguez.itutor.service.UserService;
import com.uca.jiniguez.itutor.service.VoteService;

import exception.NotFoundException;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SkillService skillService;
	
	@Autowired
	private VoteService	voteService;
	
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
		Set<Skill> newSkills = new HashSet<>();
		for(Skill s : user.getSkills()) {
			Set<Skill> a = skillService.findByRegexSkillName(s.getSkillName());
			Skill newSkill;
			try {
				newSkill = a.iterator().next();
			}catch(Exception e){
				newSkill = skillService.create(s);
			}
			skillService.addUser(newSkill, user);
			newSkills.add(newSkill);
		}
		user.setSkills(newSkills);
		final User result = userDAO.save(user);
		return result;
	}
	
	@Override
	public void update(String id, User user) throws NotFoundException {
		user.setId(id);
		if(userDAO.existsById(id)) {
			Set<Skill> newSkills = new HashSet<>();
			for(Skill s : user.getSkills()) {
				Set<Skill> a = skillService.findByRegexSkillName(s.getSkillName());
				Skill newSkill;
				try {
					newSkill = a.iterator().next();
				}catch(Exception e){
					newSkill = skillService.create(s);
				}
				skillService.addUser(newSkill, user);
				newSkills.add(newSkill);
			}
			user.setSkills(newSkills);
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
	public Set<User> validateEmail(String email, String pwd) throws NotFoundException{
		User a = userDAO.findByEmail(email);
		if(a == null)
			throw new NotFoundException();
		final Set<User> finalSet = new HashSet<>();
		
		if(a.getPassword() == null || a.getPassword().equals(pwd))
			finalSet.add(a);
		else
			throw new NotFoundException();
		return finalSet;
	}

	@Override
	public void addTeacher(String userID, String teacherID) throws NotFoundException {
		User alumn = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		User teacher = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		Set<String> teachers = alumn.getTeachers();
		if(!teachers.contains(teacher)) {
			teachers.add(teacherID);
			alumn.setTeachers(teachers);
			userDAO.save(alumn);
		}
	}
	
	@Override
	public void removeTeacher(String userID, String teacherID) throws NotFoundException {
		User alumn = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		Set<String> teachers = alumn.getTeachers();
		if(teachers.contains(teacherID)) {
			teachers.remove(teacherID);
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
	public Set<User> findFiltered(String skillName, Double lat, Double lon, Double distance, Integer minimumRating) {
		final Set<User> skillFilter;
		final Set<User> finalSet = new HashSet<>();
		if(skillName!= null) {
			skillFilter = new HashSet<>();
			Skill skill = skillService.findBySkillName(skillName);
			if(skill != null)
				skill.getTeachers().forEach(s->skillFilter.add(userDAO.findById(s).get()));
		}else
			skillFilter = findAll();
		
		for(User u : skillFilter)
			if(checkRating(u, minimumRating) && checkDistance(u, lat, lon, distance))
				finalSet.add(u);
		
		return finalSet;
	}
	
	private boolean checkDistance(User u, Double lat, Double lon, Double distance) {
		return (lat==null || lon==null) 
				|| (MathFunctions.distance(lat, lon, u.getLatitude(),u.getLongitude()) < distance);
	}

	private boolean checkRating(User u, Integer rating) {
		return (rating == null) || (voteService.getRating(u.getId()).intValue() >= rating);
	}

	@Override
	public void removeSkill(String userID, String skillName) throws NotFoundException {
		User user = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		Skill skill = skillService.findBySkillName(skillName);
		
		if(skill == null)
			return;
		
		Set<Skill> skills = user.getSkills();
		if(skills.contains(skill)) {
			skills.remove(skill);
			user.setSkills(skills);
			userDAO.save(user);
			skillService.removeUser(skill, user);
		}
	}
}
