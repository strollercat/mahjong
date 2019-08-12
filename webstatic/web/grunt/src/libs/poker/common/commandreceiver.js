function CommandReceiver() {
	this.gameSprite;
	
	this.author; //玩家的绝对坐标
	this.roomInfo; //roomInfo信息
	
	this.done = true;
	this.shootArr =[];
}

CommandReceiver.prototype.create = function() {
	this.gameSprite = new GameSprite();
	this.gameSprite.create();
	this.gameSprite.layout();
	this.gameSprite.clear();
	game.gameSprite = this.gameSprite;
}

CommandReceiver.prototype.update = function() {

	if (!this.done) {
		return;
	}
	if (webSocketClient.msgQueue.length == 0) {
		return;
	}
	var msg = webSocketClient.msgQueue[0];
	var code = msg.code;
//	console.log('### msg code is '+code);
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
	} else if (code == 'result') {
		this.result(msg);
	} else if (code == 'offline') {
		this.offline(msg);
	} else if (code == 'destroy') {
		this.destroy(msg);
	} else if(code == 'online'){
		this.online(msg);
	} else if(code == 'chatText'){
		this.chatText(msg);
	} else if(code == 'chatVoice'){
		this.chatVoice(msg);
	} else if(code == 'netError'){
		this.netError(msg);
//		if(util.env != 'dev'){
//			this.gameSprite.netErrorBoxSprite.group.visible = true;
//			game.world.bringToTop(this.gameSprite.netErrorBoxSprite.group);
//			var url ='home.html';
//			url += '?recommend='+util.getQueryString('recommend');
//			window.location.href = url;
//		}
	}else if(code == 'bullet'){
		this.bullet(msg);
	}else if(code == 'tint'){
		this.gameSprite.tintSprite.showTint(msg.tint);		
	}else if(code =='dismiss'){
		this.dismiss(msg);
	}else if(code == 'messagebox'){
		this.gameSprite.showMessageBox(msg.message,function(btn){
			btn.context.group.destroy();
		});
	}else if(code == 'roomStart'){
		this.roomStart(msg);
	}else if(code == 'threeWaterShoot'){
		this.threeWaterShoot(msg);
	}
	
	webSocketClient.msgQueue.shift();
}
CommandReceiver.prototype.absToRel = function(dir) {
	return (4 - this.author + dir) % 4;
}


CommandReceiver.prototype.dismiss = function(msg){
	var dismiss = msg.dismiss;
	var dir = this.absToRel(msg.dir);
	var name =  this.gameSprite.userSpriteArr[dir].getName();
	if(dismiss){
		this.gameSprite.requestDismissSprite.group.visible = true;
		game.world.bringToTop(this.gameSprite.requestDismissSprite.group);
		this.gameSprite.requestDismissSprite.setMessage([name+'请求解散房间','您是否同意?']);
	}else{
		this.gameSprite.requestDismissSprite.group.visible = false;
		this.gameSprite.showMessageBox([name+'拒绝解散房间','继续游戏!'],function(btn){
			btn.context.group.destroy();
		});
	}
}
CommandReceiver.prototype.roomNotice = function(msg) {
	this.gameSprite.clear();

	this.author = msg.author;
	this.shootArr = [];
	
	this.gameSprite.topSprite.group.visible = true;
	this.gameSprite.topSprite.setRoomId(msg.roomId);
	
	var users = msg.users,len = msg.users.length, i, user, dir, userSprite;
	for (i = 0; i < len; i++) {
		user = users[i];
		dir = this.absToRel(user['dir']);
		userSprite = this.gameSprite.userSpriteArr[dir];
		userSprite.fightInfo =  user.fightInfo;
		userSprite.fightInfo.dir = user['dir'];
		userSprite.fightInfo.authorDir = this.author;
		userSprite.fightInfo.commandHandler = this.gameSprite.commandHandler;
		userSprite.group.visible = true;
		userSprite.setName(user.name);
		userSprite.setScore(user.score);
		userSprite.changeHeadUrl(user.url);
		userSprite.ready(user.ready);
		userSprite.headUs.boxSprite.inputEnabled = true;
		if (!user.ready && user['dir'] == this.author) {
			this.gameSprite.readyBtnSprite.group.visible = true;
		}
	}
//	this.gameSprite.ajustUserToRoomPosition();

	
	if(msg.order == 0){
		this.gameSprite.inviteBtnSprite.group.visible = true;
		this.gameSprite.leaveBtnSprite.group.visible = true;
		this.gameSprite.leftTimeTextSprite.setLeftTime(msg.leftTime);
	}
	
	
	this.gameSprite.chatBtnSprite.group.visible = true;
	this.gameSprite.audioBtnSprite.group.visible = true;
	
	
	this.roomInfo =  msg.roomInfo;
	this.gameSprite.roomInfoSprite.group.visible = true;
	this.gameSprite.roomInfoSprite.setRoomInfo(this.roomInfo);


	this.gameSprite.menuSprite.group.visible = true;
	this.gameSprite.menuSprite.showAll();

	this.updateShareData(msg);
	
	
}


