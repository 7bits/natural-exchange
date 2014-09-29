#!/bin/bash

ssh root@178.62.39.63 -A '
cd /home/n-exchange/www
git pull origin master
mvn clean package -Dserver=prod -Dmaven.test.skip=true
sudo rm /var/lib/tomcat7/webapps/ROOT.war
sudo cp /home/n-exchange/www/target/n-exchange-1.0.0.war /var/lib/tomcat7/webapps/ROOT.war
sudo /etc/init.d/tomcat7 restart
'
