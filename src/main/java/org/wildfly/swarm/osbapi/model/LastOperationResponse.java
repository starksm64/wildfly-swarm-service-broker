package org.wildfly.swarm.osbapi.model;

public class LastOperationResponse {
    private LastOperationState state;
    private String description;

    public LastOperationResponse() {
    }

    public LastOperationResponse(LastOperationState state, String description) {
        this.state = state;
        this.description = description;
    }

    public LastOperationState getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }
}
