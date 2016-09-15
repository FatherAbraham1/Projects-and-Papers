//	Ms. Sampada L. Pingale
//	A20361180

#include<stdio.h>
#include<math.h>
#include<stdlib.h>
#include<time.h>
#include<pthread.h>
#include<string.h>
double threadTime[2];
#define MAX 1024*1024*1000
#define BYTE 1
#define KBYTE 1024
#define MBYTE 1048576

#define fileName "I.pdf"

char BUFFER[1024*1024];
double latency[2], throughput[2];
FILE *file;
struct ThreadParams
{
 int sequential, iterations, read, size, id, threads;
};
void readParams(struct ThreadParams *param, int size, int sequential, int id, int read, int threads) {
	if(size==1){
		param->size=BYTE;
		param->iterations=10000;
	}
	if(size==2)
	{
		param->size=KBYTE;
		param->iterations=10;
	}
	if(size==3)
	{
		param->size=MBYTE;
		param->iterations=2;
	}
	param->id=id;
	param->threads=threads;
	param->sequential=sequential;
}


void *diskThread(void *p)
{
	struct timeval start, end;
	struct ThreadParams params = *((struct ThreadParams*)p);
	int i;
	FILE *file;
	char FILE_NAME[30];
	if(params.read!=1)
	{
		//Write operations

		for(i=0;i<1024*1024;i++)
			BUFFER[i]='X';
		if(params.sequential==1)
		{
			// Sequential Write
			sprintf(FILE_NAME,"SEQUENTIAL_%dBYTES_%d.txt",params.size,params.id);
			file=fopen(FILE_NAME,"w");
			gettimeofday(&start, NULL);
			for(i=0;i<params.iterations;i++);
			{
				fwrite(BUFFER,params.size,sizeof(char),file);
			}
			gettimeofday(&end, NULL);
		}
		else{
			// Random Write
			sprintf(FILE_NAME,"RANDOM_%dBYTES_%d.txt",params.size,params.id);
			file=fopen(FILE_NAME,"w");
			gettimeofday(&start, NULL);
			long location;
			for(i=0;i<params.iterations;i++);
			{
				location=(rand()% (1024*1024/params.size))*params.size;
				fseek(file, location, SEEK_SET);
				fwrite(BUFFER,params.size,sizeof(char),file);
			}
			gettimeofday(&end, NULL);
			fclose(file);
		}
	}
	else{
		//Read operations
		file=fopen(fileName,"r");
		fseek(file, 0L, SEEK_END);
		long location, fileLength= ftell (file);
		char data[params.size];
		fseek(file, 0L, SEEK_SET);	
		if(params.sequential==1)
		{
			// Sequential Read
			gettimeofday(&start, NULL);
			for(i=0; i<params.iterations; i++);
			{
				fread(data, params.size, 1, file);
			}
			gettimeofday(&end, NULL);
		}
		else{
			// Random Read
			gettimeofday(&start, NULL);
			for(i=0;i<params.iterations;i++);
			{
				location=rand() % (fileLength-params.size);
				fseek(file, location, SEEK_SET);
				fread(data, params.size, 1, file);
			}
			gettimeofday(&end, NULL);
		}
		fclose(file);
	}

	latency[params.id]=((double)(end.tv_usec - start.tv_usec)/1000000)+((double)(end.tv_sec - start.tv_sec));

}
 
int main()
{
	int read, size, seq, threadsCount, id, thread, mem, operations, j;
	struct ThreadParams param[2];
	pthread_t threads[2];
	printf("\nThreads\tAccess\tMode\tIterations\tSize\tThroughput\tLatency");
	double latencyMs, speed;
	for(read=0;read<2;read++)
	{
		for(seq=0;seq<2;seq++)
		{
			for(size=1;size<=3;size++)
			{
				for(threadsCount=0;threadsCount<2;threadsCount++)
				{
					latency[0]=0.0; latency[1]=0.0;
					throughput[0]=0.0; throughput[1]=0.0;
					for (j=0; j<=threadsCount; j++)
					{
						readParams(&param[j], size, seq, j, read, threadsCount+1);
						thread = pthread_create(&threads[j], NULL, diskThread, (void *) &param[j]);
					}
					for (j=0; j<=threadsCount; j++)
					{
						thread = pthread_join(threads[j], NULL);
					}
					latencyMs=(latency[0]+latency[1])/(threadsCount+1);
					speed= (double) ((param[0].iterations)*(param[0].size))/(latencyMs*1000000);

					printf("\n%d\t",threadsCount+1);
					if(read==1)
						printf("Read\t");
					else
						printf("Write\t");
					if(seq==1)
						printf("Sequential\t");
					else
						printf("Random\t");
					
					printf("%d\t%d\t%lf\t%lf",param[0].iterations,param[0].size, speed, latencyMs);
				}
			}
		}	
	}     
	printf("\nThank you!\n");
	return 0;
}


