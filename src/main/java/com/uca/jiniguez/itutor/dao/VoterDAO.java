package com.uca.jiniguez.itutor.dao;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uca.jiniguez.itutor.model.Vote;

@Repository
public interface VoterDAO extends MongoRepository<Vote, String>{

	Set<Vote> findByReceivingUser(String receivingUser);
}
