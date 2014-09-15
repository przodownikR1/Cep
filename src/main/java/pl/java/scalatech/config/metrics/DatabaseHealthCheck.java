package pl.java.scalatech.config.metrics;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import pl.java.scalatech.entity.UserResource;

import com.codahale.metrics.health.HealthCheck;

@Slf4j
public class DatabaseHealthCheck extends HealthCheck {
    
  @Autowired
  private MongoTemplate mongoTemplate;
  MongoOperations mongoOperations;
   
    @Override
    protected Result check() throws Exception {
        log.info("Do DatabaseHealthCheck...");
        Result result;
        try {
            List<UserResource> data = mongoTemplate.findAll(UserResource.class);
            
            if (data == null) {
                log.warn("Check is KO");
                result = Result.unhealthy("Request returned no result");
            } else {
                log.info("Check is OK");
                result = Result.healthy("Connection to the database is OK");
            }
        } catch (Exception e) {
            log.error("Check is KO : an error occured");
            log.error(e.toString());
            result = Result.unhealthy("Request returned no result");
        }
        return result;
    }
}