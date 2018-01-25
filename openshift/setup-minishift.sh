#!/usr/bin/env bash

# Create a profile based on openshift 3.7.1
minishift profile set jwt-service-broker
minishift config set openshift-version v3.7.1
minishift config set memory 8GB
minishift config set cpus 3
minishift config set vm-driver virtualbox
minishift config set image-caching true
minishift addon enable admin-user
