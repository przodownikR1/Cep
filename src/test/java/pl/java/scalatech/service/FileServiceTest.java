package pl.java.scalatech.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.ApplicationConfigurations;
import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.PropConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.service.filestorage.FileService;

import com.mongodb.DB;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoDBConfig.class,MongoRepositoryConfig.class,ServiceConfig.class, ApplicationConfigurations.class,PropConfig.class })
public class FileServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private DB mongoDB;

    @Mock
    private GridFsTemplate gridFsTemplate;

    @Autowired
    @InjectMocks
    private FileService fileService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldContextStart() {

    }
}