#!/bin/bash

ssh ssh root@178.62.39.63 -A 'cd /home/n-exchange/
git pull origin master
mvn clean package -Dserver=prod
cp /home/n-exchange/target/n-exchange-1.0.0.war /var/lib/tomcat7/webapps/ROOT.war
/etc/init.d/tomcat7 restart
'
