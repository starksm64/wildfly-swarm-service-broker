package org.wildfly.swarm.osbapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class BindResponse {

    private JsonNode credentials;
    private String syslogDrainUrl;
    private String routeServiceUrl;
    private List<Object> volumeMounts = new ArrayList<>();

    public BindResponse(JsonNode credentials) {
        this.credentials = credentials;
    }

    public JsonNode getCredentials() {
        return credentials;
    }

    public void setCredentials(JsonNode credentials) {
        this.credentials = credentials;
    }

    public String getSyslogDrainUrl() {
        return syslogDrainUrl;
    }

    public void setSyslogDrainUrl(String syslogDrainUrl) {
        this.syslogDrainUrl = syslogDrainUrl;
    }

    public String getRouteServiceUrl() {
        return routeServiceUrl;
    }

    public void setRouteServiceUrl(String routeServiceUrl) {
        this.routeServiceUrl = routeServiceUrl;
    }

    public List<Object> getVolumeMounts() {
        return volumeMounts;
    }

    public void setVolumeMounts(List<Object> volumeMounts) {
        this.volumeMounts = volumeMounts;
    }
}
