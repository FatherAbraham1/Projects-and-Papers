//	Ms. Sampada L. Pingale
//	A20361180

#include<stdio.h>
#include<math.h>
#include<stdlib.h>
#include<time.h>
#include<pthread.h>

#define MAX 100000000

double iopsSamples[4][600],flopsSamples[4][600];
double iops[600],flops[600];
double totalFlops[4],totalIops[4];
void *threadIops(void *t)
{
	int intValue1,intValue2,intValue3,intValue4,intValue5,intValue6,intValue7,intValue8,intValue9,intValue0, i;
	for(i=0; i<MAX; i++)
	{
		intValue0 = 8 + 2;
		intValue1 = 3 - 2;
		intValue2 = 2 * 2;
		intValue3 = 3 / 2;
		intValue4 = 4 + 2;
		intValue5 = 5 - 2;
		intValue6 = 6 * 2;
		intValue7 = 7 / 2;
		intValue8 = 8 + 2;
		intValue9 = 9 - 2;
		intValue0 = 8 + 2;
		intValue1 = 3 - 2;
		intValue2 = 2 * 2;
		intValue3 = 3 / 2;
		intValue4 = 4 + 2;
		intValue5 = 5 - 2;
		intValue6 = 6 * 2;
		intValue7 = 7 / 2;
		intValue8 = 8 + 2;
		intValue9 = 9 - 2;
		intValue0 = 8 + 2;
		intValue1 = 3 - 2;
		intValue2 = 2 * 2;
		intValue3 = 3 / 2;
		intValue4 = 4 + 2;
		intValue5 = 5 - 2;
		intValue6 = 6 * 2;
		intValue7 = 7 / 2;
		intValue8 = 8 + 2;
		intValue9 = 9 - 2;
		intValue0 = 8 + 2;
		intValue1 = 3 - 2;
		intValue2 = 2 * 2;
		intValue3 = 3 / 2;
		intValue4 = 4 + 2;
		intValue5 = 5 - 2;
		intValue6 = 6 * 2;
		intValue7 = 7 / 2;
		intValue8 = 8 + 2;
		intValue9 = 9 - 2;
	}
	return NULL;
}


void *threadFlops(void *t)
{
	int i;
	float floatValue0,floatValue1,floatValue2,floatValue3,floatValue4,floatValue5,floatValue6,floatValue7,floatValue8,floatValue9;	
	for(i=0; i<MAX; i++)
	{
		floatValue0= 0.2 + 1.2;
		floatValue1= 0.1 - 1.2;
		floatValue2= 0.2 / 1.2;
		floatValue3= 0.3 * 1.2;
		floatValue4= 0.4 + 1.2;
		floatValue5= 0.5 - 1.2;
		floatValue6= 0.6 / 1.2;
		floatValue7= 0.7 * 1.2;
		floatValue8= 0.8 + 1.2;
		floatValue9= 0.9 - 1.2;
		floatValue0= 0.2 + 1.2;
		floatValue1= 0.1 - 1.2;
		floatValue2= 0.2 / 1.2;
		floatValue3= 0.3 * 1.2;
		floatValue4= 0.4 + 1.2;
		floatValue5= 0.5 - 1.2;
		floatValue6= 0.6 / 1.2;
		floatValue7= 0.7 * 1.2;
		floatValue8= 0.8 + 1.2;
		floatValue9= 0.9 - 1.2;
		floatValue0= 0.0 + 1.2;
		floatValue1= 0.1 - 1.2;
		floatValue2= 0.2 / 1.2;
		floatValue3= 0.3 * 1.2;
		floatValue4= 0.4 + 1.2;
		floatValue5= 0.5 - 1.2;
		floatValue6= 0.6 / 1.2;
		floatValue7= 0.7 * 1.2;
		floatValue8= 0.8 + 1.2;
		floatValue9= 0.9 - 1.2;
		floatValue0= 0.0 + 1.2;
		floatValue1= 0.1 - 1.2;
		floatValue2= 0.2 / 1.2;
		floatValue3= 0.3 * 1.2;
		floatValue4= 0.4 + 1.2;
		floatValue5= 0.5 - 1.2;
		floatValue6= 0.6 / 1.2;
		floatValue7= 0.7 * 1.2;
		floatValue8= 0.8 + 1.2;
		floatValue9= 0.9 - 1.2;
	}

	return NULL;
}

