package com.uca.jiniguez.itutor.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uca.jiniguez.itutor.model.Vote;
import com.uca.jiniguez.itutor.service.VoteService;

import exception.InvalidDataException;
import exception.NotFoundException;

@RestController
@RequestMapping(value = "/vote")
public class VoteController {

	@Autowired
	private VoteService voteService;
	
	@RequestMapping(method = {RequestMethod.POST})
	public void vote(@RequestBody Vote vote) throws NotFoundException, InvalidDataException {
		voteService.create(vote);
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/{userID}")
	public Set<Vote> findByUser(@PathVariable String userID) throws NotFoundException {
		return voteService.findByUser(userID);
	}
	
	@RequestMapping(method = {RequestMethod.GET}, value = "/{userID}/rating")
	public Double getRating(@PathVariable String userID) throws NotFoundException {
		return voteService.getRating(userID);
	}
}
