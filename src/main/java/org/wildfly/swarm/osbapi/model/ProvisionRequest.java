package org.wildfly.swarm.osbapi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ProvisionRequest {

    private String organizationId;
    private String spaceId;
    private UUID serviceId;
    private UUID planId;
    private Map<String, String> parameters = new HashMap<>();

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

    public void putParameter(String name, String value) {
        parameters.put(name, value);
    }
}
