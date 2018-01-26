package org.wildfly.swarm.osbapi.tokenservice;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.jboss.logging.Logger;

/**
 * The health check for the JWTService
 */
@Health
@ApplicationScoped
public class HealthEndpoint implements HealthCheck {
    private static final Logger log = Logger.getLogger(HealthEndpoint.class);

    @Override
    public HealthCheckResponse call() {
        log.debug("jwt-service-broker-check up");
        return HealthCheckResponse.named("jwt-service-broker-check")
                .up()
                .build();
    }
}