CommandReceiver.prototype.roomStart = function(msg) {

	
	var users = msg.users,len = msg.users.length, i, user, dir, userSprite;
	for (i = 0; i < len; i++) {
		user = users[i];
		dir = this.absToRel(user['dir']);
		userSprite = this.gameSprite.userSpriteArr[dir];
		userSprite.group.visible = true;
		userSprite.setScore(user.score);
		userSprite.zuan(false);
		userSprite.ready(user.ready);
		userSprite.headUs.boxSprite.inputEnabled = true;
		if (!user.ready && user['dir'] == this.author) {
			this.gameSprite.readyBtnSprite.group.visible = true;
		}
		
		
//		userSprite.huaUs.group.visible = false;
//		userSprite.huaUs.clearHua();
	}
	this.gameSprite.ajustUserToRoomPosition();

	
	this.gameSprite.resultSprite.clear();
	
	this.gameSprite.grayBackGround.visible = false;
	
//	this.gameSprite.clearCards();
//	this.gameSprite.infoSprite.group.visible = false;
//	this.gameSprite.whiteCardSprite.group.visible = false;
//	this.gameSprite.middleSprite.group.visible = false;
	
	
	this.gameSprite.chatBtnSprite.group.visible = true;
	this.gameSprite.audioBtnSprite.group.visible = true;
	
//	this.gameSprite.maimaSprite.clear();
	
//	for(i = 0;i< this.gameSprite.tuoguanSpriteArr.length;i++){
//		this.gameSprite.tuoguanSpriteArr[i].visible = false;
//	}
//	this.gameSprite.goonBtnSprite.group.visible = false;
	
//	this.gameSprite.menuSprite.dismissS.group.visible = true;
	this.gameSprite.menuSprite.showAll();
	
	for(var i =0 ;i< 4;i++){
		this.gameSprite.threeCardsArr[i].clear();
		this.gameSprite.threeCardsArr[i].group.visible = false;
	}
	
	this.gameSprite.cardsShootSprite.choose.clear();
	
	this.shootArr =[];
}



CommandReceiver.prototype.threeWaterShoot = function(msg) {

	if(!msg.legal){
		this.gameSprite.showMessageBox(['牌型不正确','请重新选择牌型'],function(btn){
			btn.context.group.destroy();
		});
	}else{
		this.shootArr[msg.dir] = true;
		if(msg.dir == this.author){
			this.gameSprite.cardsShootSprite.group.visible = false;
		}
		for(var i = 0;i< 4;i++){
			if(this.shootArr[i]){
				this.gameSprite.threeCardsArr[this.absToRel(i)].lpzSprite.visible = false;
			}
		}
		
	}
}





CommandReceiver.prototype.updatePersonal = function(roomInfo){
	if(roomInfo.name == 'hangzhouMajiang' || roomInfo.name == 'hongzhongMajiang' || roomInfo.name == 'guangdongMajiang'){
		this.gameSprite.middleSprite.dirSprite.group.visible = false;
		for(var i = 0;i< 4;i++){
			this.gameSprite.userSpriteArr[i].huaUs.group.visible = false;
		}
	}
	
	if(roomInfo.name == 'guangdongMajiang'){
		if(this.gameSprite.whiteCardSprite){
			this.gameSprite.whiteCardSprite.group.visible = false;
		}
	}
	if(roomInfo.name == 'hongzhongMajiang'){
		this.gameSprite.menuSprite.hide(3);
	}else{
		this.gameSprite.menuSprite.showAll();
	}
}

