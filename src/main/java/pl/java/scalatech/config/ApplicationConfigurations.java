package pl.java.scalatech.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import pl.java.scalatech.setting.ApplicationInfo;
import pl.java.scalatech.setting.ApplicationSettings;

@Configuration
@EnableConfigurationProperties({ApplicationSettings.class,ApplicationInfo.class})
public class ApplicationConfigurations {
    
   
    
}


