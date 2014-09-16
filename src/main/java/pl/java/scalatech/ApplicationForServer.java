package pl.java.scalatech;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import pl.java.scalatech.main.Application;
@Slf4j
public class ApplicationForServer extends SpringBootServletInitializer {
    private static final String DEV = "dev";
    
   
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {    
        return application.profiles(addDefaultProfile()).sources(Application.class);
    }
    
    private String addDefaultProfile() {
        String profile = System.getProperty("spring.profiles.default");
        if (profile != null) {
            log.info("+++                                     Running with Spring profile(s) : {}", profile);
            return profile;
        }
        log.warn("+++                                    No Spring profile configured, running with default configuration");
        return DEV;
    }

}
