package microservice.controllers;

import microservice.controllers.vo.NewRegistrationNotification;
import microservice.controllers.vo.RegistrationRequest;
import microservice.controllers.vo.RegistrationResponse;
import microservice.dao.RegisteredUserRepository;
import microservice.dao.vo.RegisteredUser;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;

@RestController
public class UserRegistrationController {

	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
//	@Autowired
//	private UserServiceGateway userServiceGateway;
	

	@RequestMapping(value = {"/user"}, method = {RequestMethod.POST})
	@ResponseBody
	public RegistrationResponse registerUser(@RequestBody RegistrationRequest request){
		RegisteredUser registeredUser =
				new RegisteredUser(null, request.getEmailAddress(), request.getPassword());

		registeredUserRepository.save(registeredUser);
		
		NewRegistrationNotification newRegistrationNotification = new NewRegistrationNotification(registeredUser.getId(), request.getEmailAddress(), request.getPassword());
		
//		userServiceGateway.send(newRegistrationNotification);
		
		rabbitTemplate.convertAndSend("user-registrations", "user-registrations", newRegistrationNotification);
		
		return new RegistrationResponse(registeredUser.getId(), request.getEmailAddress(), request.getPassword());
		
	}

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicate email address")
	@ExceptionHandler(DuplicateKeyException.class)
	public void duplicateEmailAddress() {}

}
