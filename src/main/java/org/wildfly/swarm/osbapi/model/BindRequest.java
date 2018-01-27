package org.wildfly.swarm.osbapi.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BindRequest {

    @JsonProperty("service_id")
    private UUID serviceId;
    @JsonProperty("plan_id")
    private UUID planId;
    @JsonProperty("bind_resource")
    private BindResource bindResource;
    private Map<String, Object> parameters = new HashMap<>();
    private ObjectNode context;

    public BindRequest() {

    }

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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public <T> Optional<T> getParameter(String name) {
        T param = (T) parameters.get(name);
        return Optional.ofNullable(param);
    }

    public ObjectNode getContext() {
        return context;
    }

    public void setContext(ObjectNode context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "BindRequest{" +
                "serviceId=" + serviceId +
                ", planId=" + planId +
                ", bindResource=" + bindResource +
                ", parameters=" + parameters +
                '}';
    }

    public static class BindResource {
        @JsonProperty("app_guid")
        private String appId;
        private String route;

        public BindResource() {
        }
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

        @Override
        public String toString() {
            return "BindResource{" +
                    "appId='" + appId + '\'' +
                    ", route='" + route + '\'' +
                    '}';
        }
    }

}