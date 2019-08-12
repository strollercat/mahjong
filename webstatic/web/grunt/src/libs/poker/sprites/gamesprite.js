function GameSprite() {
	this.bgSprite;
	this.topSprite;

	this.userSpriteArr;

	this.chatBoxSprite;
	this.roomInfoSprite;

	this.readyBtnSprite;
	this.inviteBtnSprite;
	this.leaveBtnSprite;
	this.chatBtnSprite;
	this.audioBtnSprite;

	this.leftTimeTextSprite;

	this.resultSprite;
	this.bulletSprite;
	this.tintSprite;

	this.menuSprite;
	this.requestDismissSprite;
	this.inviteHintSprite;
	this.grayBackGround;


	this.commandHandler = new CommandHandler();


	this.cardsShootSprite ;
	this.threeCardsArr;
	this.resultSprite;

};
GameSprite.prototype = util.inherit(GroupSprite.prototype);
GameSprite.prototype.constructor = GameSprite;



GameSprite.prototype.layoutUser = function() {
	var i, sprite;
	for (i = 0; i < 4; i++) {
		sprite = this.userSpriteArr[i];
		sprite.layout({
			w : game.world.width / 5
		});
	}

	this.ajustUserToRoomPosition();



};
GameSprite.prototype.ajustUserToRoomPosition = function() {
	var sprite;
	var p = this.calRoomUserPosition();
	for(var i = 0;i<4 ;i++){
		this.userSpriteArr[i].group.x = p[i].x;
		this.userSpriteArr[i].group.y = p[i].y;
	}
	this.userRoomPosition = true;
}



GameSprite.prototype.calRoomUserPosition = function() {
	var sprite = this.userSpriteArr[0];
	var retP = [], p;

	p = {
		x : game.world.width / 2 - sprite.width / 2 ,
		y : game.world.height - 1.5 * sprite.width
	};
	retP.push(p);

	p = {
		x : game.world.width - 1 * sprite.width,
		y : game.world.height / 2 - sprite.width / 2 +1.1 * sprite.width
	};
	retP.push(p);

	p = {
		x : game.world.width / 2 - sprite.width / 2 ,
		y : 1.6 *this.topSprite.height
	};
	retP.push(p);

	p = {
		x : 0,
		y : game.world.height / 2 - sprite.width / 2 + 1.1 *sprite.width
	};
	retP.push(p);
	return retP;
};

