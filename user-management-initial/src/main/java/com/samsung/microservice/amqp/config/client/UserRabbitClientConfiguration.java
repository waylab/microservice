package com.samsung.microservice.amqp.config.client;


import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.samsung.microservice.amqp.config.AbstractUserRabbitConfiguration;
import com.samsung.microservice.amqp.consumer.Receiver;

@Configuration
public class UserRabbitClientConfiguration extends AbstractUserRabbitConfiguration {

	protected static String QUEUE_NAME = "user-management";

	@Override
	protected void configureRabbitTemplate(RabbitTemplate rabbitTemplate) {
		rabbitTemplate.setRoutingKey(ROUTING_KEY);		
	}
	
	@Bean
	public Queue userManagementQueue() {
//		return amqpAdmin().declareQueue();
		return new Queue(QUEUE_NAME);
	}

	@Bean 
	public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter, AmqpAdmin rabbitAdmin) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());		
		container.setQueueNames(QUEUE_NAME);
		container.setMessageListener(listenerAdapter);
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setRabbitAdmin((RabbitAdmin) rabbitAdmin);
		container.setAutoStartup(true);
		return container;

	}
	
	@Bean
    public Receiver receiver() {
        return new Receiver();
    }
	
	@Bean 
	public MessageListenerAdapter listenerAdapter(Receiver receiver) {
		MessageListenerAdapter listener = new MessageListenerAdapter(receiver, jsonMessageConverter());
		// default Method는 "handleMessage"
	    listener.setDefaultListenerMethod("receiveMessage");
	    return listener;
	}
	
	@Bean
	public Binding userManagementBinding() {
		return BindingBuilder.bind(userManagementQueue()).to(userRegistrationsExchange()).with(ROUTING_KEY);
	}
	
	@Bean
	public AmqpAdmin rabbitAdmin() {
		return new RabbitAdmin(connectionFactory());
	}
	
}
