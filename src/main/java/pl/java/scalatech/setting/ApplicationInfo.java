package pl.java.scalatech.setting;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author przodownik
 * Module name :    poc
 * Creating time :  26 maj 2014
 */
@Data
@Component
@ConfigurationProperties(value="info")
public class ApplicationInfo extends AbstractSettings {
    private String artifact;
    private String name;
    private String description;
    private String version;
}
