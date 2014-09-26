package pl.java.scalatech.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    public String getCurrentAuditor() {
        return "TODO";
    }
}