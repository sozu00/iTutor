package com.uca.jiniguez.itutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
	
	@Indexed(unique=true)
	private String email;
	
	private String firstName;
	private String lastName;
	private String phoneNum;
	private String password;
	
	private List<String> alumns = new ArrayList<>();
	private List<String> teachers = new ArrayList<>();
}
