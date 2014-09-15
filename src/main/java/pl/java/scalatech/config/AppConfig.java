package pl.java.scalatech.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.ComponentScan.Filter;
@Configuration
@ComponentScan(basePackages = { "pl.java.scalatech.service", "pl.java.scalatech.web.controller", "pl.java.scalatech.component" }, useDefaultFilters = false, includeFilters = {
        @Filter(Service.class), @Filter(Component.class) })
@EnableTransactionManagement
@Import({ ApplicationConfigurations.class,MongoDBConfig.class,MongoRepositoryConfig.class,PropConfig.class,WebConfig.class})
public class AppConfig {

    
}