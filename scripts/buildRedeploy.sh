#!/bin/sh

echo "************ UNDEPLOYING *******************"
asadmin undeploy library
echo "************ BUILDING **********************"
mvn package
echo "************ DEPLOYING *********************"
asadmin deploy target/library.war
