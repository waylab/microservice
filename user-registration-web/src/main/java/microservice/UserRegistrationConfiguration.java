package microservice;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class UserRegistrationConfiguration {

	protected static String QUEUE_NAME = "user-registration";
	protected static String ROUTING_KEY = QUEUE_NAME;
	protected static String EXCHANGE_NAME = "user-registrations";

	@Value("${amqp.port:5672}") 
	private int port = 5672;

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
//	@Bean
//	public ConnectionFactory connectionFactory() {
//		//TODO make it possible to customize in subclasses.
//		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//		connectionFactory.setUsername("guest");
//		connectionFactory.setPassword("guest");
//		connectionFactory.setPort(port);
//		return connectionFactory;
//	}

	@Bean 
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}
	
	@Bean
	public TopicExchange userRegistrationsExchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}
}
