FROM registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift
ENV AB_JOLOKIA_OFF=true JAVA_APP_DIR=/deployments
EXPOSE 1338 8080 8778 9779
COPY target/jwt-service-broker-swarm.jar /deployments/
