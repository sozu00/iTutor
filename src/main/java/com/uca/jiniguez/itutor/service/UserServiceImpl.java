package com.uca.jiniguez.itutor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uca.jiniguez.itutor.dao.UserDAO;
import com.uca.jiniguez.itutor.model.User;

import exception.NotFoundException;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;
	
	private void updateTeacherAlumn(final User result, boolean isToRemove) throws NotFoundException {
		for(String a : result.getAlumns())
			updateTeacher(a, result, isToRemove);
		for(String t : result.getTeachers())
			updateAlumn(t, result, isToRemove);
	}

	private void updateTeacher(String userID, final User teacher, boolean isToRemove) throws NotFoundException {
		if(isToRemove) 
			removeTeacher(userID, teacher.getId());
		else
			addTeacher(userID, teacher.getId());
	}

	private void updateAlumn(String userID, final User alumn, boolean isToRemove) throws NotFoundException {
		if(isToRemove) 
			removeAlumn(userID, alumn.getId());
		else
			addAlumn(userID, alumn.getId());
	}
	
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
	public User create(User user) throws NotFoundException {
		final User result = userDAO.save(user);
		updateTeacherAlumn(result, false);
		return result;
	}
	
	@Override
	public void update(String id, User user) throws NotFoundException {
		user.setId(id);
		if(userDAO.existsById(id)) {
			updateTeacherAlumn(userDAO.findById(id).orElseThrow(NotFoundException::new), true);
			updateTeacherAlumn(userDAO.save(user), false);
		}
		else
			throw new NotFoundException();
	}

	@Override
	public void delete(String id) throws NotFoundException {
		if (userDAO.existsById(id)) {
			updateTeacherAlumn(userDAO.findById(id).orElseThrow(NotFoundException::new), true);
			userDAO.deleteById(id);
		}
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

	@Override
	public List<User> findAlumns(String userId) throws NotFoundException {
		final User a = userDAO.findById(userId).orElseThrow(NotFoundException::new);
		final List<User> finalList = new ArrayList<>();
		a.getAlumns().forEach(s->finalList.add(userDAO.findById(s).orElse(null)));
		return finalList;
	}

	@Override
	public void addTeacher(String userID, String teacherID) throws NotFoundException {
		User alumn = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		List<String> teachers = alumn.getTeachers();
		if(!teachers.contains(teacherID)) {
			teachers.add(teacherID);
			alumn.setTeachers(teachers);
			userDAO.save(alumn);
			addAlumn(teacherID,userID);
		}
	}
	
	@Override
	public void addAlumn(String userID, String alumnID) throws NotFoundException {
		User teacher = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		List<String> alumns = teacher.getAlumns();
		if(!alumns.contains(alumnID)) {
			alumns.add(alumnID);
			teacher.setAlumns(alumns);
			userDAO.save(teacher);
			addTeacher(alumnID, userID);
		}
	}
	
	@Override
	public void removeTeacher(String userID, String teacherID) throws NotFoundException {
		User alumn = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		List<String> teachers = alumn.getTeachers();
		if(teachers.contains(teacherID)) {
			teachers.remove(teacherID);
			alumn.setTeachers(teachers);
			userDAO.save(alumn);
			removeAlumn(teacherID,userID);
		}
	}
	
	@Override
	public void removeAlumn(String userID, String alumnID) throws NotFoundException {
		User teacher = userDAO.findById(userID).orElseThrow(NotFoundException::new);
		List<String> alumns = teacher.getAlumns();
		if(alumns.contains(alumnID)) {
			alumns.remove(alumnID);
			teacher.setAlumns(alumns);
			userDAO.save(teacher);
			removeTeacher(alumnID, userID);
		}
	}
}
