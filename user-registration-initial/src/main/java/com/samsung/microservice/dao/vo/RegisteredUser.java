package com.samsung.microservice.dao.vo;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class RegisteredUser {
	
	private String id;
	
	@Indexed(unique=true)
	@Field
	private String emailAddress;
	
	private String password;
	
	public RegisteredUser(){}
	
	public RegisteredUser(String id, String emailAddress, String password) {
		super();
		this.id = id;
		this.emailAddress = emailAddress;
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
