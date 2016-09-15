#!/bin/bash

echo "start"
cc -pthread CPUBenchmark.c -o CPU
./CPU >> CPU.csv
cc -pthread MEMORYBenchmark.c -o MEMORY
./MEMORY >> MEMORY.csv
cc -pthread DISKBenchmark.c -o DISK
./DISK >> DISK.csv

echo "end"