GameSprite.prototype.layout = function() {
	var width, height;

	this.bgSprite.layout();

	width = game.world.width;
	this.topSprite.layout({
		w : width
	});
	this.topSprite.group.x = game.world.width / 2 - width / 2;

	height = game.world.height / 4;
	width = height / 76 * 33;
	this.inviteHintSprite.layout({
		w : width,
		h : height
	});
	this.inviteHintSprite.group.y = game.world.height / 4 * 3;
	this.inviteHintSprite.group.x = game.world.width - width * 1.2;

	// width = game.world.height/5;
	// this.middleSprite.layout({w:width});
	// this.middleSprite.group.x = game.world.width/2 - width/2;
	// this.middleSprite.group.y = game.world.height/2 - width/2;

	// width = width/4 * 4;
	// this.infoSprite.layout({w:width});
	// this.infoSprite.group.x = game.world.width / 2 - this.middleSprite.width
	// / 2
	// - width;
	// this.infoSprite.group.y = game.world.height / 2 - width / 4;

	this.layoutUser();
	// this.layoutCards();

	width = game.world.width / 4 * 3;
	this.chatBoxSprite.layout({
		w : width
	});
	this.chatBoxSprite.group.x = game.world.width / 2 - width / 2
	this.chatBoxSprite.group.y = game.world.height / 2
			- this.chatBoxSprite.height / 2;

	// height = game.world.height/25;
	// this.roomInfoSprite.layout({h:height});
	// this.roomInfoSprite.group.y = 0.5 *height;
	// this.roomInfoSprite.group.x = game.world.width/2 + this.topSprite.width/2
	// + height;

	width = game.world.width / 3.5;
	height = width / 188 * 80;
	this.readyBtnSprite.layout({
		w : width,
		h : height
	});
	this.readyBtnSprite.group.x = game.world.width / 2 - width / 2;
	this.readyBtnSprite.group.y = game.world.height / 2 - height;
	this.inviteBtnSprite.layout({
		w : width,
		h : height
	});
	this.inviteBtnSprite.group.x = game.world.width / 2 - width / 2;
	this.inviteBtnSprite.group.y = game.world.height / 2 + height / 4;
	this.leaveBtnSprite.layout({
		w : width,
		h : height
	});
	this.leaveBtnSprite.group.x = game.world.width / 2 - width / 2;
	this.leaveBtnSprite.group.y = game.world.height / 2 + height / 2 * 3;
	// this.goonBtnSprite.layout({w:width,h:height});
	// this.goonBtnSprite.group.x = game.world.width/2 - width/2;
	// this.goonBtnSprite.group.y = game.world.height -
	// (this.cardsSpriteArr[0].innerWidth *2 + height);

	width = game.world.height / 10;
	var p = this.calRoomUserPosition();
	this.chatBtnSprite.layout({
		w : width,
		h : width
	});
	this.chatBtnSprite.group.x = p[1].x + 0 * this.userSpriteArr[0].width;
	this.chatBtnSprite.group.y = p[1].y + 2 * width;
	this.audioBtnSprite.layout({
		w : width
	});
	this.audioBtnSprite.group.x = p[1].x + 0 * this.userSpriteArr[0].width;
	this.audioBtnSprite.group.y = this.chatBtnSprite.group.y;

	

	p = this.calRoomUserPosition();
	var userSprite = this.userSpriteArr[0];
	height = userSprite.width / 3;
	this.leftTimeTextSprite.layout({
		w : p[1].x - p[3].x,
		h : height,
		fh : height
	});
	this.leftTimeTextSprite.group.x = game.world.width / 2
			- this.leftTimeTextSprite.width / 2;
	this.leftTimeTextSprite.group.y = game.world.height / 2 - 4 * height;

	// this.resultSprite.layout();

	this.bulletSprite.layout({
		w : game.world.height / 10
	});

	height = game.world.height / 10;
	this.tintSprite.layout({
		w : game.world.width,
		h : height
	});
	// this.tintSprite.group.y = (this.calRoomUserPosition()[2].y -
	// this.topSprite.height - height)/2 + this.topSprite.height;
	this.tintSprite.group.y = game.world.height / 3 * 2;

	this.menuSprite.layout({
		w : this.topSprite.height / 10 * 8,
		h : this.topSprite.height / 10 * 8
	});
	this.menuSprite.group.x = game.world.width / 2 - this.topSprite.width / 2
			+ this.topSprite.width / 10 * 1.6;

	this.requestDismissSprite.layout({
		w : game.world.width,
		h : 0
	});
	this.requestDismissSprite.group.x = 0;
	this.requestDismissSprite.group.y = game.world.height / 4;

	this.grayBackGround.width = game.world.width;
	this.grayBackGround.height = game.world.height;

	// this.maimaSprite.layout({w:game.world.width/2});
	// this.maimaSprite.group.x = (game.world.width - this.maimaSprite.width)/2;
	// this.maimaSprite.group.y = (game.world.height -
	// this.maimaSprite.height)/2;

	// for(var i = 0;i< this.tuoguanSpriteArr.length;i++){
	// this.tuoguanSpriteArr[i].width = game.world.width/6;
	// this.tuoguanSpriteArr[i].height = this.tuoguanSpriteArr[i].width /307 *
	// 258;
	// this.ajustTuoguanSpritePosition(i);
	// }
	this.cardsShootSprite.layout({
		w : game.world.width,
		h : game.world.height
	});
	
	width = this.userSpriteArr[0].width/2;
	height = width/80*112;
	p = this.calRoomUserPosition();
	for(var i = 0;i< 4;i++){
		this.threeCardsArr[i].layout({w:width,h:height});
	}
	this.threeCardsArr[0].group.x = game.world.width/2 - 1.3*width;
	this.threeCardsArr[0].group.y = this.userSpriteArr[0].group.y - this.threeCardsArr[0].getTotalHeight();
	this.threeCardsArr[2].group.x = game.world.width/2 - 1.3*width;
	this.threeCardsArr[2].group.y = this.userSpriteArr[2].group.y + height;
	this.threeCardsArr[1].group.x = game.world.width - this.threeCardsArr[1].getTotalWidth();
	this.threeCardsArr[1].group.y = game.world.height/2 -height;
	this.threeCardsArr[3].group.x = 0;
	this.threeCardsArr[3].group.y = game.world.height/2 -height;
	
	
	this.resultSprite.layout();

};

