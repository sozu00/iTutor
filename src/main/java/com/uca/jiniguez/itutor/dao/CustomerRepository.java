package com.uca.jiniguez.itutor.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.uca.jiniguez.itutor.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}
