package com.uca.jiniguez.itutor.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uca.jiniguez.itutor.model.Skill;
import com.uca.jiniguez.itutor.service.SkillService;

import exception.NotFoundException;

@RestController
@RequestMapping(value = "/skill")
public class SkillController {

	@Autowired
	private SkillService skillService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public Set<Skill> findSkills(@RequestParam(required=false) String skillName) throws NotFoundException{
		if(skillName!=null)
			return skillService.findByRegexSkillName(skillName);
		else
			return skillService.findAll();
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/{skillID}")
	public Skill findById(@PathVariable String skillID) throws NotFoundException {
		return skillService.findById(skillID);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public Skill create(@RequestBody Skill skill) throws NotFoundException {
		return skillService.create(skill);
	}
	
	@RequestMapping(method = {RequestMethod.PUT}, value = "/{skillID}")
	public void update(@PathVariable String skillID, @RequestBody Skill skill) throws NotFoundException {
		skillService.update(skillID, skill);
	}
	
	@RequestMapping(method = {RequestMethod.DELETE}, value = "/{skillID}")
	public void delete(@PathVariable String skillID) throws NotFoundException {
		skillService.delete(skillID);
	}
}
