package pl.java.scalatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.java.scalatech.config.mongo.MongoDBConfig;
import pl.java.scalatech.config.mongo.MongoRepositoryConfig;
@Configuration
@Import({ ApplicationConfigurations.class,MongoDBConfig.class,MongoRepositoryConfig.class,PropConfig.class,WebConfig.class,ServiceConfig.class,SwaggerConfig.class})
public class AppConfig {

    
}