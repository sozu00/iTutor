package com.uca.jiniguez.itutor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.uca.jiniguez.itutor.dto.ErrorDTO;

import exception.InvalidDataException;
import exception.NotFoundException;

@ResponseBody
@ControllerAdvice(basePackages = "com.uca.jiniguez.itutor")
public class APIAdvice {
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDTO notFound(NotFoundException e) {
		return new ErrorDTO(404, e.getMessage());
	}
	
	@ExceptionHandler(InvalidDataException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO notValidData(InvalidDataException e) {
		return new ErrorDTO(400, e.getMessage());
	}
}