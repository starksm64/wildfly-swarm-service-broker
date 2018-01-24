package org.wildfly.swarm.osbapi.model;

public class ProvisionResponse {
    private String dashboardUrl;
    private String operation;

    public ProvisionResponse() {
    }

    public ProvisionResponse(String dashboardUrl) {
        this(dashboardUrl, null);
    }

    public ProvisionResponse(String dashboardUrl, String operation) {
        this.dashboardUrl = dashboardUrl;
        this.operation = operation;
    }


    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public void setDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
