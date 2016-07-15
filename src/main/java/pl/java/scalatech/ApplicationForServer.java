package pl.java.scalatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import de.codecentric.boot.admin.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.AppConfig;

@Slf4j
@Configuration
@EnableAdminServer
@EnableAutoConfiguration
public class ApplicationForServer extends SpringBootServletInitializer {
    

    //private static ZooKeepPublisher zooKeepPublisher = null;
    
    public static void main(String[] args) {
     SpringApplication app = new  SpringApplication();
     listenerAdded(app);
     app.run(ApplicationForServer.class, args);
     
    }

    private static void listenerAdded(SpringApplication app) {
        app.addListeners(event -> {
             try {
                 //zooKeepPublisher.start();
                 System.out.println("\n\t==================================================\n\tCEP Server Started\n\t==================================================\n");
             } catch (Exception ex) {
                 System.err.println("Failed to register with ZooKeeper. Shutting down. Stack trace follows.");
                 ex.printStackTrace(System.err);
                 System.exit(-1);
             }                       
         });
         app.addListeners(event -> {
             System.out.println("\n\t==================================================\n\tCEP Server Started\n\t==================================================\n");
             /*if(zooKeepPublisher!=null) {
                 zooKeepPublisher.stop();
                                                        
             }*/
         });
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        SpringApplicationBuilder app = application.sources(WebConfiguration.class);              
        return app;
    }

    @Configuration
    @Import(AppConfig.class)
    @ComponentScan(excludeFilters = @Filter({ Service.class, Configuration.class }))
    static class WebConfiguration {
    }


}
