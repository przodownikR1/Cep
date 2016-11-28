package pl.java.scalatech.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.java.scalatech.entity.UserResource;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  11 wrz 2014 11:38:07
 
 */
public interface UserResourceRepository extends MongoRepository<UserResource, String>{
     
     Optional<UserResource> findByUserLogin(String login);
     
}
