
package org.wildfly.swarm.osbapi.model;

import java.io.IOException;

import javax.annotation.Generated;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonSerialize(using = ServiceInstance.Serializer.class)
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ServiceInstance {
    private static final ObjectMapper mapper = new ObjectMapper();
    private JsonNode create;

    public JsonNode getCreate() {
        return create;
    }

    public void setCreate(JsonNode create) {
        this.create = create;
    }

    protected static class Serializer extends JsonSerializer<ServiceInstance> {
        @Override
        public void serialize(ServiceInstance value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            ObjectNode node = mapper.createObjectNode();
            ObjectNode schema = node.putObject("create");
            schema.set("parameters", value.getCreate());
            mapper.writeValue(gen, node);
        }
    }

}
