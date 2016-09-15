echo "Running shared memory experiments on 1 GB"
./64/gensort -a 10000000 /mount/raid/shmemtemp/data.txt
javac SharedMemory.java
echo "1 Thread"

java SharedMemory 1 > logfile1G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile1G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_1Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_1Thread.txt
echo "Done"
echo "2 Threads"

java SharedMemory 2 >> logfile1G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile1G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_2Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_2Thread.txt
echo "Done"
echo "4 Threads"

java SharedMemory 4 >> logfile1G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile1G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_4Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_4Thread.txt
echo "Done"
echo  "8 Threads"

java SharedMemory 8 >> logfile1G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile1G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_8Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-1GB_8Thread.txt
echo "10 GB Done!"

echo "Running shared memory experiments on 10 GB"
./64/gensort -a 100000000 /mount/raid/shmemtemp/data.txt

echo "1 Thread"
java SharedMemory 1 > logfile10G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile10G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_1Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_1Thread.txt
echo "Done!"

echo "2 Threads"
java SharedMemory 2 >> logfile10G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile10G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_2Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_2Thread.txt
echo "Done!"

echo "4 Threads"
java SharedMemory 4 >> logfile10G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile10G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_4Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_4Thread.txt
echo "Done!"

echo  "8 Threads"
java SharedMemory 8 >> logfile10G.txt
./64/valsort /mount/raid/shmemtemp/sortedData.txt >>logfile10G.txt
head -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_8Thread.txt
tail -10 /mount/raid/shmemtemp/sortedData.txt >> Sort-shared-memory-10GB_8Thread.txt
echo "10 GB Done!"

