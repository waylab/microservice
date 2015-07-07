package microservice.amqp.gateway;

import microservice.controllers.vo.NewRegistrationNotification;

public interface UserServiceGateway {

	void send(NewRegistrationNotification newRegistrationNotification);

}
