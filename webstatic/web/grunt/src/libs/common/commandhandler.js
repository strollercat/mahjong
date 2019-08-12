function CommandHandler() {

}
CommandHandler.prototype.listen = function(action, cns) {
	var body = {}, msg;
	if (action == 'angang') {
		body.code='mjAction';
		body.action = 104;
		body.card0 = cns[0];
		body.card1 = 0;
	} else if (action == 'gang') {
		body.code='mjAction';
		body.action = 104;
		body.card0 = cns;
		body.card1 = -1;
	} else if (action == 'peng') {
		body.code='mjAction';
		body.action = 102;
		body.card0 = -1;
		body.card1 = -1;
	} else if (action == 'chi') {
		body.code='mjAction';
		body.action = 103;
		body.card0 = cns[0];
		body.card1 = cns[1];
	} else if (action == 'guo' || action == 'bugen') {
		body.code='mjAction';
		body.action = 100;
		body.card0 = -1;
		body.card1 = -1;
	} else if (action == 'gen') {
		body.code='mjAction';
		body.action = 106;
		body.card0 = -1;
		body.card1 = -1;
	} else if (action == 'hu') {
		body.code='mjAction';
		body.action = 105;
		body.card0 = -1;
		body.card1 = -1;
	} else if (action == 'da') {
		body.code='mjAction';
		body.action = 101;
		body.card0 = cns;
		body.card1 = -1;
	} else if(action == 'ready'){
		body.code = 'ready';
	} else if(action == 'chatText'){
		body.code = 'chatText';
		body.text = cns;
	} else if(action == 'chatVoice'){
		body.code = 'chatVoice';
		body.localId = cns.localId;
		body.serverId = cns.serverId;
	}else if(action == 'bullet'){
		body.code = 'bullet';
		body.authorDir = cns[0];
		body.dir = cns[1];
		body.index = cns[2];
	}else if(action == 'dismiss'){
		body.code = 'dismiss';
		body.dismiss = cns;
	}else if(action == 'managed'){
		body.code ='managed';
	}else if(action == 'threeWaterShoot'){
		body.code = action;
		body.cns = cns;
	}else{
		return ;
	}
	msg = JSON.stringify(body);
	console.log('### send msg ' + msg);
	if(webSocketClient){
		webSocketClient.send(msg);
	}
}
