cd ~geosparc/ktunaxa
svn update
mvn -DskipTests clean install
cd ~geosparc/ktunaxa/deploy
mvn -P ktunaxa clean process-resources
sudo /etc/init.d/tomcat6 stop
sudo rm -rf /var/lib/tomcat-6/webapps/*.war
sudo rm -rf /var/lib/tomcat-6/webapps/probe /var/lib/tomcat-6/webapps/activiti-rest /var/lib/tomcat-6/webapps/bpm
sudo rm -rf /var/lib/tomcat-6/webapps/map
sudo rm -rf /var/lib/tomcat-6/webapps/alfresco
sudo rm -rf /var/lib/tomcat-6/temp /var/lib/tomcat-6/work
sudo cp /home/geosparc/ktunaxa/deploy/target/*.war /var/lib/tomcat-6/webapps
sudo /etc/init.d/tomcat6 start


