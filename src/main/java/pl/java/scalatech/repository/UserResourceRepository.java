package pl.java.scalatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import pl.java.scalatech.entity.User;
import pl.java.scalatech.entity.UserResource;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  11 wrz 2014 11:38:07
 
 */
public interface UserResourceRepository extends MongoRepository<UserResource, String>{
     Page<UserResource> findByUser(User user,Pageable pagaPageable);
     
     
}
