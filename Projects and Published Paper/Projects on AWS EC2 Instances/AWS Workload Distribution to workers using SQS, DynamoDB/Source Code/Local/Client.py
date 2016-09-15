import Queue
import threading
import time
import argparse


workload_file=' '
Queue_name=' '
no_of_threads=' '

exitFlag = 0
parser = argparse.ArgumentParser(description="HOW TO RUN THE PROGRAM")
parser.add_argument('-s', '--Queue',required=True)
parser.add_argument('-t','--ThreadNum',type=int,required=True)
parser.add_argument('-w','--FileName',required=True)
args=parser.parse_args()
workload_file= args.FileName
Queue_name=args.Queue
no_of_threads=args.ThreadNum

class myThread (threading.Thread):
    def __init__(self, threadID, name, q):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.q = q
    def run(self):
        print "Starting " + self.name
        process_data(self.name, self.q)
        print "Exiting " + self.name

def process_data(threadName, q):
    while not exitFlag:
        queueLock.acquire()
        if not workQueue.empty():
            data = q.get()
            queueLock.release()
            time.sleep(float(data))
        else:
            queueLock.release()

threadList = []
for i in range(no_of_threads):
    threadList.append("THREAD-%d"% i)
nameList=[]
file = open(workload_file,"r")
for f in file:
    x = f.split(" ")
    nameList.append(x[1])

queueLock = threading.Lock()
workQueue = Queue.Queue()
threads = []
threadID = 1

# Fill the queue
queueLock.acquire()
for word in nameList:
    workQueue.put(word)
queueLock.release()

# Create new threads
start=time.time()
for tName in threadList:
    thread = myThread(threadID, tName, workQueue)
    thread.start()
    threads.append(thread)
    threadID += 1

# Wait for queue to empty
while not workQueue.empty():
    pass

# Notify threads it's time to exit
exitFlag = 1

# Wait for all threads to complete
for t in threads:
    t.join()
end=time.time()
print "TOTAL TIME TAKEN"
print end-start
