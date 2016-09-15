import boto.dynamodb
import boto.sqs
from boto.sqs.connection import SQSConnection
from boto.sqs.message import Message

class remoteWorker:
	def fetchTasks(self):
		rs = self.sqs_connection.get_messages()
		if rs and rs[0]:
			message = rs[0]
			message = message.get_body()
			print message
			message = message.split('|')
			print message
			if len(message) < 2:
				return False
			self.taskId = message[0]
			self.task = message[1:]
			self.message = rs[0]
			return True
		return False
	
	def checkDuplicates(self,taskId):
		try:
			item = self.conn.get_item(
			hash_key = str(taskId))
	        return item
	    except:
	    	return None
	
	def executeTask(self):
		return True
   
	def deleteTask(self):
		self.sqs_connection.delete_message(self.message)
	    return True
	   
	def isDuplicateRunning(self):
		item = self.checkDuplicates(self.taskId)
	    if item and item['taskStatus'] == 'P':
	        return True
	    return False
	    
	def updateStatus(self,status):
		print self.taskId
	    item = self.checkDuplicates(self.taskId)
	    if item:
	    	item['taskStatus'] = status
	        item.put()
	        return True        
	    return False
    
	def runMain(self):
		sqs_connection=boto.sqs.connect_to_region("us-west-2", aws_access_key_id='AKIAJ54BKJR3S4DBCBPA', aws_secret_access_key='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')
		#queue=sqs_connection.get_queue('requestqueue')
		conn = boto.dynamodb.connect_to_region("us-west-2", aws_access_key_id='AKIAJ54BKJR3S4DBCBPA', aws_secret_access_key='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')
		while(True):
			if self.fetchTasks() and not self.isDuplicateRunning():
			self.updateStatus('P')
	        if self.executeTask():
	        	self.updateStatus('F')
	        else:
	        	self.updateStatus('E')
	            print self.message
	            print self.taskId
	            print self.task
	            break
		print "All tasks completed successfully!"	    
	
		
if __name__ == "__main__":
 remote_worker = remoteWorker()
 remote_worker.runMain()
