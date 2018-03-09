package com.uca.jiniguez.itutor.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ErrorDTO implements Serializable{

	private static final long serialVersionUID = -6038054957186855061L;
	private Integer code;
	private String msg;
	
	public ErrorDTO(Integer c, String m) {
		super();
		code = c; msg = m;
	}
	
}
