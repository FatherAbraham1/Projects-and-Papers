import boto.dynamodb
import boto.sqs
from boto.sqs.message import Message

class remoteClient:
	sqs_connection=boto.sqs.connect_to_region("us-west-2", aws_access_key_id='AKIAJ54BKJR3S4DBCBPA', aws_secret_access_key='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')
	conn = boto.sqs.connect_to_region("us-west-2", aws_access_key_id='AKIAJ54BKJR3S4DBCBPA', aws_secret_access_key='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')
	queue=connection.create_queue('requestqueue')
	message=Message()
	file=open("sleep_0_10000.txt","r")
	taskId=0
	for f in file:
	        x=f.split(" ")
	        message.set_body(str(taskId) + "|" +  str(x[1]))
	        queue.write(message)
	        taskId=taskId+1
	start = time.time()
	def checkDuplicate(self,taskId):
		try:
			item = self.dynamoDB_obj.get_item(
	        hash_key=taskId)
	        return item
	    except:
	        return None
        
	def insertTask(self,taskId,task_status):
		item = self.checkDuplicate(taskId)
	    if item and (item['taskStatus'] == 'C' or item['taskStatus'] == 'P') :
	       	return False
	            
	    data ={'taskStatus':task_status,'try_count':0}
	    item = self.conn.new_item(
	    hash_key = taskId,
	    attrs= data
        )
	    item.put()
	    return True

    def validateData(self,taskId,taskDesc):
        return True
    
        
    def check_status(self,taskId):
    	item = self.checkDuplicates(taskId)
        message = ''
        if item:
            task_status = item['taskStatus']
            if task_status == 'P':
            	message = 'task running'
            elif task_status=='C':
                message = 'task Created'
            elif task_status == 'E':
                message = 'Error'
            elif task_status == 'F':
                message = 'task finished'
        else:
            message = 'Job does not exists'

        return message
    	
	end = time.time()
	print "Total time taken:"
	print end-start
  
