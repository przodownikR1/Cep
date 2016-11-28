package pl.java.scalatech.web.controller.health;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HealthController {

    private final ApplicationContext applicationContext;

    public HealthController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping(value = "/api/appContext")
    ResponseEntity<List<String>> appContext() {
        List<String> names = newArrayList(applicationContext.getBeanDefinitionNames());
        names.sort((String s1, String s2) -> s1.compareTo(s2));
        log.info("beans : {}", names);
        return ResponseEntity.ok(names);
    }

}
