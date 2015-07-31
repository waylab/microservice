package com.samsung.microservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samsung.microservice.dao.vo.RegisteredUser;

public interface RegisteredUserRepository extends MongoRepository<RegisteredUser, String> {

}
