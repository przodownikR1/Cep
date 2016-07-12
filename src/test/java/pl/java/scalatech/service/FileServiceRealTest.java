package pl.java.scalatech.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.ApplicationConfigurations;
import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.PropConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.service.filestorage.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoDBConfig.class,MongoRepositoryConfig.class,ServiceConfig.class, ApplicationConfigurations.class,PropConfig.class })
public class FileServiceRealTest {
    
    @Autowired
    private FileService fileService;
    
    @Test
    public void shouldBootstrap(){
       fileService.retrieveFileDateByLogin("");        
    }
}
