package com.samsung.microservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.samsung.microservice.controllers.vo.RegistrationRequest;
import com.samsung.microservice.controllers.vo.RegistrationResponse;
import com.samsung.microservice.dao.RegisteredUserRepository;

// TODO Rest Controller
public class UserRegistrationController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RegisteredUserRepository registeredUserRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	// TODO Request Mapping
	// TODO Requestbody message converting
	public RegistrationResponse registerUser(RegistrationRequest request){
		logger.debug("### Step1 : [ Spring RestController Success ] Hello World (POST /user)");
		
		if(request.getEmailAddress() != null && request.getPassword() != null){
			logger.debug("### Step2 : [ Spring Requestbody - vo mapping Success ] " + request.toString());
		}
		
		// TODO User Repository 호출
		
		// TODO Publish Notification Message
		
		// TODO User ID와 EmailAddress를 Response
		return new RegistrationResponse();
	}
	
	// TODO Exception Handler(DuplicateKeyException)
	
}
