package pl.java.scalatech.config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "pl.java.scalatech.setting" }, useDefaultFilters = false, includeFilters = { @Filter(Component.class) })
// @Profile(value={"dev","test"})
public class PropConfig {

    @Bean
    public PropertySource<?> yamlPropertySourceLoader() throws IOException {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        PropertySource<?> applicationYamlPropertySource = loader.load("application.yml", new ClassPathResource("application.yml"), "default");
        return applicationYamlPropertySource;

    }

}