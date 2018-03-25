package com.uca.jiniguez.itutor.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uca.jiniguez.itutor.model.User;

@Repository
public interface UserDAO extends MongoRepository<User, String>{

    public User findByEmail(String email);
}
