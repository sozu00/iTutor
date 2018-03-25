package com.uca.jiniguez.itutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class User implements Serializable{

	private static final long serialVersionUID = -967337843249861551L;

	public User() {}
	public User(String firstName) {
		this.firstName=firstName;
	}
	
	@Id
	private String id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;
	
	//private List<User> alumns = new ArrayList<>();
	//private List<User> teachers = new ArrayList<>();
}
