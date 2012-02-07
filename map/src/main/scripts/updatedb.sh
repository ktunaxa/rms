export SQLTS="`date +%Y%m%d-%H%M`"
sudo -u postgres pg_dump -v -f referral-backup-$SQLTS.sql referral
cd ../map
mvn -P db process-resources
cd ../deploy
sudo -u postgres psql -d referral <referral-backup-$SQLTS.sql
cd ../map
mvn -Pupdatedb process-resources
cd ../deploy
