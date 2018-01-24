package org.wildfly.swarm.osbapi.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BindRequest {

    private UUID serviceId;
    private UUID planId;
    private BindResource bindResource;
    private Map<String, String> parameters = new HashMap<>();

    public BindRequest(UUID serviceId, UUID planId) {
        this.serviceId = serviceId;
        this.planId = planId;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public UUID getPlanId() {
        return planId;
    }

    public void setPlanId(UUID planId) {
        this.planId = planId;
    }

    public BindResource getBindResource() {
        return bindResource;
    }

    public void setBindResource(BindResource bindResource) {
        this.bindResource = bindResource;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public <T> Optional<T> getParameter(String name) {
        T param = (T) parameters.get(name);
        return Optional.ofNullable(param);
    }

    public static class BindResource {
        private String appId;
        private String route;

        public BindResource(String appId, String route) {
            this.appId = appId;
            this.route = route;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }
    }

}