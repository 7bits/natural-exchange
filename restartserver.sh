#!/bin/bash

ssh root@office.7bits.it -p 22522 -A 'cd /home/n-exchange/
git pull origin master
mvn package -Dserver=prod
cp /home/n-exchange/target/n-exchange-1.0.0.war /var/lib/tomcat7/webapps/ROOT.war
/etc/init.d/tomcat7 restart
'
