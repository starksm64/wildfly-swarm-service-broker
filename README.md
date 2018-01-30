# wildfly-swarm-service-broker
This is an example project of implenting the Open Service Broker API(OSBAPI) framework for Wildfly Swarm.
It consists of an abstract JAX-RS base class that implements the OSBAPI endpoints along with an example
concrete JWTService implementation that manages JWTServiceInstance(s).

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
`MINISHIFT_ENABLE_EXPERIMENTAL=y minishift start --service-catalog`

# Configure command line environment
```bash
eval $(minishift oc-env)
eval $(minishift docker-env)
oc login $(minishift ip):8443 -u admin -p admin
oc whoami
```

# Launch console app
`minishift console`

# Create a new project
`oc new-project jwt-service-broker`

# Service broker deployment
```bash
mvn clean package
docker build -f openshift/Dockerfile -t example/jwt-service-broker .
docker images | grep jwt-service-broker
oc process -f openshift/jwt-service-broker-template.yml --param-file=openshift/params.env | oc create -f -
```

output for the `oc process` should be something like:
```bash
service "jwt-sb" created
serviceaccount "jwt-sb" created
clusterrolebinding "jwt-sb" created
deploymentconfig "jwt-sb" created
route "jwt-sb-1338" created
secret "jwt-sb-auth-secret" created
clusterservicebroker "jwt-service-broker" created
```

# Check the clusterservicebrokers and clusterserviceclasses
To verify that the service broker was registered:
```bash
[wildfly-swarm-service-broker 1288]$  oc get clusterservicebrokers
NAME                      AGE
jwt-service-broker        41s
template-service-broker   4h
```

To verify that the jwt-service-broker catalog was read, issue the following:
```bash
[wildfly-swarm-service-broker 1333]$ oc get clusterserviceclasses --all-namespaces -o custom-columns=NAME:.metadata.name,DISPLAYNAME:spec.externalMetadata.displayName
NAME                                   DISPLAYNAME
38e57637-7701-4e3e-bd01-a9690130bffa   JWT TokenService 
e34630c7-0255-11e8-8c1c-1213db363b8d   Jenkins
e34cf908-0255-11e8-8c1c-1213db363b8d   Pipeline Build Example
e3536afa-0255-11e8-8c1c-1213db363b8d   MongoDB
e3589dbe-0255-11e8-8c1c-1213db363b8d   MariaDB
e35bd30f-0255-11e8-8c1c-1213db363b8d   MySQL
e3603594-0255-11e8-8c1c-1213db363b8d   PostgreSQL
e365fdf7-0255-11e8-8c1c-1213db363b8d   CakePHP + MySQL
e374b6d8-0255-11e8-8c1c-1213db363b8d   Django + PostgreSQL
e37abe8a-0255-11e8-8c1c-1213db363b8d   Dancer + MySQL
e37f0186-0255-11e8-8c1c-1213db363b8d   Node.js + MongoDB
e3831ee8-0255-11e8-8c1c-1213db363b8d   Rails + PostgreSQL
e387ca8f-0255-11e8-8c1c-1213db363b8d   Jenkins (Ephemeral)
```
_Note: it can take a couple of minutes for the JWT TokenService entry to show up._

# Clean up
```bash
oc delete route jwt-sb-1338
oc delete deploymentconfig jwt-sb
oc delete service jwt-sb
oc delete serviceaccount jwt-sb
oc delete clusterrolebindings.rbac.authorization.k8s.io jwt-sb
oc delete clusterservicebrokers jwt-service-broker
```

or
```bash
oc delete project jwt-service-broker
oc delete clusterrolebindings.rbac.authorization.k8s.io jwt-sb
oc delete clusterservicebrokers jwt-service-broker
```

# TODO
* Update template to support the use of https

# Debug

MINISHIFT_ENABLE_EXPERIMENTAL=y minishift start --service-catalog --show-libmachine-logs -v5
