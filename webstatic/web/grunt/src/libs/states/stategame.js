function StateGame() {

	this.commandReceiver;

	

	this.create = function() {
		this.commandReceiver = new CommandReceiver();
		this.commandReceiver.create();
	};

	this.update = function() {
		 this.commandReceiver.update();
	};

};
