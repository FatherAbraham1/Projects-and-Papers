#Final
sudo apt-get update
echo "start Raid 0"
sudo apt-get install mdadm
sudo mdadm --create --verbose /dev/md0 --level=0 --name=MY_RAID --raid-devices=2 /dev/xvdf /dev/xvdg
sudo mkfs.ext4 -L MY_RAID /dev/md0
sudo mkdir -p /mount/raid
sudo mount LABEL=MY_RAID /mount/raid
echo "raid 0 Done!"

sudo apt-get install openjdk-7-jdk
echo ' export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.79.x86_64/jre' >> ~/.bashrc
echo ' export PATH=$PATH:$JAVA_HOME/bin ' >> ~/.bashrc
echo 'export _JAVA_OPTIONS="-Xmx28g"' >> ~/.bashrc 
source .bashrc 
sudo apt-get install openjdk-7-jdk
echo "Done!" 

echo "Generating 1GB data.txt"
chmod 777 64/gensort
chmod 777 64/valsort
sudo mkdir /mount/raid/shmemtemp
sudo chmod 777 /mount/raid/shmemtemp
./64/gensort -a 10000000 /mount/raid/shmemtemp/data.txt
echo "Done!"

echo "Running shared memory 1 GB"
javac SharedMemory.java
java SharedMemory > logfile1G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile1G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB.txt
echo "1 GB Done!"

echo "Generating 10GB data.txt"
./64/gensort -a 100000000 /mount/raid/shmemtemp/data.txt
echo "Done!"

echo "Running shared memory"
java SharedMemory > logfile10G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB.txt
echo "10 GB Done!"
