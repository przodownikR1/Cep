package pl.java.scalatech.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.java.scalatech.config.AppConfig;

@Configuration
@ComponentScan(basePackages="pl.java.scalatech")
@EnableAutoConfiguration
@Import(AppConfig.class)
public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", System.getProperty("spring.profiles.default", "dev"));
        System.err.println("==================================");
        SpringApplication.run(Application.class, args);
        
    }
}
