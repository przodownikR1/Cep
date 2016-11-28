package pl.java.scalatech.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;

@Configuration
public class MongoDBConfig extends AbstractMongoConfiguration {
    @Autowired
    private MongoSettings mongoSettings;

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Bean
    public DB mongoDB() throws Exception {
        return mongo().getDB(mongoSettings.getDatabase());
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(mongoSettings.getHost());
    }

    @Override
    public String getDatabaseName() {
        return mongoSettings.getDatabase();
    }

    @Bean
    public GridFS gridFS() throws Exception {
        return new GridFS(mongoDB());
    }
    @Profile("cascade")
    @Bean
    CascadingMongoEventListener cascadingMongoListener(){
        return new CascadingMongoEventListener();
    }
    
    
}