export BAKTS="`date +%Y%m%d-%H%M`"
cp /opt/tomcat-6/webapps/map.war ./map.$BAKTS.bak.war
cd ~geosparc/ktunaxa
svn update
mvn -U -DskipTests -Dcheckstyle.skip clean install
cd ~geosparc/ktunaxa/deploy
mvn -P ktunaxa clean process-resources
sudo /etc/init.d/tomcat6 stop
sudo rm -rf /opt/tomcat-6/webapps/*.war
sudo rm -rf /opt/tomcat-6/webapps/probe /opt/tomcat-6/webapps/activiti-rest /opt/tomcat-6/webapps/bpm
sudo rm -rf /opt/tomcat-6/webapps/map
sudo rm -rf /opt/tomcat-6/webapps/alfresco
sudo rm -rf /opt/tomcat-6/temp /opt/tomcat-6/work
sudo cp /home/geosparc/ktunaxa/deploy/target/*.war /opt/tomcat-6/webapps
cd ../map
mvn -Pupdatedb process-resources
sudo /etc/init.d/tomcat6 start

