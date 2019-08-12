function WebSocketClient() {
	this.socket;
	this.serverUrl;
	this.msgQueue = new Array();
	
	this.closeTime = 0;
}

WebSocketClient.prototype.connect = function() {
	var ishttps = 'https:' == document.location.protocol ? true : false;
	if (ishttps) {
		this.serverUrl = 'wss://' + location.hostname + '/web/api/websocket'
				+ '?roomId='+util.getQueryString('roomId');
	} else {
		this.serverUrl = 'ws://' + location.hostname + '/web/api/websocket'
				+ '?roomId='+util.getQueryString('roomId');
	}

	if(util.env == 'dev'){
		this.serverUrl = this.serverUrl+('&code=' + util.getQueryString('code'));
	}
	this.socket = new WebSocket(this.serverUrl);
	var webSocketClient = this;

	this.socket.onopen = function() {
		console.log('on open!');
	}

	this.socket.onclose = function(evt) {
		console.log('onclose '+evt.code);
		webSocketClient.closeTime +=1;
		console.log('Connection close! '+webSocketClient.closeTime);
		if(webSocketClient.closeTime<2){
			webSocketClient.connect();
		}else{
			webSocketClient.msgQueue.push({code:'netError'});
		}
	}

	this.socket.onmessage = function(evt) {
		console.log('### receive data:  '+evt.data);
		webSocketClient.msgQueue.push(JSON.parse(evt.data));
	}
	
	this.socket.onerror = function(evt){
//		console.log('onerror '+evt.data);
//		webSocketClient.msgQueue.push({code:'netError'});
	}

};
WebSocketClient.prototype.disconnect = function() {
	this.socket.close();
};
WebSocketClient.prototype.send = function(msg) {
	this.socket.send(msg);
};



