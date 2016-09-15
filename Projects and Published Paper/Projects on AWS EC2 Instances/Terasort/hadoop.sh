sudo yum update
sudo yum install python-pip
sudo pip install pssh
sudo yum install xdotool
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u73-b02/jdk-8u73-linux-x64.tar.gz
tar -xvzf jdk-8u73-linux-x64.tar.gz
ln -s jdk1.8.0_73 jdk

wget http://mirror.reverse.net/pub/apache/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz
tar -xvzf hadoop-2.7.2.tar.gz
ln -s hadoop-2.7.2 hadoop

wget http://d3kbcqa49mib13.cloudfront.net/spark-1.6.0-bin-hadoop2.6.tgz
tar -xvzf spark-1.6.0-bin-hadoop2.6.tgz
ln -s spark-1.6.0-bin-hadoop2.6 spark

wget http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz
tar -xvzf scala-2.11.8.tgz
ln -s scala-2.11.8 scala 

echo 'export PATH=~/hadoop/bin:~/hadoop/sbin:~/jdk/bin:~/scala/bin:~/spark/bin:$PATH' >> ~/.bashrc
echo 'export HADOOP_HOME=~/hadoop' >> ~/.bashrc
echo 'export HADOOP_CONF=~/hadoop/conf' >> ~/.bashrc
echo 'export JAVA_HOME=~/jdk' >> ~/.bashrc
echo 'export HADOOP_DATANODE_OPTS="-Xmx3g"' >> ~/.bashrc
echo 'export _JAVA_OPTIONS="-Xmx2g"' >> ~/.bashrc
echo 'export SCALA_HOME=~/scala' >> ~/.bashrc
echo 'export SPARK_HOME=~/spark' >> ~/.bashrc
source .bashrc

ssh-keygen -f ~/.ssh/id_rsa -t rsa -P ""
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
echo 'StrictHostKeyChecking=no' >> ~/.ssh/config
chmod 600 ~/.ssh/config
read -p 'Enter Public ip : ' ip
read -p 'Enter Private ip : ' privateip
echo $ip >> hadoop/etc/hadoop/masters
ssh-keyscan -H $privateip >> ~/.ssh/known_hosts

echo localhost > hadoop/etc/hadoop/slaves
sed -i 's@${JAVA_HOME}@'/home/ec2-user/jdk'@g' ~/hadoop/etc/hadoop/hadoop-env.sh

sed -i '/configuration/ d' ~/hadoop/etc/hadoop/core-site.xml 
cat > ~/hadoop/etc/hadoop/core-site.xml <<EOF
<configuration>
<property>
<name>hadoop.tmp.dir</name> 
<value>/mount/raid/tmp</value> 
</property>
<property>
<name>fs.defaultFS</name> 
<value>hdfs://$privateip:8020</value> 
</property>
</configuration>
EOF

sed -i '/configuration/ d' ~/hadoop/etc/hadoop/hdfs-site.xml 
cat > ~/hadoop/etc/hadoop/hdfs-site.xml <<EOF
<configuration>
<property>
<name>dfs.replication</name>
<value>1</value>
</property>
<property>
<name>dfs.permissions</name>
<value>false</value>
</property>
</configuration>
EOF
sed -i '/configuration/ d' ~/hadoop/etc/hadoop/yarn-site.xml 
cat > ~/hadoop/etc/hadoop/yarn-site.xml <<EOF
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
<property>
<name>yarn.resourcemanager.hostname</name>
<value>$privateip</value>
</property>
<property>
<name>yarn.nodemanager.aux-services</name>
<value>mapreduce_shuffle</value>
</property> 
<property>
<name>yarn.nodemanager.aux-services.mapreduce_shuffle.class</name>
<value>org.apache.hadoop.mapred.ShuffleHandler</value>
</property>
<property>
<description>The address of the scheduler interface.</description>
<name>yarn.resourcemanager.scheduler.address</name>
<value>\${yarn.resourcemanager.hostname}:8030</value>
</property>
<property>
<description>The address of the applications manager interface in the RM.</description>
<name>yarn.resourcemanager.address</name>
<value>\${yarn.resourcemanager.hostname}:8032</value>
</property>
<property>
<description>The http address of the RM web application.</description>
<name>yarn.resourcemanager.webapp.address</name>
<value>\${yarn.resourcemanager.hostname}:8088</value>
</property>
<property>
<name>yarn.resourcemanager.resource-tracker.address</name>
<value>\${yarn.resourcemanager.hostname}:8031</value>
</property>
<property>
<description>The address of the RM admin interface.</description>
<name>yarn.resourcemanager.admin.address</name>
<value>\${yarn.resourcemanager.hostname}:8033</value>
</property>
<property>
<name>yarn.nodemanager.vmem-check-enabled</name>
<value>false</value>
<description>Whether virtual memory limits will be enforced for containers</description>
</property>
<property>
<name>yarn.nodemanager.vmem-pmem-ratio</name>
<value>1</value>
<description>Ratio between virtual memory to physical memory when setting memory limits for containers</description>
</property>
</configuration>
EOF
cp ~/hadoop/etc/hadoop/mapred-site.xml.template ~/hadoop/etc/hadoop/mapred-site.xml
sed -i '/configuration/ d' ~/hadoop/etc/hadoop/mapred-site.xml 
cat > ~/hadoop/etc/hadoop/mapred-site.xml <<EOF
<configuration>
<property>
<name>mapreduce.jobtracker.address</name>
<value>$ip:8021</value>
</property>
<property>
<name>mapreduce.framework.name</name>
<value>yarn</value>
</property>
<property>
<name>mapreduce.map.memory.mb</name>
<value>2048</value>
</property>
<property>
<name>mapreduce.reduce.memory.mb</name>
<value>2048</value>
</property>
</configuration>
EOF
cp ~/spark/conf/spark-env.sh.template ~/spark/conf/spark-env.sh
cat > ~/spark/conf/spark-env.sh <<EOF
/home/ec2-user/jdk
export JAVA_HOME=/home/ec2-user/jdk  
export SPARK_PUBLIC_DNS=localhost  
export SPARK_MASTER_IP=$privateip
export SPARK_WORKER_MEMORY=2g
export SPARK_WORKER_DIR=/mount/raid/tmp
SPARK_JAVA_OPTS+=" -Dspark.local.dir=/mount/raid/spark -Dhadoop.tmp.dir=/mount/raid.tmp"
export SPARK_JAVA_OPTS
EOF
sudo chown -R ec2-user ~/hadoop
sudo chown -R ec2-user ~/scala
sudo chown -R ec2-user ~/spark