CommandReceiver.prototype.updateShareData = function(msg){
	if(game.updateShareData){
		game.updateShareData(msg);
	}
	this.updateShareDataOfDesc();
};
CommandReceiver.prototype.updateShareDataOfDesc = function(){
	if(game.updateShareDataOfDesc && this.roomInfo && this.roomInfo.playerNum >= 4){
		var playerNum = this.gameSprite.userSpriteSize();
		game.updateShareDataOfDesc(playerNum);
	}
};
CommandReceiver.prototype.processCandite = function(candite){
	var retArr = [];
	for(var i = 0;i< candite.length;i++){
		candite[i].cards.splice(0,1);
		retArr.push(candite[i].cards);
	}
	return retArr;	
}
CommandReceiver.prototype.updateCards = function(cards){
	
	
	
	this.gameSprite.cardsShootSprite.group.visible = true;
	for(var i = 0;i< cards.length;i++){
		if(cards[i].dir ==  this.author){
			this.gameSprite.cardsShootSprite.setCards(cards[i].i);
			if(cards[i].special){
				this.gameSprite.cardsShootSprite.choose.setSpecial(cards[i].special.name);
			}else{
				var candite = cards[i].candite;
				candite = this.processCandite(candite);
				this.gameSprite.cardsShootSprite.choose.setCandidate(candite);
			}
		}
		this.gameSprite.threeCardsArr[this.absToRel(cards[i].dir)].group.visible = true;
//		this.gameSprite.threeCardsArr[this.absToRel(cards[i].dir)].blackCardsArr[0].group.visible = false;
//		this.gameSprite.threeCardsArr[this.absToRel(cards[i].dir)].blackCardsArr[1].group.visible = false;
		this.gameSprite.threeCardsArr[this.absToRel(cards[i].dir)].lpzSprite.visible = true;
	}
//	this.gameSprite.cardsShootSprite.setCards(cns)
//	for(var i = 0;i< this.gameSprite.threeCardsArr.length;i++){
//		this.gameSprite.threeCardsArr[i].group.visible =true;
//		this.gameSprite.threeCardsArr[i].lpzSprite.visible =true;
//	}
		
}

CommandReceiver.prototype.gameNotice = function(msg) {
	this.gameSprite.clear();

	this.author = msg.author;

	this.gameSprite.topSprite.group.visible = true;
	this.gameSprite.topSprite.setRoomId(msg.roomId);

	var len = msg.users.length, i, user, dir, userSprite,shoot;
	for (i = 0; i < len; i++) {
		user = msg.users[i];
		dir = this.absToRel(user['dir']);
		userSprite = this.gameSprite.userSpriteArr[dir];
		userSprite.group.visible = true;
		userSprite.fightInfo =  user.fightInfo;
		userSprite.fightInfo.dir = user['dir'];
		userSprite.fightInfo.authorDir = this.author;
		userSprite.fightInfo.commandHandler = this.gameSprite.commandHandler;
		userSprite.setName(user.name);
		userSprite.setScore(user.score);
		userSprite.changeHeadUrl(user.url);
		userSprite.ready(false);
		userSprite.headUs.boxSprite.inputEnabled = true;
	}
	
	var shooted = false;
	for(var i = 0;i< msg.shooted.length;i++){
		dir = msg.shooted[i].dir;
		shoot = msg.shooted[i].shooted;
		this.shootArr[dir] = shoot;
		if(dir == this.author && shoot){
			shooted = true;
		}
	}
	if(shooted){
		for(var i = 0;i< 4;i++){
			this.gameSprite.threeCardsArr[i].group.visible = true;
			if(this.shootArr[i]){
				this.gameSprite.threeCardsArr[this.absToRel(i)].lpzSprite.visible = false;
			}
		}
	}else{
		this.updateCards(msg.cards);
	}
	

	this.gameSprite.chatBtnSprite.group.visible = true;
	this.gameSprite.audioBtnSprite.group.visible = true;
	
	this.roomInfo =  msg.roomInfo;
	this.gameSprite.roomInfoSprite.group.visible = true;
	this.gameSprite.roomInfoSprite.setRoomInfo(this.roomInfo);
	
	
	this.updateShareData(msg);
	
	this.gameSprite.menuSprite.group.visible = true;
	this.gameSprite.menuSprite.hide(3);
	
	this.updatePersonal(this.roomInfo);
	
}
CommandReceiver.prototype.enterRoom = function(msg) {

	var dir, userSprite;
	dir = this.absToRel(msg.dir);
	userSprite = this.gameSprite.userSpriteArr[dir];
	userSprite.setName(msg.name);
	userSprite.setScore(msg.score);
	userSprite.changeHeadUrl(msg.url);
	userSprite.ready(false);
	userSprite.fightInfo =  msg.fightInfo;
	userSprite.fightInfo.dir = msg.dir;
	userSprite.fightInfo.authorDir = this.author;
	userSprite.fightInfo.commandHandler = this.gameSprite.commandHandler;
	userSprite.headUs.boxSprite.inputEnabled = true;
	
	
	this.updateShareDataOfDesc();
}
CommandReceiver.prototype.leaveRoom = function(msg) {
	var dir, userSprite;
	dir = this.absToRel(msg.dir);
	userSprite = this.gameSprite.userSpriteArr[dir];
	userSprite.setName('');
	userSprite.setScore('');
	userSprite.removeHeadImage();
	userSprite.ready(false);
	userSprite.headUs.boxSprite.inputEnabled = false; 
	if(userSprite.fightInfoSprite){
		userSprite.fightInfoSprite.group.destroy();
		userSprite.fightInfoSprite = null;
	}
	
	
	
	this.updateShareDataOfDesc();
}

