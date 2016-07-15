package pl.java.scalatech.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "pl.java.scalatech.setting" }, useDefaultFilters = false, includeFilters = { @Filter(Component.class) })
@Slf4j
public class PropConfig {
    @Configuration
    @PropertySources({ @PropertySource(value = { "file:./conf/cep.properties" }), @PropertySource(value = { "file:./conf/cep-dev.properties" }) })
    @Profile({ "dev", "dev_test"})
    static class PropertiesLoaderForDev {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            log.info("+++     dev profile launch");
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    @Configuration
    @PropertySources({ @PropertySource(value = { "file:./conf/cep.properties" }), @PropertySource(value = { "file:./conf/cep-prod.properties" }) })
    @Profile("prod")
    static class PropertiesLoaderForProd {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            log.info("+++        prod profile launch");
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

}