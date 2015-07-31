package com.samsung.microservice.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.samsung.microservice.amqp.vo.NewRegistrationNotification;
import com.samsung.microservice.controllers.vo.RegistrationRequest;
import com.samsung.microservice.controllers.vo.RegistrationResponse;
import com.samsung.microservice.dao.RegisteredUserRepository;
import com.samsung.microservice.dao.vo.RegisteredUser;

@RestController
public class UserRegistrationController {

	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@RequestMapping(value = {"/user"}, method = {RequestMethod.POST})
	@ResponseBody
	public RegistrationResponse registerUser(@RequestBody RegistrationRequest request){
		RegisteredUser registeredUser =
				new RegisteredUser(null, request.getEmailAddress(), request.getPassword());

		registeredUserRepository.save(registeredUser);
		
		NewRegistrationNotification newRegistrationNotification =new NewRegistrationNotification(registeredUser.getId(), request.getEmailAddress());
		
		rabbitTemplate.convertAndSend("user-registrations", "notify-new-user", newRegistrationNotification);
		
		return new RegistrationResponse(registeredUser.getId(), request.getEmailAddress());
		
	}

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicate email address")
	@ExceptionHandler(DuplicateKeyException.class)
	public void duplicateEmailAddress() {
	}

}