CommandReceiver.prototype.userReady = function(msg) {
	var dir, userSprite;
	dir = this.absToRel(msg.dir);
	userSprite = this.gameSprite.userSpriteArr[dir];
	userSprite.ready(msg.ready);
	
	if(dir == 0){
		this.gameSprite.readyBtnSprite.group.visible = false;
	}
}
CommandReceiver.prototype.gameStart = function(msg) {
//	this.gameSprite.clearCards();
	
	
//	this.done = false;
	this.gameSprite.readyBtnSprite.group.visible = false;
	this.gameSprite.inviteBtnSprite.group.visible = false;
	this.gameSprite.leaveBtnSprite.group.visible = false;
	
	this.gameSprite.leftTimeTextSprite.clear();
	
//	this.gameSprite.ipTintTextSprite.group.visible = false;
	
	
//	if(document.title == '宁波麻将' || document.title == '奉化麻将'){
//		this.gameSprite.topSprite.quanTextSprite.group.visible = true;
//		console.log('quan['+msg.quan+']');
//		this.gameSprite.topSprite.setQuan(msg.quan);
//	}else{
//		this.gameSprite.topSprite.quanTextSprite.group.visible = false;
//	}
	
	for (var i = 0; i < this.gameSprite.userSpriteArr.length; i++) {
		this.gameSprite.userSpriteArr[i].ready(false);
	}
//	for(var i = 0;i< this.gameSprite.cardsSpriteArr.length;i++){
//		this.gameSprite.cardsSpriteArr[i].showArrow(false);
//	}
	
//	this.gameSprite.menuSprite.dismissS.group.visible = false;
	this.gameSprite.menuSprite.hide(3);
	
	
	
//	var cr = this;
//	this.gameSprite.gameStartTween(function(context) {
//		cr.gameStartInner(msg);
//		cr.done = true;
//	});
	this.gameStartInner(msg);
//	this.updateCards(msg.cards);
}
CommandReceiver.prototype.gameStartInner = function(msg) {
//	game.config.baidas = msg.baidas;
	this.updateCards(msg.cards);
//	this.gameSprite.userSpriteArr[this.absToRel(msg.east)].zuan(true);
//	this.gameSprite.middleSprite.group.visible = true;
//	this.gameSprite.middleSprite.setEast(this.absToRel(msg.east));
//	this.gameSprite.middleSprite.setQuan(msg.quan);
//	this.gameSprite.middleSprite.quiet(false);
//	this.gameSprite.showWhiteCard(msg.whiteCard);
	
//	for(var i = 0;i<4;i++){
//		this.gameSprite.userSpriteArr[i].huaUs.group.visible = true;
//	}
	
//	this.roomInfo.quan = msg.quan;
//	this.roomInfo.laoShu = msg.laoShu;
	this.gameSprite.roomInfoSprite.setRoomInfo(this.roomInfo);
	
//	this.gameSprite.infoSprite.group.visible = true;
//	this.gameSprite.infoSprite.setTotalJu(this.roomInfo.totalJu);
//	this.gameSprite.infoSprite.setLeftCard(msg.leftCard);
//	this.gameSprite.infoSprite.setLeftJu(msg.leftJu);
	
	
	this.updatePersonal(this.roomInfo);
}


