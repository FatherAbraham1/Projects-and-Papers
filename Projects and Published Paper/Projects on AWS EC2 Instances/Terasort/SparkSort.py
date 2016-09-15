from __future__ import print_function
import sys
from pyspark import SparkContext
from pyspark import SparkConf

if __name__ == "__main__":
    with open('out.txt','w+') as f:
   	if len(sys.argv) != 2:
        	print("Exiting. Log: <error>", error=sys.stderr)
        	exit(-1)
    	config=(SparkConf()
		.setAppName("SparkSort")
		.set("spark.executor.memory","2g")
		.set("spark.memory", "2g")
		.set("spark.default.parallelism","16"))

    	context = SparkContext(conf=config)
    	lines = context.textFile(sys.argv[1], 1,use_unicode=False).map(lambda x: (x[0:10],x[10:]))
    	recordCount =lines.sortByKey(True,1).map(lambda x: (x[0] + x[1].strip('\n'))+'\r') 
	recordCount.saveAsTextFile("Sort-spark-10g")
    context.stop()