GameSprite.prototype.createCards = function() {
	this.cardsSpriteArr = [];
	var cs;
	cs = new Cards0Sprite(0, 0, 0, this.commandHandler);
	cs.create();
	this.cardsSpriteArr.push(cs);
	cs = new Cards1Sprite(0, 0, 0);
	cs.create();
	this.cardsSpriteArr.push(cs);
	cs = new Cards2Sprite(0, 0, 0);
	cs.create();
	this.cardsSpriteArr.push(cs);
	cs = new Cards3Sprite(0, 0, 0);
	cs.create();
	this.cardsSpriteArr.push(cs);
}
GameSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	game.audioManager = new AudioManager();
	game.audioManager.create();

	var gameSprite = this;

	this.inviteHintSprite = new InviteHintSprite();
	this.inviteHintSprite.create();

	this.bgSprite = new BackgroundSprite();
	this.bgSprite.create();
	this.topSprite = new TopSprite(0);
	this.topSprite.create();
	// this.middleSprite = new MiddleSprite();
	// this.middleSprite.create();
	// this.infoSprite = new InfoSprite(0);
	// this.infoSprite.create();

	this.userSpriteArr = [];
	for (var i = 0; i < 4; i++) {
		var userSprite = new UserSprite(0);
		userSprite.create();
		this.userSpriteArr.push(userSprite);
	}

	// this.createCards();


	this.chatBoxSprite = new ChatTextInput(0, this.commandHandler);
	this.chatBoxSprite.create();
	this.roomInfoSprite = new RoomInfoSprite(0);
	this.roomInfoSprite.create();

	this.readyBtnSprite = new ButtonSprite(0, 0, 'ready_normal', 'ready_press',
			function(btn) {
				btn.context.commandHandler.listen('ready', null);
			}, this);
	this.readyBtnSprite.create();
	this.inviteBtnSprite = new ButtonSprite(0, 0, 'invite_normal',
			'invite_press', function(btn) {
				game.world.bringToTop(gameSprite.grayBackGround);
				gameSprite.grayBackGround.visible = true;
				gameSprite.showMessageBox([ '把此页面分享给好友', '赶紧邀请朋友加入吧' ],
						function(btn) {
							btn.context.group.destroy();
							gameSprite.inviteHintSprite.group.visible = false;
							gameSprite.grayBackGround.visible = false;
						});
				game.world.bringToTop(gameSprite.inviteHintSprite.group);
				gameSprite.inviteHintSprite.group.visible = true;
			}, this);
	this.inviteBtnSprite.create();
	this.leaveBtnSprite = new ButtonSprite(0, 0, 'leave_normal', 'leave_press',
			function(btn) {
				var url = 'home.html';
				url += '?recommend=' + util.getQueryString('recommend');
				if (util.env == 'dev') {
					url += '&code=' + util.getQueryString('code');
				}
				window.location.href = url;
				// wx.closeWindow();
			}, this);
	this.leaveBtnSprite.create();
	this.chatBtnSprite = new ButtonSprite(
			0,
			0,
			'chat_normal',
			'chat_press',
			function(btn) {
				btn.context.chatBoxSprite.group.visible = !btn.context.chatBoxSprite.group.visible;
				game.world.bringToTop(btn.context.chatBoxSprite.group);
			}, this);
	this.chatBtnSprite.create();
	this.audioBtnSprite = new AudioSprite(0, this.commandHandler);
	this.audioBtnSprite.create();


	this.leftTimeTextSprite = new TimerTextSprite();
	this.leftTimeTextSprite.create();


	// this.resultSprite = new ResultSprite(this);
	// this.resultSprite.create();

	this.bulletSprite = new BulletAnimateSprite();
	this.bulletSprite.create();

	this.tintSprite = new TintSprite();
	this.tintSprite.create();

	this.menuSprite = new MenuSprite(function(textSprite) {
		var text = this.textS.getText();
		if (text == '邀请好友') {
			game.world.bringToTop(gameSprite.grayBackGround);
			gameSprite.grayBackGround.visible = true;
			gameSprite.showMessageBox([ '把此页面分享给好友', '赶紧邀请朋友加入吧' ], function(
					btn) {
				btn.context.group.destroy();
				gameSprite.inviteHintSprite.group.visible = false;
				gameSprite.grayBackGround.visible = false;
			});
			game.world.bringToTop(gameSprite.inviteHintSprite.group);
			gameSprite.inviteHintSprite.group.visible = true;
		} else if (text == '返回大厅') {
			var url = 'home.html';
			url += '?recommend=' + util.getQueryString('recommend');
			if (util.env == 'dev') {
				url += '&code=' + util.getQueryString('code');
			}
			window.location.href = url;
		} else if (text == '解散房间') {
			gameSprite.commandHandler.listen('dismiss', true);
			gameSprite.showMessageBox([ '申请解散房间成功', '等待其他玩家同意' ],
					function(btn) {
						btn.context.group.destroy();
					});
		} else if (text == '游戏规则') {
			var message = gameSprite.roomInfoSprite.message;
			if (message) {
				gameSprite.showMessageBox(message, function(btn) {
					btn.context.group.destroy();
				});
			}
			;
		}
		gameSprite.menuSprite.subMenuGroup.visible = false;
	});
	this.menuSprite.create();

	var btn1Info = {
		image : 'buttons',
		image1 : 'agree_normal',
		image2 : 'agree_press',
		callBack : function(btn) {
			btn.context.group.visible = false;
			gameSprite.commandHandler.listen('dismiss', true);
		}
	};
	var btn2Info = {
		image : 'buttons',
		image1 : 'refuse_normal',
		image2 : 'refuse_press',
		callBack : function(btn) {
			btn.context.group.visible = false;
			gameSprite.commandHandler.listen('dismiss', false);
		}
	};
	this.requestDismissSprite = new MessageBox1Sprite(btn1Info, btn2Info, [ '',
			'' ], function(btn) {
		btn.context.group.visible = false;
	});
	this.requestDismissSprite.create();
	// this.requestDismissSprite.layout({w:game.world.width/2,h:0});

	this.grayBackGround = game.add.graphics(0, 0);
	this.grayBackGround.beginFill(0x00000);
	this.grayBackGround.drawRect(0, 0, game.world.width, game.world.height);
	this.grayBackGround.alpha = 0.7;
	this.grayBackGround.endFill();




	
	
	this.threeCardsArr = [];
	for(var i = 0;i< 4;i++){
		var s = new ThreeCardsSprite();
		s.create();
		this.threeCardsArr.push(s);
	}
	
	this.cardsShootSprite= new CardsShootSprite(this.commandHandler);
	this.cardsShootSprite.create();
	
	
	this.resultSprite = new ResultSprite();
	this.resultSprite.create();

};

