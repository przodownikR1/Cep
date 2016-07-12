package pl.java.scalatech.controller;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import pl.java.scalatech.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { AppConfig.class})
@ActiveProfiles(profiles="dev,cache")
@Slf4j
@WebAppConfiguration
public class UploadTest {
    @Autowired
    private RestTemplate restTemplate;
    
    @Test
    public void shouldWebWork(){
       assertThat(restTemplate).isNotNull();
    }
    
}
