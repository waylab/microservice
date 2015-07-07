package microservice.amqp.gateway;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import microservice.controllers.vo.NewRegistrationNotification;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.support.RabbitGatewaySupport;

public class UserRegistrationGateway extends RabbitGatewaySupport implements UserServiceGateway {

	private String defaultReplyTo;
	
	public void setDefaultReplyTo(String defaultReplyTo) {
		this.defaultReplyTo = defaultReplyTo;
	}
	
	public void send(NewRegistrationNotification newRegistrationNotification) {
		getRabbitTemplate().convertAndSend(newRegistrationNotification, new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setReplyTo(defaultReplyTo);
				try {
					message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString().getBytes("UTF-8"));
				}
				catch (UnsupportedEncodingException e) {
					throw new AmqpException(e);
				}
				return message;
			}
		});
	}

}