CommandReceiver.prototype.result = function(msg) {
	
	this.done = false;
	
	this.gameSprite.resultSprite.group.visible = true;
	for(var i = 0;i<4;i++){
		this.gameSprite.userSpriteArr[i].headUs.boxSprite.inputEnabled = false;
	}
	this.gameSprite.audioBtnSprite.group.visible = false;
	this.gameSprite.chatBtnSprite.group.visible = false;
	
	var dataArr = [];
	var finalScore ;
	
	var cards = msg.cards;
	for(var i = 0;i< cards.length;i++){
		var dir = cards[i].dir;
		var data = {};
		dataArr[dir] = data;
		
		
		var nameArr = [];
		var cns = [];
		
		var tmpCs = cards[i].cards;
		if(tmpCs[0]){
			nameArr.push(tmpCs[0].name);
			nameArr.push(' ');
			nameArr.push(' ');
			cns = tmpCs[0].cns;
		}else{
			for(var j = 1;j<tmpCs.length;j++){
				nameArr.push(tmpCs[j].name);
				cns = cns.concat(tmpCs[j].cns);
			}
		}
		data.cns = cns;
		data.nameArr = nameArr;
	}
	
	for(var i = 0;i< msg.scores.length;i++){
		var dir = msg.scores[i].dir;
		var data = dataArr[dir];
		data.score = msg.scores[i].score;
		if(dir == this.author){
			finalScore = msg.scores[i].score;
		}
	}
	for(var i=0;i< cards.length;i++){
		dataArr[i].headUrl =this.gameSprite.userSpriteArr [this.absToRel(i) ].getHeadUrl();
	}
	var resultMessage = '';
	var roomMessage = this.gameSprite.roomInfoSprite.message;
	if(roomMessage){
		for(var i = 0 ;i< roomMessage.length;i++){
			resultMessage +=roomMessage[i]+' ';
		}
	}
	var cr = this;
	this.gameSprite.resultSprite.show(dataArr, function() {
		cr.gameSprite.resultSprite.clear();
		if(cr.gameSprite.commandHandler){
			cr.gameSprite.commandHandler.listen('ready', null);
		}
		cr.done = true;
	},resultMessage,finalScore);
}
CommandReceiver.prototype.offline = function(msg){
	var dir = this.absToRel(msg.dir);
	this.gameSprite.userSpriteArr[dir].offline();
}
CommandReceiver.prototype.online = function(msg){
	var dir = this.absToRel(msg.dir);
	this.gameSprite.userSpriteArr[dir].online();
}

CommandReceiver.prototype.chatVoice = function(msg){
//	alert(msg.serverId + ' '+msg.dir+" "+msg.localId);
	if(!game.audioManager){
		return;
	}
	if(msg.dir == this.author){
		game.audioManager.playLocalId(msg.localId);
	}else{
		game.audioManager.playServerId(msg.serverId);
	}
	var dir =  this.absToRel(msg.dir);
	this.gameSprite.userSpriteArr[dir].showVoice(3);
};

CommandReceiver.prototype.chatText = function(msg){
	var dir = this.absToRel(msg.dir);
	var text = msg.text;
	var content;
	if(text.startsWith('#face:')){
		content = text.substring(6,text.length);
		this.gameSprite.userSpriteArr[dir].showFace(content,3);
		game.world.bringToTop(this.gameSprite.userSpriteArr[dir].group);
	}else if(text.startsWith('#text:')){
		content = text.substring(6,text.length);
		content = parseInt(content);
		var index = content;
		content = this.gameSprite.chatBoxSprite.getTextByIndex(content);
		if(dir == 1){
			this.gameSprite.userSpriteArr[dir].showText(content,false);
		}else{
			this.gameSprite.userSpriteArr[dir].showText(content,true);
		}
		game.world.bringToTop(this.gameSprite.userSpriteArr[dir].group);
		
		if(game.audioManager){
			game.audioManager.playByKey('text_'+index);
		}
	}
};
CommandReceiver.prototype.bullet = function(msg){
	var posArr;
	if(this.gameSprite.userRoomPosition){
		posArr = this.gameSprite.calRoomUserPosition();
	}else{
		posArr = this.gameSprite.calGameUserPosition();
	}
	var authorPos =  posArr[this.absToRel(msg.authorDir)];
	var destPos =posArr[this.absToRel(msg.dir)];
	var sprite = this.gameSprite.userSpriteArr[this.absToRel(msg.dir)].fightInfoSprite;
	if(sprite){
		sprite.group.visible = false;
	}
	this.gameSprite.bulletSprite.bullet(authorPos,destPos,msg.index);
};

CommandReceiver.prototype.destroy = function(msg){
	this.done = false;
	var gameSprite = this.gameSprite;
	this.gameSprite.showMessageBox(msg.reason.split(","),function(btn){
		btn.context.group.destroy();
		var url ='';
		url += ('home.html?recommend='+util.getQueryString('recommend'));
		if(util.env == 'dev'){
			url+= ('&code='+util.getQueryString('code'));
		}
		url+=('&roomId='+util.getQueryString('roomId'));
		window.location.href = url;
	});
}
CommandReceiver.prototype.netError = function(msg){
	var gameSprite = this.gameSprite;
	this.gameSprite.showMessageBox(['网络异常','请刷新页面重新连接'],function(btn){
		btn.context.group.destroy();
	});
}
