package pl.java.scalatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.AppConfig;
import pl.java.scalatech.entity.ResourceRestriction;

@Configuration
@EnableAutoConfiguration
public class ApplicationForServer {
    
    public static void main(String[] args) {
     SpringApplication app = new  SpringApplication(); 
     app.run(ApplicationForServer.class, args);
     
    }
    // @formatter:off
    @Bean
    ResourceRestriction start(){
        return   ResourceRestriction.builder()               
                .quota(200f)
                .rateByDay(0)
                .quotaByDay(3)
                .rateByWeek(0)
                .quotaByWeek(10)
                .rateByMount(0)
                .quotaByMount(50).countResource(0).build();
    }
   // @formatter:on

  

    @Configuration
    @Import(AppConfig.class)
    @ComponentScan(excludeFilters = @Filter({ Service.class, Configuration.class }))
    static class WebConfiguration {
    }


}
