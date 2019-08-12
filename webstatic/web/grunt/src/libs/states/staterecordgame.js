function StateRecordGame() {

	this.recordReceiver;

	

	this.create = function() {
		
		this.recordReceiver = new RecordReceiver();
		this.recordReceiver.create();

	};

	this.update = function() {
		 this.recordReceiver.update();
	};

};
