package pl.java.scalatech.config.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@ConfigurationProperties(prefix="mongo")
@Data
@Component
public class MongoSettings {    
    private String database;
    private String host;

}
