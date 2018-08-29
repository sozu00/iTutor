package com.uca.jiniguez.itutor.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
}
