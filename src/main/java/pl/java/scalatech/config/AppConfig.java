package pl.java.scalatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
@Configuration
@Import({ ApplicationConfigurations.class,MongoDBConfig.class,MongoRepositoryConfig.class,PropConfig.class,WebConfig.class,ServiceConfig.class})
public class AppConfig {

    
}