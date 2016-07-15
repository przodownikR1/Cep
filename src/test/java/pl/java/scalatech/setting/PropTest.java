package pl.java.scalatech.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.ApplicationConfigurations;
import pl.java.scalatech.config.PropConfig;

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
        assertThat(applicationSettings).isNotNull();
        
        log.info(" ++++   {}", applicationInfo);
        assertThat(applicationInfo).isNotNull();
        
    }

}
