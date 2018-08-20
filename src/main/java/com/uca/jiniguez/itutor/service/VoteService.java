package com.uca.jiniguez.itutor.service;

import java.util.Set;

import com.uca.jiniguez.itutor.model.Vote;

import exception.InvalidDataException;
import exception.NotFoundException;

public interface VoteService {
	
	void create(Vote vote) throws NotFoundException, InvalidDataException;

	Set<Vote> findByUser(String userID);
	Double getRating(String userID);
}
