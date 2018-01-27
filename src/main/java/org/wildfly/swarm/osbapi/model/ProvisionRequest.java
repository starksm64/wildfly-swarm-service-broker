package org.wildfly.swarm.osbapi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProvisionRequest {

    @JsonProperty("organization_guid")
    private String organizationId;
    @JsonProperty("space_guid")
    private String spaceId;
    @JsonProperty("service_id")
    private UUID serviceId;
    @JsonProperty("plan_id")
    private UUID planId;
    private Map<String, Object> parameters = new HashMap<>();
    private ObjectNode context;

    public ProvisionRequest() {
    }

    public ProvisionRequest(UUID serviceId, UUID planId, String organizationId, String spaceId) {
        this.serviceId = serviceId;
        this.planId = planId;
        this.organizationId = organizationId;
        this.spaceId = spaceId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
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

    public void putParameter(String name, String value) {
        parameters.put(name, value);
    }

    public ObjectNode getContext() {
        return context;
    }

    public void setContext(ObjectNode context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "ProvisionRequest{" +
                "organizationId='" + organizationId + '\'' +
                ", spaceId='" + spaceId + '\'' +
                ", serviceId=" + serviceId +
                ", planId=" + planId +
                ", parameters=" + parameters +
                ", context=" + context +
                '}';
    }
}
