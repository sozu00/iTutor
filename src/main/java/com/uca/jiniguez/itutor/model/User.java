package com.uca.jiniguez.itutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class User implements Serializable{

	private static final long serialVersionUID = -967337843249861551L;

	public User() {}
	public User(String firstName) {
		this.name=firstName;
	}
	
	@Id
	private String id;
	
	@Indexed(unique=true, sparse=true)
	private String email;
	
	private String name;
	private String phoneNum;
	private String password;
	private String quote;
	private Double price = 0.0;
	private List<Boolean> levels = new ArrayList<>(Arrays.asList(false,false,false,false));
	private Integer formation = 1;
	
	@OneToMany(fetch=FetchType.LAZY)
	private Set<String> teachers = new HashSet<>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Skill> skills = new HashSet<>();
}
