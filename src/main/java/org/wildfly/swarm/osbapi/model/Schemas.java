
package org.wildfly.swarm.osbapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Schemas {

    @JsonProperty("service_instance")
    private ServiceInstance serviceInstance;
    @JsonProperty("service_binding")
    private ServiceBinding serviceBinding;

    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(ServiceInstance serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public ServiceBinding getServiceBinding() {
        return serviceBinding;
    }

    public void setServiceBinding(ServiceBinding serviceBinding) {
        this.serviceBinding = serviceBinding;
    }

}
