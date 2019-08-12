function CommandReceiver() {
	this.gameSprite;
	
	this.author; //玩家的绝对坐标
	this.roomInfo; //roomInfo信息,包含了各种麻将信息
	
	this.done = true;
}
CommandReceiver.prototype.updateCards = function(cards) {
	var len = cards.length, i, card, dir, j;
	for (i = 0; i < len; i++) {
		card = cards[i];
		dir = this.absToRel(card.dir);
		this.gameSprite.cardsSpriteArr[dir].group.visible = true;
		this.gameSprite.cardsSpriteArr[dir].showArrow(false);
		this.gameSprite.cardsSpriteArr[dir].addInnerCard(card.i);
		this.gameSprite.cardsSpriteArr[dir].sortInnerCard();
		if(card.i.length %3 ==2){
			this.gameSprite.cardsSpriteArr[dir].seperateInnerCard();
			if(dir == 0){
				this.gameSprite.cardsSpriteArr[0].clickEnabled(true);
			}
		}
		if (card.m && card.m.length > 0) {
			for (j = 0; j < card.m.length; j++) {
				var mcs = (card.m)[j];
				var who =  this.absToRel(mcs.who);
				if (mcs.a == 'hua') {
					this.gameSprite.userSpriteArr[dir].addHuaArr(mcs.cns);
				} else if (mcs.a == 'angang') {
					this.gameSprite.cardsSpriteArr[dir]
							.addMiddleAnGangCard(mcs.cns[0]);
				} else if (mcs.a == 'peng') {
					this.gameSprite.cardsSpriteArr[dir].addMiddlePengCard(
							mcs.cns[0], who);
				} else if (mcs.a == 'chi') {
					this.gameSprite.cardsSpriteArr[dir].addMiddleChiCard(
							mcs.cns, who, mcs.cn);
				} else if (mcs.a == 'xiangang' || mcs.a == 'minggang') {
					this.gameSprite.cardsSpriteArr[dir].addMiddleMingGangCard(
							mcs.cns[0], who);
				}
			}
		}
		if (card.o && card.o.length > 0) {
			for (j = 0; j < card.o.length; j++) {
				this.gameSprite.cardsSpriteArr[dir].addOutterCard(card.o[j]);
			}
		}
		this.gameSprite.cardsSpriteArr[dir].showArrow(false);
	}
};

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
		this.destroy(msg);
	} else if (code == 'no') {
		this.no(msg);
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
	}else if(code == 'maima'){
		this.maima(msg);
	}else if(code == 'messagebox'){
		this.gameSprite.showMessageBox(msg.message,function(btn){
			btn.context.group.destroy();
		});
	}else if(code == 'managed'){
		this.codeManaged(msg);
	}else if(code == 'roomStart'){
		this.roomStart(msg);
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
	this.gameSprite.ajustUserToRoomPosition();

	
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
		userSprite.huaUs.group.visible = false;
		userSprite.huaUs.clearHua();
	}
	this.gameSprite.ajustUserToRoomPosition();

	
	this.gameSprite.resultSprite.clear();
	
	this.gameSprite.grayBackGround.visible = false;
	
	this.gameSprite.clearCards();
	this.gameSprite.infoSprite.group.visible = false;
	this.gameSprite.whiteCardSprite.group.visible = false;
	this.gameSprite.middleSprite.group.visible = false;
	
	
	this.gameSprite.chatBtnSprite.group.visible = true;
	this.gameSprite.audioBtnSprite.group.visible = true;
	
	this.gameSprite.maimaSprite.clear();
	
	for(i = 0;i< this.gameSprite.tuoguanSpriteArr.length;i++){
		this.gameSprite.tuoguanSpriteArr[i].visible = false;
	}
	this.gameSprite.goonBtnSprite.group.visible = false;
	
//	this.gameSprite.menuSprite.dismissS.group.visible = true;
	this.gameSprite.menuSprite.showAll();
}