void *threadCPUSamples(void *th)
{
//	clock_t start, end;

	time_t start, end;
	double total, diff;
   	int i=0;
	int id, intValue1,intValue2,intValue3,intValue4,intValue5,intValue6,intValue7,intValue8,intValue9,intValue0;
	float floatValue0,floatValue1,floatValue2,floatValue3,floatValue4,floatValue5,floatValue6,floatValue7,floatValue8,floatValue9;	
	int sampleCount,opsCount;
	id = *((int *) th);

	sampleCount=0;
	opsCount=0;
	total=0;
	//Running Integer operations for 600 Samples

	time(&start);
   	while(i!=1)
   	{
		intValue0 = 8 + 2;
		intValue1 = 3 - 2;
		intValue2 = 2 * 2;
		intValue3 = 3 / 2;
		intValue4 = 4 + 2;
		intValue5 = 5 - 2;
		intValue6 = 6 * 2;
		intValue7 = 7 / 2;
		intValue8 = 8 + 2;
		intValue9 = 9 - 2;
		intValue0 = 8 + 2;
		intValue1 = 3 - 2;
		intValue2 = 2 * 2;
		intValue3 = 3 / 2;
		intValue4 = 4 + 2;
		intValue5 = 5 - 2;
		intValue6 = 6 * 2;
		intValue7 = 7 / 2;
		intValue8 = 8 + 2;
		intValue9 = 9 - 2;
		intValue0 = 8 + 2;
		intValue1 = 3 - 2;
		intValue2 = 2 * 2;
		intValue3 = 3 / 2;

		time(&end);

		diff = difftime(end, start);
		opsCount=opsCount+27;
		if(diff>=1)
		{
			time(&start);
			// Recording sample
			iopsSamples[id][sampleCount]=opsCount;
			sampleCount++;
			opsCount=6;
			total=total+diff;
			if(total>=600||sampleCount==600)
				i=1;
		}
   	}	

	printf("\nSeconds :%f\t id: %d",total,id);
	
	sampleCount=0;
	opsCount=0;
	total=0;
	i=0;
	//Running Floating point operations for 600 Samples
	time(&start);
   	while(i!=1)
   	{
		floatValue0= 0.2 + 1.2;
		floatValue1= 0.1 - 1.2;
		floatValue2= 0.2 / 1.2;
		floatValue3= 0.3 * 1.2;
		floatValue4= 0.4 + 1.2;
		floatValue5= 0.5 - 1.2;
		floatValue6= 0.6 / 1.2;
		floatValue7= 0.7 * 1.2;
		floatValue8= 0.8 + 1.2;
		floatValue9= 0.9 - 1.2;
		floatValue0= 0.2 + 1.2;
		floatValue1= 0.1 - 1.2;
		floatValue2= 0.2 / 1.2;
		floatValue3= 0.3 * 1.2;
		time(&end);
	   	diff = difftime(end,start);
		opsCount=opsCount+17;
		if(diff>=1)
		{
			// Recording number of operations
			time(&start);
			flopsSamples[id][sampleCount]=opsCount+6;
			sampleCount++;
			opsCount=0;
			total=total+diff;
			if(total>=600||sampleCount==600)
			{
				i=1;
			}
		}
   	}
	printf("\nSeonds :%f\tThreadId: %d", total, id);
	return NULL;
}

int main()
{
	int i,j, concurrentThreads=1, threadID[7],id=0;
	clock_t start, end;
	double total, diff;

	//Calculating IOPS and FLOPS
	for(i=1; i<=3; i++)
	{
		printf("\n\nExecuting %d thread(s)\n",concurrentThreads);
		pthread_t threads[concurrentThreads];
		int thread, j;

		//Initializing Threads for IOPS
		start=clock();
		for (j=0; j<concurrentThreads; j++)
		{
			printf("\nThread %d integer", id);
			threadID[id]=id;
			thread = pthread_create(&threads[j], NULL, threadIops, (void *) &threadID[id]);
			id++;
		}
		//Executing IOPS threads simultaneously
		for (j=0; j<concurrentThreads; j++)
		{
			thread = pthread_join(threads[j], NULL);
		}
		end=clock();
		diff=(double)(end-start)/(CLOCKS_PER_SEC*concurrentThreads);
		totalIops[i-1]=40*(double)MAX/diff;
		id=0;
		//Initializing Threads for FLOPS
		start=clock();
		for (j=0; j<concurrentThreads; j++)
		{
			printf("\nThread %d float", id);
			threadID[id]=id;
			thread = pthread_create(&threads[j], NULL, threadFlops, (void *) &threadID[id]);
			id++;
		}
		//Executing FLOPS threads simultaneously
		for (j=0; j<concurrentThreads; j++)
		{
			thread = pthread_join(threads[j], NULL);
		}
		end=clock();
		diff=(double)(end-start)/(CLOCKS_PER_SEC*concurrentThreads);
		totalFlops[i-1]=40*(double)MAX/diff;

		concurrentThreads=concurrentThreads*2;
	}

	//Displaying IOPS, GIOPS, FLOPS, GFLOPS
	printf("\nThreads\tFLOPS\tGFLOPS\tIOPS\tGIOPS");
	int threadCount=1;
	for(i=0; i<3; i++){		
		totalFlops[i]=totalFlops[i];
		totalIops[i]=totalIops[i];
		printf("\n%d\t%lf\t%lf\t%lf\t%lf",threadCount, totalFlops[i], totalFlops[i]/1000000000, totalIops[i], totalIops[i]/1000000000);
		threadCount=threadCount*2;
	}

	int sampleThread,sampleId[4];
	//Collecting 600 Samples	
	pthread_t sampleThreads[4];
	printf("\nTaking 600 Samples\n(600*2seconds=20 minutes)");
	//Executing threads simultaneously
	for (j=0; j<4; j++)
	{
		sampleId[j]=j;
		sampleThread = pthread_create(&sampleThreads[j], NULL, threadCPUSamples, (void *) &sampleId[j]);
	}
	//Waiting for all threads to join
	for (j=0; j<4; j++)
	{
		sampleThread = pthread_join(sampleThreads[j], NULL);
	}

	//Displaying 600 IOPS and GFLOPS
	printf("\n600 Samples :\nSample\tIOPS\tGIOPS\tFLOPS\tGFLOPS");
	for(j=0;j<600;j++)
	{
		iops[j]=0.0;
		flops[j]=0.0;
		printf("\n%d",j+1);
		for(i=0; i<4; i++)
		{
			iops[j]=iops[j]+iopsSamples[i][j];
			flops[j]=flops[j]+flopsSamples[i][j];
		}
		printf("\t%lf\t%lf\t%lf\t%lf",iops[j],iops[j]/1000000000,flops[j],flops[j]/1000000000);
	}

	printf("\nThank you!\n");
	return 1;
}

