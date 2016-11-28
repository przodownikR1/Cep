package pl.java.scalatech.config;

import static java.util.Arrays.stream;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "pl.java.scalatech.web.controller" }, useDefaultFilters = false, includeFilters = { @Filter(Controller.class) })
public class WebConfig extends WebMvcConfigurerAdapter {
    private static final String DEV_PROFILE = "dev";
    private static final String PROD_PROFILE = "prod";
    @Autowired
    private Environment env;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("1500KB");
        factory.setMaxRequestSize("1500KB");
        return factory.createMultipartConfig();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Resource photo() {
        return new ClassPathResource("foto.jpg");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/js/").setCachePeriod(31556926);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("index.html").addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/");
        
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        mapSwaggerIfProfile(registry);
        
    }

    private void mapSwaggerIfProfile(ViewControllerRegistry registry) {
        stream(env.getActiveProfiles()).filter(p->DEV_PROFILE.equals(p)).findFirst()
        .ifPresent(t -> registry.addViewController("/").setViewName("index.html"));
        
        stream(env.getActiveProfiles()).filter(p->PROD_PROFILE.equals(p)).findFirst()
        .ifPresent(t -> registry.addViewController("/").setViewName("/health"));
    }
  
    @Autowired
    private ContentNegotiationManager contentNegotiationManager;

    /**
     * Content Negotiation
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        Map<String, MediaType> mediatypes = new HashMap<>();
        mediatypes.put("html", MediaType.TEXT_HTML);
        mediatypes.put("pdf", MediaType.parseMediaType("application/pdf"));
        mediatypes.put("png", MediaType.parseMediaType("application/png"));
        mediatypes.put("jpg", MediaType.parseMediaType("application/jpg"));
        mediatypes.put("xls", MediaType.parseMediaType("application/vnd.ms-excel"));
        mediatypes.put("xml", MediaType.APPLICATION_XML);
        mediatypes.put("json", MediaType.APPLICATION_JSON);
        
        configurer.mediaTypes(mediatypes);
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setContentNegotiationManager(contentNegotiationManager);
        return viewResolver;
}
    
   

    
}