CommandReceiver.prototype.updatePersonal = function(roomInfo){
	if(roomInfo.name == 'hangzhouMajiang' || roomInfo.name == 'hongzhongMajiang' || roomInfo.name == 'guangdongMajiang'){
		this.gameSprite.middleSprite.dirSprite.group.visible = false;
		for(var i = 0;i< 4;i++){
			this.gameSprite.userSpriteArr[i].huaUs.group.visible = false;
		}
	}
	
	if(roomInfo.name == 'guangdongMajiang' || roomInfo.name == 'xiangshanMajiang'){
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


CommandReceiver.prototype.gameNotice = function(msg) {
	this.gameSprite.clear();

	this.author = msg.author;
	
	

	this.gameSprite.topSprite.group.visible = true;
	this.gameSprite.topSprite.setRoomId(msg.roomId);

	
//	this.gameSprite.ipTintTextSprite.group.visible = false;

	var len = msg.users.length, i, user, dir, userSprite;
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
		userSprite.huaUs.group.visible = true;
		userSprite.ready(false);
		userSprite.headUs.boxSprite.inputEnabled = true;
	}
	this.gameSprite.ajustUserToGamePosition();

	game.config.baidas = msg.baidas;
	this.updateCards(msg.cards);

	this.gameSprite.userSpriteArr[this.absToRel(msg.east)].zuan(true);
	this.gameSprite.middleSprite.group.visible = true;
	this.gameSprite.middleSprite.setEast(this.absToRel(msg.east));
	this.gameSprite.middleSprite.setQuan(msg.quan);
	if(msg.middleArrow != -1){
		this.gameSprite.middleSprite.setArrow(this.absToRel(msg.middleArrow));
	}
	this.gameSprite.middleSprite.quiet(false);
	this.gameSprite.infoSprite.group.visible = true;
	this.gameSprite.infoSprite.setTotalJu(msg.roomInfo.totalJu);
	this.gameSprite.infoSprite.setLeftCard(msg.leftCard);
	this.gameSprite.infoSprite.setLeftJu(msg.leftJu);
	this.gameSprite.showWhiteCard(msg.whiteCard);


	if (msg.arrow >= 0) {
		this.gameSprite.cardsSpriteArr[this.absToRel(msg.arrow)]
				.showArrow(true);
	}
	if (msg.isBao) {
		var genlist = msg.genlist;
		for (i = 0; i < genlist.length; i++) {
			if (genlist[i].gen === false) {
				this.gameSprite.cardsSpriteArr[this.absToRel(genlist[i].dir)]
						.downInner();
			}
		}
	}
	
	this.gameSprite.chatBtnSprite.group.visible = true;
	this.gameSprite.audioBtnSprite.group.visible = true;
	
	this.roomInfo =  msg.roomInfo;
	this.roomInfo.quan = msg.quan;
	this.roomInfo.laoShu = msg.laoShu;
	this.gameSprite.roomInfoSprite.group.visible = true;
	this.gameSprite.roomInfoSprite.setRoomInfo(this.roomInfo);
	
	
	this.updateShareData(msg);
	if(document.title == '宁波麻将' || document.title == '奉化麻将'){
		this.gameSprite.topSprite.quanTextSprite.group.visible = true;
		this.gameSprite.topSprite.setQuan(msg.quan);
	}else{
		this.gameSprite.topSprite.quanTextSprite.group.visible = false;
	}
	
	this.gameSprite.menuSprite.group.visible = true;
	this.gameSprite.menuSprite.hide(3);
//	this.gameSprite.menuSprite.dismissS.group.visible = false;
	
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
	this.gameSprite.clearCards();
	
	
	this.done = false;
	this.gameSprite.readyBtnSprite.group.visible = false;
	this.gameSprite.inviteBtnSprite.group.visible = false;
	this.gameSprite.leaveBtnSprite.group.visible = false;
	
	this.gameSprite.leftTimeTextSprite.clear();
	
//	this.gameSprite.ipTintTextSprite.group.visible = false;
	
	
	if(document.title == '宁波麻将' || document.title == '奉化麻将'){
		this.gameSprite.topSprite.quanTextSprite.group.visible = true;
		console.log('quan['+msg.quan+']');
		this.gameSprite.topSprite.setQuan(msg.quan);
	}else{
		this.gameSprite.topSprite.quanTextSprite.group.visible = false;
	}
	
	for (var i = 0; i < this.gameSprite.userSpriteArr.length; i++) {
		this.gameSprite.userSpriteArr[i].ready(false);
	}
	for(var i = 0;i< this.gameSprite.cardsSpriteArr.length;i++){
		this.gameSprite.cardsSpriteArr[i].showArrow(false);
	}
	
//	this.gameSprite.menuSprite.dismissS.group.visible = false;
	this.gameSprite.menuSprite.hide(3);
	
	var cr = this;
	this.gameSprite.gameStartTween(function(context) {
		cr.gameStartInner(msg);
		cr.done = true;
	});
}
CommandReceiver.prototype.gameStartInner = function(msg) {
	game.config.baidas = msg.baidas;
	this.updateCards(msg.cards);
	this.gameSprite.userSpriteArr[this.absToRel(msg.east)].zuan(true);
	this.gameSprite.middleSprite.group.visible = true;
	this.gameSprite.middleSprite.setEast(this.absToRel(msg.east));
	this.gameSprite.middleSprite.setQuan(msg.quan);
	this.gameSprite.middleSprite.quiet(false);
	this.gameSprite.showWhiteCard(msg.whiteCard);
	
	for(var i = 0;i<4;i++){
		this.gameSprite.userSpriteArr[i].huaUs.group.visible = true;
	}
	
	this.roomInfo.quan = msg.quan;
	this.roomInfo.laoShu = msg.laoShu;
	this.gameSprite.roomInfoSprite.setRoomInfo(this.roomInfo);
	
	this.gameSprite.infoSprite.group.visible = true;
	this.gameSprite.infoSprite.setTotalJu(this.roomInfo.totalJu);
	this.gameSprite.infoSprite.setLeftCard(msg.leftCard);
	this.gameSprite.infoSprite.setLeftJu(msg.leftJu);
	
	
	this.updatePersonal(this.roomInfo);
}

CommandReceiver.prototype.mo = function(msg) {
	var dir = this.absToRel(msg.dir);
	this.gameSprite.cardsSpriteArr[dir].addInnerCard(msg.card);
	this.gameSprite.cardsSpriteArr[dir].seperateInnerCard();
	this.gameSprite.middleSprite.resetTime();
	this.gameSprite.middleSprite.setArrow(dir);
	
	this.gameSprite.infoSprite.setLeftCard(this.gameSprite.infoSprite.leftCard - 1);
	
	if(dir == 0){
		this.gameSprite.cardsSpriteArr[0].clickEnabled(true);
	}
	
	
}
CommandReceiver.prototype.da = function(msg) {
	this.gameSprite.actionBtnsSprite.clear();

	
	var dir = this.absToRel(msg.dir);
	this.gameSprite.cardsSpriteArr[dir].addOutterCard(msg.card);
	if(game.audioManager){
		game.audioManager.playByKey('discard');
		game.audioManager.playByCard(msg.card);
	};
	this.gameSprite.cardsSpriteArr[dir].removeInnerCard(msg.card);
	this.gameSprite.cardsSpriteArr[dir].sortInnerCard();
	
	for (var i = 0; i < 4; i++) {
		this.gameSprite.cardsSpriteArr[i].showArrow(false);
	}
	this.gameSprite.cardsSpriteArr[dir].showArrow(true);
	this.gameSprite.cardsSpriteArr[0].clickEnabled(false);
	if(dir == 0){
		this.gameSprite.cardsSpriteArr[0].recoverColor();
	}
}
CommandReceiver.prototype.next = function(msg) {
	var i, a, cns, len = msg.as.length,hasHu = false;
	for (i = 0; i < len; i++) {
		a = msg.as[i].a;
		if(a == 'hu'){
			hasHu = true;
		}
		if (a == 'da') {
			cns = msg.as[i].cns;
			this.gameSprite.cardsSpriteArr[0].clickEnabled(true);
			if(cns != null && cns.length != 0 && cns[0] != null && cns[0].length != 0){
				cns = cns[0];
				this.gameSprite.cardsSpriteArr[0].clickDisable(cns);
			}
		} else {
			cns = msg.as[i].cns;
			this.gameSprite.actionBtnsSprite.show(a, cns);
			game.world.bringToTop(this.gameSprite.actionBtnsSprite.group);
		}
	}
	if(hasHu && this.roomInfo.name == 'hongzhongMajiang'){
		this.gameSprite.actionBtnsSprite.hideBtn('guo');
	}
}

CommandReceiver.prototype.dahua = function(msg) {
	this.done = false;
	
	if(game.audioManager){
		game.audioManager.playByKey('mj.hua');
	}
	
	var dir = this.absToRel(msg.dir);
	var card = msg.card;

	this.gameSprite.userSpriteArr[dir].addHuaArr([ card ]);
	this.gameSprite.cardsSpriteArr[dir].removeInnerCard(card);
	var cr = this;
	
	game.world.bringToTop(this.gameSprite.actionShowsSprite.group);
	
	this.gameSprite.actionShowsSprite.show('hua', dir, function() {
		cr.done = true;
	});
}

CommandReceiver.prototype.chiPengGang = function(msg) {
	this.done = false;
	this.gameSprite.actionBtnsSprite.clear();

	var dir = this.absToRel(msg.dir);
	var a = msg.a;
	var cns = msg.cns;
	var who = this.absToRel(msg.who);
	var cn = msg.cn;
	
	if( a == 'chi' || a == 'peng'){
		if(dir == 0){
			this.gameSprite.cardsSpriteArr[0].clickEnabled(true);
		}
	}
	
	
	
	this.gameSprite.cardsSpriteArr[dir].sortInnerCard();
	for (i = 0; i < msg.i.length; i++) {
		this.gameSprite.cardsSpriteArr[dir].removeInnerCard((msg.i)[i]);
	}
	this.gameSprite.cardsSpriteArr[dir].addMiddleCard(a, cns, who, cn);
	
	if(a == 'minggang' || a == 'chi' || a == 'peng'){
		this.gameSprite.cardsSpriteArr[who].removeOutterCard(cn);
	}
	for(i =0;i<this.gameSprite.cardsSpriteArr.length;i++){
		this.gameSprite.cardsSpriteArr[i].showArrow(false);
	}

	var action;
	if (a == 'minggang' || a == 'angang' || a == 'xiangang') {
		action = 'gang';
	} else {
		action = a;
	}
	

	
	
	
	var cr = this;
	game.world.bringToTop(this.gameSprite.actionShowsSprite.group);
	this.gameSprite.actionShowsSprite.show(action, dir, function() {

		if (action == 'peng' || action == 'chi') {
			cr.gameSprite.middleSprite.resetTime();
			cr.gameSprite.middleSprite.setArrow(dir);
		}
		cr.done = true;
	});

}

CommandReceiver.prototype.no = function(msg) {
	this.gameSprite.actionBtnsSprite.clear();
}
CommandReceiver.prototype.gen = function(msg) {

	if (msg.dir == this.author) {
		this.gameSprite.actionBtnsSprite.clear();
	}

	var dir = this.absToRel(msg.dir);
	var gen = msg.gen;
	if (gen) {

	} else {
		this.gameSprite.cardsSpriteArr[dir].downInner();
	}

}
CommandReceiver.prototype.hu = function(msg) {
	this.done = false;
	
	if(msg.dir == this.author){
		this.gameSprite.actionBtnsSprite.clear();
	}
	if(!this.gameSprite.actionBtnsSprite.btns['hu'].group.visible){
		this.gameSprite.actionBtnsSprite.clear();
	}	
	var dir = this.absToRel(msg.dir);
	var cr = this;
	game.world.bringToTop(this.gameSprite.actionShowsSprite.group);
	this.gameSprite.actionShowsSprite.show('hu', dir, function() {
		cr.done = true;
	});
}

CommandReceiver.prototype.result = function(msg) {
	this.done = false;
	var cr = this;
	var i, rs = msg.rs, len = rs.length;
	for (i = 0; i < len; i++) {
		rs[i].dir = {
			abs : rs[i].dir,
			rel : this.absToRel(rs[i].dir),
			author : this.author
		};
	}
	this.gameSprite.middleSprite.quiet(true);
	this.gameSprite.resultSprite.group.visible = true;
	
	this.gameSprite.cardsSpriteArr[0].clickEnabled(false);
	for(var i = 0;i<4;i++){
		this.gameSprite.userSpriteArr[i].headUs.boxSprite.inputEnabled = false;
	}
	this.gameSprite.audioBtnSprite.group.visible = false;
	this.gameSprite.chatBtnSprite.group.visible = false;
	
	var resultMessage = '';
	var roomMessage = this.gameSprite.roomInfoSprite.message;
	if(roomMessage){
		for(var i = 0 ;i< roomMessage.length;i++){
			resultMessage +=roomMessage[i]+' ';
		}
	}
	
	this.gameSprite.resultSprite.show(rs, function() {
		cr.gameSprite.resultSprite.clear();
		if(cr.gameSprite.commandHandler){
			cr.gameSprite.commandHandler.listen('ready', null);
		}
		cr.done = true;
	},resultMessage);
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
CommandReceiver.prototype.maima = function(msg){
	this.done = false;
	
	
	
	this.gameSprite.grayBackGround.visible = true;
	var cr = this;
	this.gameSprite.maimaSprite.showMaima(msg.cards,function(){
		cr.done = true;
	});
	game.world.bringToTop(this.gameSprite.grayBackGround);
	game.world.bringToTop(this.gameSprite.maimaSprite.group);
	
	var hucards = msg.hucards;
	if(hucards !=null  && hucards.length!=0){
		for(var i = 0;i<hucards.length;i++){
			var huc = hucards[i];
			var dir = this.absToRel(huc.dir);
			this.gameSprite.cardsSpriteArr[dir].upInner(huc.ics);
		}
	}
}
CommandReceiver.prototype.codeManaged = function(msg){
	var dir  = this.absToRel(msg.dir);

	if(msg.managed === true){
		console.log('codemanaged true ');
		this.gameSprite.tuoguanSpriteArr[dir].visible = true;
		if(dir == 0){
			this.gameSprite.grayBackGround.visible = true;
			this.gameSprite.goonBtnSprite.group.visible = true;
		}
	}else{
		console.log('codemanaged false ');
		this.gameSprite.tuoguanSpriteArr[dir].visible = false;
		if(dir == 0){
			this.gameSprite.grayBackGround.visible = false;
			this.gameSprite.goonBtnSprite.group.visible = false;
		}
	}
}
