package pl.java.scalatech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.java.scalatech.entity.ResourceRestriction;

public interface ResourceRestrictionRepository extends MongoRepository<ResourceRestriction, String>{

}
