package com.samsung.microservice.amqp.config.client;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import com.samsung.microservice.amqp.config.AbstractUserConfiguration;

@Configuration
public class UserServerConfiguration extends AbstractUserConfiguration {
	
	@Override
	protected void configureRabbitTemplate(RabbitTemplate rabbitTemplate) {
		rabbitTemplate.setExchange(EXCHANGE_NAME);
	}
	
}
