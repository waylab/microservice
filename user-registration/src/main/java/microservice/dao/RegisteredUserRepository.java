package microservice.dao;

import microservice.dao.vo.RegisteredUser;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegisteredUserRepository extends MongoRepository<RegisteredUser, String> {

}
