package com.uca.jiniguez.itutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
	private String latitude;
	private String longitude;
	
	private List<String> alumns = new ArrayList<>();
	private List<String> teachers = new ArrayList<>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Skill> skills = new ArrayList<>();
}
