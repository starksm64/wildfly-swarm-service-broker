# wildfly-swarm-service-broker
Open Service Broker API framework for Wildfly Swarm

# Minishift setup
minishift profile set jwt-service-broker
minishift config set openshift-version v3.7.1
minishift config set memory 8GB
minishift config set cpus 3
minishift config set vm-driver virtualbox
minishift config set image-caching true
minishift addon enable admin-user

MINISHIFT_ENABLE_EXPERIMENTAL=y minishift start --service-catalog

eval $(minishift oc-env)
eval $(minishift docker-env)
oc login $(minishift ip):8443 -u admin -p admin

minishift console
oc whoami

oc new-project jwt-service-broker

# Service broker deployment
mvn clean package

docker build -t example/jwt-service-broker .

docker images | grep jwt-service-broker

oc process -f openshift/jwt-service-broker-template.yml --param-file=openshift/params.env | oc create -f -
service "jwt-sb" created
serviceaccount "jwt-sb" created
clusterrolebinding "jwt-sb" created
deploymentconfig "jwt-sb" created
route "jwt-sb-1338" created
secret "jwt-sb-auth-secret" created
clusterservicebroker "jwt-service-broker" created

# Clean up
oc delete route jwt-sb-1338
oc delete deploymentconfig jwt-sb
oc delete service jwt-sb
oc delete serviceaccount jwt-sb
oc delete clusterrolebindings.rbac.authorization.k8s.io jwt-sb
oc delete clusterservicebrokers jwt-service-broker

or

oc delete project jwt-service-broker
oc delete clusterrolebindings.rbac.authorization.k8s.io jwt-sb
oc delete clusterservicebrokers jwt-service-broker
