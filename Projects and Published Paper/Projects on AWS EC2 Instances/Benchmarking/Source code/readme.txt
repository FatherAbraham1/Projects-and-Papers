Step A: Create and connect to EC2 t2.micro instance:
----------------------------------------------------
1. Create an instance of Amazon EC2 t2.micro instance.
2. Download the .pem permission file
3. Open a terminal and go to the folder location of .pem file (e.g. EC2.pem)
	$cd /path
4. Get the public DNS of EC2 instance(e.g. ec2-user@ec2-52-36-50-111.us-west-2.compute.amazonaws.com )
5. Create connection to the instance by executing following command :
	$chmod 400 EC2.pem 
	$ssh -i "EC2.pem" ec2-user@ec2-52-36-50-111.us-west-2.compute.amazonaws.com 
6. To get essential devvelopment tools on your instance, execute following command:
	$sudo yum groupinstall "Development Tools" 

Step B: Transfer your files on instance:
----------------------------------------
1. Transfer all files using SCP command as:
	$scp -i /path/EC2.pem CPUBenchmark.c ec2-user@ec2-52-36-50-111.us-west-2.compute.amazonaws.com
						OR
1. Open terminal and execute to install filezilla:
	$sudo apt-get update 
	$sudo apt-get install filezilla 
2. After installing filezilla, make a connection to EC t2.micro in filezilla using same permission file and public DNS.

Files to transfer : 
	CPUBenchmark.c	DISKBenchmark.c	MEMORYBenchmark.c
	batch.sh		I.pdf


Step C: Run the Benchmark Script:
--------------------------------
1. Go to terminal from Step A. Execute following command to add permission for executing to your script:
	$chmod u+x batch.sh 
2. Execute the following commands to run script:
	$./batch.sh 
This program will run for more than 20 minutes to run as it contains time constraints.

Individual Benchmark instructions:
----------------------------------
1. CPU Benchmark:
-----------------
This benchmark will run atleast for 20 minutes. Hence check the output on screen  after 20+ minutes.
Commands:
	$gcc CPUBenchmark.c -o -CPU -pthread
	$./CPU
2. MEMORY Benchmark:
--------------------
Commands:
	$gcc MEMORYBenchmark.c -o -MEMORY -pthread
	$./MEMORY
3. DISK Benchmark:
------------------
This benchmark will need a file "I.pdf". You can place any file instead of I.pdf but rename it to "I.pdf". The file must be more than 1024*1024*10 bytes. i.e. 10MB. Keep both DISKBenchmark.c and I.pdf in same folder.
Commands:
	$gcc DISKBenchmark.c -o -DISK -pthread
	$./DISK

