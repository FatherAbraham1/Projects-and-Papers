//	Ms. Sampada L. Pingale
//	A20361180

#include<stdio.h>
#include<math.h>
#include<stdlib.h>
#include<time.h>
#include<pthread.h>

#define BYTE 1
#define KBYTE 1024
#define MBYTE 1048576
#define MAX 1024*1024*100
clock_t start, end;
struct ThreadParams {
    int size, sequential, id, threads, iterations;
};
double threadTime[2];
void *memoryThread(void *p)
{
	struct ThreadParams params = *((struct ThreadParams*)p);
	//Declaring variables
	char *src,*dest,d;
	int i,j;
	src=(char *) malloc(MAX*sizeof (char));
	dest=(char *) malloc(MAX*sizeof (char));	

	//performing random OR sequential operations
	if(params.sequential==1)
	{
		//Sequential operations		
		start=clock();
		for(i=0;i<params.iterations;i++)
		{
			memcpy(dest+(i*params.size),src+(i*params.size),(params.size*sizeof(char)));
		}
		end=clock();
	}
	else
	{
		int location;
		//Random operations
		start=clock();
		for(i=0; i<params.iterations; i++)
		{
			memcpy(dest+((i*params.size)%(MAX-1)),src+(rand()%(MAX-1-params.size)),(sizeof(char)*params.size));
		}
		end=clock();
	}
	threadTime[params.id]=end-start;

	free(src);
	free(dest);

}

void readParams(struct ThreadParams *param, int size, int sequential, int id, int threads) {
	if(size==1){
		param->size=BYTE;
		param->iterations=1000000;
	}
	if(size==2)
	{
		param->size=KBYTE;
		param->iterations=10000;
	}
	if(size==3)
	{
		param->size=MBYTE;
		param->iterations=100;
	}
	param->id=id;
	param->threads=threads;
	param->sequential=sequential;
}


int main(){

	struct ThreadParams param[2];
	int seq, expCount=0, size, threadsCount, thread, threadID[2], j, operations, divisionFactor, mem;
	pthread_t threads[2];
	double diff, throughput, latency;
	printf("\nThreads\tBlock Size\tAccess Type\tThroughput(mbps)\tLatency(ms)");
	for(seq=0;seq<2;seq++)
	{
		for(size=1;size<=3;size++)
		{
			for(threadsCount=0;threadsCount<2;threadsCount++)
			{
				threadTime[0]=0.0;
				threadTime[1]=0.0;
				for (j=0; j<=threadsCount; j++)
				{
					readParams(&param[j], size, seq, j, threadsCount+1);
					thread = pthread_create(&threads[j], NULL, memoryThread, (void *) &param[j]);
				}
				for (j=0; j<=threadsCount; j++)
				{
					thread = pthread_join(threads[j], NULL);
				}
				if(size==1){
					mem=BYTE;
					operations=1000000;
					divisionFactor=1024*1024;
				}
				if(size==2)
				{
					mem=KBYTE;
					operations=10000;
					divisionFactor=1024;
				}
				if(size==3)
				{
					mem=MBYTE;
					operations=100;
					divisionFactor=1;
				}
				//Calculating average time taken by all threads
				diff=(threadTime[0]+threadTime[1])/(2*CLOCKS_PER_SEC*(threadsCount+1));
				//Calculating latency by dividing  
				latency=(diff*1000)/(operations*(threadsCount+1)*mem);
				throughput=(operations*(threadsCount+1)*mem)/(diff*1024*1024);
				//Displaying throughput and latency for all experiments
				printf("\n%d\t%d\t",(threadsCount+1),mem);
				if(seq==1)
				printf("Sequential\t");
				else
				printf("Random\t");
				printf("%lf\t%.20lf",throughput, latency);
			}
		}
	}
	printf("\nThank you!\n");
	return (1);

}
