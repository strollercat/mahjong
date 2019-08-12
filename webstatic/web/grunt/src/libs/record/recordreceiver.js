function RecordReceiver(){
	
	
}
RecordReceiver.msgQueue;


RecordReceiver.prototype = util.inherit(CommandReceiver.prototype);
RecordReceiver.prototype.constructor = RecordReceiver;

RecordReceiver.prototype.create = function(){
	this.gameSprite = new RecordGameSprite();
	this.gameSprite.create();
	this.gameSprite.layout();
	this.gameSprite.clear();
	game.gameSprite = this.gameSprite;
	
	this.done = true;
	
	
}
RecordReceiver.prototype.roomNotice = function(msg) {
	this.gameSprite.clear();

	this.author = msg.author;
	this.gameSprite.topSprite.group.visible = true;
	this.gameSprite.topSprite.setRoomId(msg.roomId);
	
	var users = msg.users,len = msg.users.length, i, user, dir, userSprite;
	for (i = 0; i < len; i++) {
		user = users[i];
		dir = this.absToRel(user['dir']);
		userSprite = this.gameSprite.userSpriteArr[dir];
		userSprite.group.visible = true;
		userSprite.setName(user.name);
		userSprite.setScore(user.score);
		userSprite.changeHeadUrl(user.url);
		userSprite.ready(user.ready);
		userSprite.headUs.boxSprite.inputEnabled = true;
	}
	this.gameSprite.readyBtnSprite.group.visible = false;
	this.gameSprite.ajustUserToRoomPosition();
	
	if(msg.order == 0){
		this.gameSprite.inviteBtnSprite.group.visible = true;
		this.gameSprite.leaveBtnSprite.group.visible = true;
	}
	
	
	this.gameSprite.chatBtnSprite.group.visible = true;
	this.gameSprite.audioBtnSprite.group.visible = true;
	
	
	this.roomInfo =  msg.roomInfo;
	this.gameSprite.roomInfoSprite.group.visible = true;
	this.gameSprite.roomInfoSprite.setRoomInfo(this.roomInfo);

//	this.setLeftTime(msg);
	
	this.gameSprite.recordGameIndex.group.visible= true;
	this.gameSprite.recordGameIndex.setText('第'+(this.gameSprite.gameIndex+1)+'局');
	
//	this.gameSprite.ipTintTextSprite.group.visible = false;

	this.gameSprite.cardsSpriteArr[0].clickEnabled(false);

}


RecordReceiver.prototype.gameStart = function(msg) {
	CommandReceiver.prototype.gameStart.apply(this, arguments);
	this.gameSprite.recordGameIndex.group.visible = false;
	
	this.gameSprite.cardsSpriteArr[0].clickEnabled(false);
}



RecordReceiver.prototype.update = function(){
	
	if(!this.done){
		return ;
	}
	
	if(!this.gameSprite.canNext){
		return ;
	}
	if (RecordReceiver.msgQueue[this.gameSprite.gameIndex].length == this.gameSprite.actionIndex) {
		return;
	}
	var msg = RecordReceiver.msgQueue[this.gameSprite.gameIndex][this.gameSprite.actionIndex++];
	var code = msg.code;
	console.log('### msg code is '+code);
	if (code == 'roomNotice') {
		this.roomNotice(msg);
	} else if (code == 'enterRoom') {
		this.enterRoom(msg);
	} else if (code == 'leaveRoom') {
		this.leaveRoom(msg);
	} else if (code == 'userReady') {
		this.userReady(msg);
	} else if (code == 'gameStart') {
		this.gameStart(msg);
	} else if (code == 'gameNotice') {
		this.gameNotice(msg);
	} else if (code == 'mo') {
		this.mo(msg);
	} else if (code == 'da') {
		this.da(msg);
	} else if (code == 'next') {
		this.next(msg);
	} else if (code == 'chipenggang') {
		this.chiPengGang(msg);
	} else if (code == 'dahua') {
		this.dahua(msg);
	} else if (code == 'gen') {
		this.gen(msg);
	} else if (code == 'hu') {
		this.hu(msg);
	} else if (code == 'result') {
		this.result(msg);
	} else if (code == 'offline') {
		this.offline(msg);
	} else if (code == 'destroy') {
		this.destroy();
	} else if (code == 'no') {
		this.no(msg);
	} else if(code == 'online'){
		this.online(msg);
	} else if(code == 'chatText'){
		this.chatText(msg);
	} else if(code == 'chatVoice'){
		this.chatVoice(msg);
	} else if(code == 'netError'){
		if(util.env != 'dev'){
			this.gameSprite.netErrorBoxSprite.group.visible = true;
			game.world.bringToTop(this.gameSprite.netErrorBoxSprite.group);
			var url ='home.html';
			url += '?recommend='+util.getQueryString('recommend');
			window.location.href = url;
		}
	}else if(code == 'bullet'){
		this.bullet(msg);
	}else{
		return ;
	}
	this.gameSprite.cardsSpriteArr[0].clickEnabled(false);
	this.gameSprite.canNext = false;
}	