package com.samsung.microservice.amqp.consumer;

import com.samsung.microservice.amqp.vo.NewRegistrationNotification;

public class Receiver {

	public void receiveMessage(NewRegistrationNotification newUser) {
		System.out.println("Received ID:[" + newUser.getId() + "] EMAIL:[" + newUser.getEmailAddress()+"]" );
	}


}
