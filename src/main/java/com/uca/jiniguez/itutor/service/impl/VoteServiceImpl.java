package com.uca.jiniguez.itutor.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uca.jiniguez.itutor.dao.VoterDAO;
import com.uca.jiniguez.itutor.model.Vote;
import com.uca.jiniguez.itutor.service.VoteService;

import exception.InvalidDataException;
import exception.NotFoundException;


@Service
public class VoteServiceImpl implements VoteService{
	
	@Autowired
	private VoterDAO voterDAO;

	@Override
	public void create(Vote vote) throws NotFoundException, InvalidDataException{
		try {
			Set<Vote> votesReceived = findByUser(vote.getReceivingUser());
			for(Vote v : votesReceived) {
				if(v.getVoterUser().equals(vote.getVoterUser()))
					throw new InvalidDataException("Este usuario ya ha sido votado");
			}
			
			voterDAO.save(vote);
		}
		catch(InvalidDataException e) {throw e;}
		catch(Exception e) {throw new NotFoundException();}
	}

	@Override
	public Set<Vote> findByUser(String userID) {
		return voterDAO.findByReceivingUser(userID);
	}

	@Override
	public Double getRating(String userID) {
		Double rating = new Double(0);
		Set<Vote> votes = voterDAO.findByReceivingUser(userID);
		
		for(Vote v : votes)
			rating += v.getRating();
		
		rating /= votes.size();
		
		return rating;
	}
	
	
}
