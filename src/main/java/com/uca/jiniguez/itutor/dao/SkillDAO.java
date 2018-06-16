package com.uca.jiniguez.itutor.dao;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uca.jiniguez.itutor.model.Skill;

@Repository
public interface SkillDAO extends MongoRepository<Skill, String>{
	
	Set<Skill> findBySkillNameLike(String skillName);
	Skill findBySkillName(String skillName);
}
