/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samsung.microservice.amqp.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class AbstractEmailRabbitConfiguration {

	protected static String EXCHANGE_NAME = "user-registrations";

	protected static String ROUTING_KEY = "notify-new-user";
	
	@Value("${amqp.ip:70.12.114.116}") 
	private String ip;
	
	@Value("${amqp.port:5672}") 
	private int port;
	

	protected abstract void configureRabbitTemplate(RabbitTemplate template);

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(ip);
		connectionFactory.setUsername("c2");
		connectionFactory.setPassword("c2");
		connectionFactory.setPort(port);
		return connectionFactory;
	}

	@Bean 
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setMessageConverter(jsonMessageConverter());
		configureRabbitTemplate(template);
		return template;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		// The JsonMessageConverter which uses the org.codehaus.jackson 1.x library
		// and Jackson2JsonMessageConverter which uses the com.fasterxml.jackson 2.x library.
		Jackson2JsonMessageConverter converter =  new Jackson2JsonMessageConverter();
		return converter;
	}
	
	@Bean
	public TopicExchange userRegistrationsExchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	/**
	 * @return the admin bean that can declare queues etc.
	 */
	@Bean
	public AmqpAdmin amqpAdmin() {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
		return rabbitAdmin ;
	}

}
