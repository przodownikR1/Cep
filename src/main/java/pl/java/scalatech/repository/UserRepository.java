package pl.java.scalatech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.java.scalatech.entity.User;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  11 wrz 2014 12:03:37
 
 */
public interface UserRepository extends MongoRepository<User, String>{

}
