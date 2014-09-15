package pl.java.scalatech.config.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


@Component
public class CepHealth  implements HealthIndicator {

    @Override
    public Health health() {
         return Health.down(new RuntimeException("Erreur lors de l'execution"))
                .withDetail("test sauvegarde", "KO pb disque")
                .build();
    }

}
