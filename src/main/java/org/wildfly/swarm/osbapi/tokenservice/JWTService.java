package org.wildfly.swarm.osbapi.tokenservice;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.jose4j.jwk.RsaJsonWebKey;
import org.wildfly.swarm.osbapi.api.AbstractServiceBroker;
import org.wildfly.swarm.osbapi.model.BindRequest;
import org.wildfly.swarm.osbapi.model.BindResponse;
import org.wildfly.swarm.osbapi.model.Catalog;
import org.wildfly.swarm.osbapi.model.LastOperationResponse;
import org.wildfly.swarm.osbapi.model.LastOperationState;
import org.wildfly.swarm.osbapi.model.Plan;
import org.wildfly.swarm.osbapi.model.ProvisionRequest;
import org.wildfly.swarm.osbapi.model.ProvisionResponse;
import org.wildfly.swarm.osbapi.model.Schemas;
import org.wildfly.swarm.osbapi.model.Service;
import org.wildfly.swarm.osbapi.model.ServiceBinding;
import org.wildfly.swarm.osbapi.model.ServiceInstance;

/**
 * A JWTService broker example
 */
@ApplicationScoped
public class JWTService extends AbstractServiceBroker {
    private static final String BINDING_SCHEMA_PATH = "/json/jwt-service-binding-schema.json";
    private static final String INSTANCE_SCHEMA_PATH = "/json/jwt-service-instance-schema.json";
    private static final Logger log = Logger.getLogger(JWTService.class);
    private Service tokenService;
    private Throwable lastOpError;
    private JWTServiceInstance instance;

    /**
     * Lists the supported service instances. Currently there is only one that is configured in {@link #init()}.
     *
     * @see #init()
     *
     * @param securityContex
     * @return The catalog for the JWTService
     */
    protected Catalog doGetCatalog(SecurityContext securityContex) {
        log.info("doGetCatalog");
        Catalog catalog = new Catalog();
        if(tokenService == null) {
            init();
        }
        catalog.getServices().add(tokenService);
        return catalog;
    }

    protected LastOperationResponse doGetLastOperationStatus(SecurityContext securityContext, String instanceId, String serviceId, String planId, String operation) throws Exception {
        log.infof("doGetLastOperationStatus, lastOpError=%s", lastOpError);
        LastOperationResponse response;
        if(lastOpError != null) {
            response = new LastOperationResponse(LastOperationState.FAILED, lastOpError.getMessage());
        } else {
            response = new LastOperationResponse(LastOperationState.SUCCEEDED, "Success for domain: "+instance.getDomain());
        }
        return response;
    }

    protected Response doProvisionService(SecurityContext securityContext, String instanceId, boolean acceptsIncomplete, ProvisionRequest request) throws Exception {
        log.infof("doProvisionService, instanceId=%s, request=%s", instanceId, request);
        Optional<String> optDomain = request.getParameter("domain");
        if(!optDomain.isPresent()) {
            log.error("No domain parameter specified");
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing domain paramter").build();
        }
        instance = new JWTServiceInstance(instanceId, optDomain.get());
        ProvisionResponse response = new ProvisionResponse();
        response.setOperation("create");
        log.info("Provisioned JWTServiceInstance");
        return Response.ok(response).build();
    }

    /**
     * Binding the service creates the signer key and returns the public key in the credentials of the response.
     * @param securityContext
     * @param instanceId
     * @param bindingId
     * @param request
     * @return
     */
    protected Response doBindServiceInstance(SecurityContext securityContext, String instanceId, String bindingId, BindRequest request) {
        log.infof("doBindServiceInstance, instanceId=%s, request=%s", instanceId, request);
        Optional<Integer> ttlOpt = request.getParameter("ttl");
        Integer ttl = ttlOpt.orElse(30);
        instance.setTtl(ttl);
        Response response;
        try {
            KeyPair instanceKey = generateKeyPair(2048);
            RSAPublicKey publicKey = (RSAPublicKey) instanceKey.getPublic();
            RsaJsonWebKey jwk = new RsaJsonWebKey(publicKey);
            String json = jwk.toJson();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jwkObj = mapper.readTree(json);
            BindResponse bindResponse = new BindResponse(jwkObj);
            response = Response.ok(bindResponse).build();
        } catch (Exception e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            log.errorf(e, "Failed to bind service, domain=%s", instance.getDomain());
        }
        log.infof("Boound JWTServiceInstance, domain=%s", instance.getDomain());
        return response;
    }

    @Path("/jwt/generate/{serviceId}")
    @GET
    public Response generateToken() {
        // Only one token service, in future map serviceId to service instance
        return Response.ok().build();
    }

    /**
     * Generate a new RSA keypair.
     * @param keySize - the size of the key
     * @return KeyPair
     * @throws NoSuchAlgorithmException on failure to load RSA key generator
     */
    private static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair;
    }

    /**
     * Initialize the tokenService information needed for the catalog.
     */
    private synchronized void init() {
        // Base free service
        tokenService = new Service();
        tokenService.setBindable(true);
        tokenService.setDescription("A JWT token generator service");
        tokenService.setId(UUID.randomUUID().toString());
        tokenService.setName("JWT Token Service");
        tokenService.setTags(Collections.singletonList("JWT"));
        tokenService.setRequires(Collections.singletonList("volume_mount"));
        tokenService.setPlanUpdateable(false);
        //tokenService.setMetadata();
        Plan basePlan = new Plan();
        basePlan.setId(UUID.randomUUID().toString());
        basePlan.setFree(true);
        basePlan.setName("base-free-plan");
        basePlan.setDescription("Basic JWT generation support");
        Schemas schemas = new Schemas();
        ObjectMapper mapper = new ObjectMapper();

        ServiceInstance serviceInstance = new ServiceInstance();
        // Read the schema in from the INSTANCE_SCHEMA_PATH resource
        try(InputStream is = getClass().getResourceAsStream(INSTANCE_SCHEMA_PATH)) {
            JsonNode schema = mapper.readTree(is);
            serviceInstance.setCreate(schema);
        } catch (IOException e) {
            e.printStackTrace();
        }
        schemas.setServiceInstance(serviceInstance);

        ServiceBinding serviceBinding = new ServiceBinding();
        // Read the schema in from the BINDING_SCHEMA_PATH resource
        try(InputStream is = getClass().getResourceAsStream(BINDING_SCHEMA_PATH)) {
            JsonNode schema = mapper.readTree(is);
            serviceBinding.setCreate(schema);
        } catch (IOException e) {
            e.printStackTrace();
        }
        schemas.setServiceBinding(serviceBinding);

        basePlan.setSchemas(schemas);
        tokenService.setPlans(Collections.singletonList(basePlan));
    }
}
