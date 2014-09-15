package pl.java.scalatech.config.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class MyOwnIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().withDetail("test de javamind", "Super green").withDetail("test database", "OK call in 10ms").build();
    }
}