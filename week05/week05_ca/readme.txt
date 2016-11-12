StreamMessage format
	teamId: string
	podId: long //positive
	note: string
	callback: string
	image_type: string
	image length: int
	image: byte[]

Upload form fields
	teamId: string
	podId: long //positive
	note: string
	callback: string
	image: byte[]
	

callback format:
	GET /....?podId=XXX&ackId=XXX

Resources
	jdbc: jdbc/ca3_pod - for acknowledgement see ca3_epod.sql for table schema
	connection factory: jms/connectionFactory
	queue: jms/delivery
	scheduled executor: concurrent/myThreadPool
	custom: config/imgDir (string)
