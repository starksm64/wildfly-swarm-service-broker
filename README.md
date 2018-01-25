# wildfly-swarm-service-broker
Open Service Broker API framework for Wildfly Swarm

# Minishift setup
Either perform:

```bash
minishift profile set jwt-service-broker
minishift config set openshift-version v3.7.1
minishift config set memory 8GB
minishift config set cpus 3
minishift config set vm-driver virtualbox
minishift config set image-caching true
minishift addon enable admin-user
```

or run the `openshift/setup-minishift.sh` script

# Start minishift with the service-catalog
MINISHIFT_ENABLE_EXPERIMENTAL=y minishift start --service-catalog

# Configure command line environment
eval $(minishift oc-env)
eval $(minishift docker-env)
oc login $(minishift ip):8443 -u admin -p admin
oc whoami

# Launch console app
minishift console

# Create a new project
oc new-project jwt-service-broker

# Service broker deployment
mvn clean package

docker build -f openshift/Dockerfile -t example/jwt-service-broker .

docker images | grep jwt-service-broker

oc process -f openshift/jwt-service-broker-template.yml --param-file=openshift/params.env | oc create -f -

output should be something like:
```bash
service "jwt-sb" created
serviceaccount "jwt-sb" created
clusterrolebinding "jwt-sb" created
deploymentconfig "jwt-sb" created
route "jwt-sb-1338" created
secret "jwt-sb-auth-secret" created
clusterservicebroker "jwt-service-broker" created
```

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

# TODO
* Update template to support the use of https
