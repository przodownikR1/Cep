package pl.java.scalatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.codecentric.boot.admin.config.EnableAdminServer;
@Configuration
@EnableAdminServer
@Profile("dev")
public class AdminConfig {

}
