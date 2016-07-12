package pl.java.scalatech;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import pl.java.scalatech.config.AppConfig;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class ApplicationForServer extends SpringBootServletInitializer {
    private static final String DEV_PROFILE = "dev";

    public static void main(String[] args) {
        SpringApplication.run(ApplicationForServer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        SpringApplicationBuilder app = application.profiles(addDefaultProfile()).sources(WebConfiguration.class);
        return app;
    }

    @Configuration
    @Import(AppConfig.class)
    @ComponentScan(excludeFilters = @Filter({ Service.class, Configuration.class }))
    static class WebConfiguration {
    }

    private String addDefaultProfile() {
        String profile = System.getProperty("spring.profiles.default");
        if (profile != null) {
            log.info("+++  Running with Spring profile(s) : {}", profile);
            return profile;
        }
        log.warn("+++   No Spring profile configured, running with default configuration");
        return DEV_PROFILE;
    }

}
