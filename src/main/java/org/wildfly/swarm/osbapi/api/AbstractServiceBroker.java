package org.wildfly.swarm.osbapi.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.wildfly.swarm.osbapi.model.BindRequest;
import org.wildfly.swarm.osbapi.model.BindResponse;
import org.wildfly.swarm.osbapi.model.Catalog;
import org.wildfly.swarm.osbapi.model.LastOperationResponse;
import org.wildfly.swarm.osbapi.model.ProvisionRequest;
import org.wildfly.swarm.osbapi.model.ProvisionResponse;

@Path("/v2")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public abstract class AbstractServiceBroker {

    @Path("/catalog")
    @GET
    public Catalog getCatalog(@Context SecurityContext securityContex) {
        Catalog catalog = doGetCatalog(securityContex);
        return catalog;
    }


    @Path("/service_instances/{instanceId}/last_operation")
    @GET
    public LastOperationResponse getLastOperationStatus(@Context SecurityContext securityContext,
                                                 @PathParam("instanceId") String instanceId,
                                                 @QueryParam("service_id") String serviceId,
                                                 @QueryParam("plan_id") String planId,
                                                 @QueryParam("operation") String operation) throws Exception {
        LastOperationResponse response = doGetLastOperationStatus(securityContext, instanceId, serviceId, planId, operation);
        return response;
    }

    @Path("/service_instances/{instanceId}")
    @PUT
    public Response provisionService(@Context SecurityContext securityContext,
                                       @PathParam("instanceId") String instanceId,
                                       @QueryParam("accepts_incomplete") @DefaultValue("false") boolean acceptsIncomplete,
                                       ProvisionRequest request) throws Exception {
        Response response = doProvisionService(securityContext, instanceId, acceptsIncomplete, request);
        Response.status(Response.Status.BAD_REQUEST).build();
        return response;
    }

    @Path("/service_instances/{instanceId}/service_bindings/{bindingId}")
    @PUT
    public Response bindServiceInstance(@Context SecurityContext securityContext,
                                            @PathParam("instanceId") String instanceId,
                                     @PathParam("bindingId") String bindingId, BindRequest bindRequest) {
        Response response = doBindServiceInstance(securityContext, instanceId, bindingId, bindRequest);
        return response;
    }

    protected abstract Catalog doGetCatalog(SecurityContext securityContex);
    protected abstract LastOperationResponse doGetLastOperationStatus(SecurityContext securityContext,
                                                                      String instanceId,
                                                                      String serviceId,
                                                                      String planId,
                                                                      String operation) throws Exception;
    protected abstract Response doProvisionService(SecurityContext securityContext,
                                                             String instanceId,
                                                             boolean acceptsIncomplete,
                                                             ProvisionRequest request) throws Exception;
    protected abstract Response doBindServiceInstance(SecurityContext securityContext, String instanceId,
                                                          String bindingId, BindRequest bindRequest);
}
