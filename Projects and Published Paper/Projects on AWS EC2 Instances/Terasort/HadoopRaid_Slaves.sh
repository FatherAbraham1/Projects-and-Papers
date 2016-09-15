#update slaves node on this instance and master instance using:
#sudo nano hadoop/etc/hadoop/slaves
#Then execute this script
#sudo chmod 777 HadoopRaid.sh
#./HadoopRaid.sh



echo "start raid 0"
sudo yum install mdadm
sudo mdadm --create --verbose /dev/md0 --level=0 --name=MY_RAID --raid-devices=2 /dev/xvdb /dev/xvdc
sudo mkfs.ext4 -L MY_RAID /dev/md0
sudo mkdir -p /mount/raid
sudo mount LABEL=MY_RAID /mount/raid
echo "end"
sudo chown ec2-user -R /mount/
sudo chown ec2-user -R /mount/raid
mkdir /mount/raid/tmp
sudo chown ec2-user -R /mount/raid/tmp
cd
source .bashrc

