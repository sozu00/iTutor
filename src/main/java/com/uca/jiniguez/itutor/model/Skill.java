package com.uca.jiniguez.itutor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Skill implements Serializable{

	private static final long serialVersionUID = 8831928217267380979L;

	public Skill() {}
	
	public Skill(String skillName) {
		this.skillName = skillName;
	}
	
	@Id
	private String id;
	
	@Indexed(unique=true, sparse=true)
	private String skillName;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<String> teachers = new HashSet<>();
}