GameSprite.prototype.showMessageBox = function(msg, callBack) {

	var btn1Info = {
		image : 'buttons',
		image1 : 'confirm_normal',
		image2 : 'confirm_press',
		callBack : callBack
	};
	var mb = new MessageBox1Sprite(btn1Info, null, msg, callBack);
	mb.create();
	mb.layout({
		w : game.world.width,
		h : 0
	});

	// mb.group.x = game.world.width/4*3;
	mb.group.y = game.world.height / 4;
}

GameSprite.prototype.gameStartTween = function(complete) {
//	var i, sprite, tween;
//	var ps = this.calGameUserPosition();
//	this.userRoomPosition = false;
//	for (i = 0; i < this.userSpriteArr.length; i++) {
//		sprite = this.userSpriteArr[i];
//		tween = game.add.tween(sprite.group);
//		tween.to(ps[i], 800, Phaser.Easing.Linear.None, false, 0, 0, false);
//		if (i == 0) {
//			tween.onComplete.add(complete, this);
//		}
//		tween.start();
//	}
};

GameSprite.prototype.userSpriteSize = function() {
	var total = 0;
	for (var i = 0; i < this.userSpriteArr.length; i++) {
		var userSprite = this.userSpriteArr[i];
		if (this.userSpriteArr[i].headUs.imgUrl) {
			total += 1;
		}
	}
	return total;
};


GameSprite.prototype.clearUsers = function() {
	for (var i = 0; i < 4; i++) {
		this.userSpriteArr[i].clear();
		this.userSpriteArr[i].headUs.boxSprite.inputEnabled = true;
	}
}

GameSprite.prototype.clear = function() {
	this.bgSprite;

	this.topSprite.setRoomId('');
	this.topSprite.setQuan('');
	this.topSprite.group.visible = false;

	// this.middleSprite.setArrow(0);
	// this.middleSprite.setEast(0);
	// this.middleSprite.setQuan(0);
	// this.middleSprite.dirSprite.group.visible = true;
	// this.middleSprite.group.visible = false;

	// this.infoSprite.setLeftCard('');
	// this.infoSprite.setLeftJu('');
	// this.infoSprite.group.visible = false;

	this.clearUsers();
	// this.clearCards();

	this.chatBoxSprite.group.visible = false;
	this.roomInfoSprite.group.visible = false;

	this.readyBtnSprite.group.visible = false;
	this.inviteBtnSprite.group.visible = false;
	this.leaveBtnSprite.group.visible = false;
	this.chatBtnSprite.group.visible = false;
	this.audioBtnSprite.group.visible = false;

	// this.actionShowsSprite.clear();
	// this.actionBtnsSprite.clear();
	// this.whiteCardSprite.group.visible = false;

	this.leftTimeTextSprite.clear();

	// this.resultSprite.clear();

	this.menuSprite.group.visible = false;
	this.menuSprite.subMenuGroup.visible = false;
	this.requestDismissSprite.group.visible = false;

	this.inviteHintSprite.group.visible = false;

	this.grayBackGround.visible = false;

	// this.maimaSprite.clear();

	// for(var i = 0;i< this.tuoguanSpriteArr.length;i++){
	// this.tuoguanSpriteArr[i].visible = false;
	// }
	// this.goonBtnSprite.group.visible = false;

	this.cardsShootSprite.clear();
	this.cardsShootSprite.group.visble = false;
	
	for(var i = 0;i < this.threeCardsArr.length;i++){
		this.threeCardsArr[i].group.visible = false;
	}
	
	this.resultSprite.clear();
}
