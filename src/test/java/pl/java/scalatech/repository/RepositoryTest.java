package pl.java.scalatech.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.hash.Hashing;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.entity.UserResource;
import pl.java.scalatech.service.filestorage.MongoFileService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MongoDBConfig.class, MongoFileService.class,MongoRepositoryConfig.class })
@Slf4j
public class RepositoryTest {
   @Autowired 
   private UserRepository userRepository; 
   @Autowired
   private UserResourceRepository userResourceRepository;
    
  @Test  
  public void shouldSaveUser(){
 /*     //given
      User user = new User();
      user.setId(Hashing.md5().toString());
      user.setLogin("przodownik");
      user.setEmail("przodownikR1@gmail.com");
      //when
      user = userRepository.save(user);
      log.info("{}",user);
      //then
      Assertions.assertThat(userRepository.findOne(user.getId())).isNotNull();
      Assertions.assertThat(userRepository.findOne(user.getId()).getLogin()).isEqualTo(user.getLogin());*/
      
      UserResource ur = new UserResource();
      User user = new User();
      user.setId(Hashing.md5().toString());
      user.setLogin("przodownik");
      ur.setUser(user);
      ur.setResourceMd5(Hashing.md5().toString());
      ur.setResourceName("yamaha.png");
      ur.setLogin("przodownik");
      ur = userResourceRepository.save(ur);
      assertThat(userResourceRepository.findOne(ur.getId())).isNotNull();
      log.info("{}",userResourceRepository.findByUser(user, new PageRequest(0, 2)).getContent().size());
      
      
  }

}
