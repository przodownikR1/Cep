package pl.java.scalatech.setting;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.ApplicationConfigurations;
import pl.java.scalatech.config.PropConfig;

import com.google.common.collect.Lists;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  5 wrz 2014 12:47:08
 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { PropConfig.class, ApplicationConfigurations.class})
@Slf4j
public class PropTest {

    @Autowired
    private ApplicationContext applicationContext;    
    @Autowired 
    private ApplicationSettings applicationSettings;
    @Autowired 
    private ApplicationInfo applicationInfo;

    @Test
    public void shouldProperContextLoad() {
        List<String> names = Lists.newArrayList(applicationContext.getBeanDefinitionNames());
        for (String string : names) {
            log.info("beans : {}", string);    
        }
        
    }

    @Test
    public void shouldPropLoaded() {
        log.info(" ++++   {}", applicationSettings);
        Assertions.assertThat(applicationSettings).isNotNull();
        
        log.info(" ++++   {}", applicationInfo);
        Assertions.assertThat(applicationInfo).isNotNull();
        
    }

}
