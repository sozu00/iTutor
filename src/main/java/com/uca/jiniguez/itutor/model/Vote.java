package com.uca.jiniguez.itutor.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Vote implements Serializable{

	private static final long serialVersionUID = -967337843249861551L;

	public Vote() {}
	
	private String voterUser;
	private String receivingUser;
	private String comment;
	private Integer rating;
}
