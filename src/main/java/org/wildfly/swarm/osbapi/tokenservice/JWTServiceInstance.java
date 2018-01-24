package org.wildfly.swarm.osbapi.tokenservice;

import java.util.Map;

public class JWTServiceInstance {
    private String id;
    private String domain;
    private int ttl;

    public JWTServiceInstance(String id, String domain) {
        this(id, domain, 30);
    }
    public JWTServiceInstance(String id, String domain, int ttl) {
        this.id = id;
        this.domain = domain;
        this.ttl = ttl;
    }

    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String generateToken(Map<String, Object> params) {
        return "{}";
    }
}
