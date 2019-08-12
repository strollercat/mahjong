function SingleResultSprite(gameSprite) {
	this.gameSprite = gameSprite;
	
	
	this.dir ;
	this.ics ;
	this.mcs ;
	this.score ;
	this.hu ;
	
	this.height;
	
	
	this.headUs;
	this.nameUs ;
	this.huaUs ;
	this.cards0Sprite;
	this.scoreSprite;
	this.huSprite;
	this.huDetailSprite;
	
	
}
SingleResultSprite.prototype = util.inherit(GroupSprite.prototype);
SingleResultSprite.prototype.constructor = SingleResultSprite;

SingleResultSprite.prototype.clear = function(){
	this.headUs.zuan(false);
	this.nameUs.setText1('');
	this.nameUs.setText2('');
	this.huaUs.clearHua();
	this.cards0Sprite.clear();
	this.cards0Sprite.innerGroup.x = this.cards0Sprite.middleGroup.x;
	this.scoreSprite.setText('');
	this.huSprite.visible = false;
	this.huDetailSprite.setText('');
};

SingleResultSprite.prototype.layout = function(wh){
	this.height = wh.h;
	
	var userWidth = this.height / 10 * 8;
	this.headUs.layout({w:userWidth});
	this.headUs.group.y = (this.height - userWidth) / 2;
	this.headUs.group.x = userWidth;
	this.nameUs.layout({w:userWidth});
	this.nameUs.group.y = this.headUs.group.y;
	this.nameUs.group.x = this.headUs.group.x + userWidth;
	this.huaUs.layout({w:userWidth});
	this.huaUs.group.x =this.headUs.group.x + userWidth;
	this.huaUs.group.y = this.headUs.group.y + userWidth/2;
	
	var huWidth = this.height / 2;
	this.huSprite.width = huWidth;
	this.huSprite.height = this.huSprite.width;
	this.huSprite.x = game.world.width - 2 * huWidth;
	this.huSprite.y = (this.height - huWidth) / 2;
	
	var textWidth = this.height / 4;
	this.scoreSprite.layout({w:textWidth,h:textWidth,fh:textWidth});
	this.scoreSprite.setText(this.scoreSprite.getText());
	this.scoreSprite.group.x = game.world.width - 2 * huWidth - 2 * textWidth;
	this.scoreSprite.group.y = (this.height - textWidth) / 2;
	
	
	var cardWidth = CardSprite.getWidthByHeight(0, this.height / 2);
	this.cards0Sprite.layout({iw:cardWidth,mw:cardWidth,ow:0});
//	this.cards0Sprite.group.x = 3.5 * userWidth;
//	this.cards0Sprite.group.y = (this.height - cardWidth) / 3 * 2;
	this.cards0Sprite.middleGroup.x = 3.5 * userWidth;
	this.cards0Sprite.innerGroup.x = 3.5 * userWidth;
	this.cards0Sprite.middleGroup.y = (this.height - cardWidth) / 3 * 2;
	this.cards0Sprite.innerGroup.y = (this.height - cardWidth) / 3 * 2;
	
	var fontHeight =(this.height - cardWidth) / 3 * 2
	this.huDetailSprite.layout({w:game.world.width / 3,h:fontHeight,fh:fontHeight/2});
	this.huDetailSprite.setText(this.huDetailSprite.getText());
	this.huDetailSprite.group.x = game.world.width / 3;
	
};
SingleResultSprite.prototype.show = function(dir, ics, mcs, score, hu){
	this.dir = dir;
	this.ics = ics;
	this.mcs = mcs;
	this.score = score;
	this.hu = hu;
	
	var i,card;
	this.cards0Sprite.addInnerCard(this.ics);
	if(this.ics.length %3 ==2){
		this.cards0Sprite.seperateInnerCard();
	}
	if (this.mcs && this.mcs.length > 0) {
		for (i = 0; i < this.mcs.length; i++) {
			card = this.mcs[i];
			if (card.a == 'peng') {
				this.cards0Sprite.addMiddlePengCard(card.cns[0], 0);
			} else if (card.a == 'chi') {
				this.cards0Sprite.addMiddleChiCard(card.cns, 0, card.cn);
			} else if (card.a == 'angang') {
				this.cards0Sprite.addMiddleAnGangCard(card.cns[0]);
			} else if (card.a == 'minggang' || card.a == 'xiangang') {
				this.cards0Sprite.addMiddleMingGangCard(card.cns[0], 0);
			}
		}
	}
	if(this.hu){
		this.huSprite.visible = true;
		this.huDetailSprite.setText(this.hu);
	}
	if(this.score > 0){
		this.score = '+'+this.score;
	}
	this.scoreSprite.setText(this.score);
	
	if(this.gameSprite == null){
		return ;
	}
	var relDir = this.dir.rel;
	var oldU = this.gameSprite.userSpriteArr[relDir];
	this.headUs.changeHeadUrl(oldU.getHeadUrl());
	this.headUs.zuan(oldU.headUs.zuanSprite.visible);
	this.nameUs.setText1(oldU.getName());
	this.nameUs.setText2(oldU.getScore());
	this.huaUs.addHuaArr(oldU.getHuaArr());
	
};


SingleResultSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	
	this.headUs = new HeaderUserSprite(0);
	this.headUs.create();
	this.headUs.fangZhu(false);
	this.headUs.offlineSprite.visible = false;
	this.headUs.zuan(false);
	this.headUs.ready(false);
	this.group.add(this.headUs.group);

	this.nameUs = new TwoLineTextSprite(0,'#ffffff','#ffff00');
	this.nameUs.create();
	this.group.add(this.nameUs.group);

	this.huaUs = new HuaUserSprite(0);
	this.huaUs.create();
	this.group.add(this.huaUs.group);
	
	
	this.cards0Sprite = new Cards0Sprite(0,0,0,null);
	this.cards0Sprite.create();
	this.cards0Sprite.outterGroup.visible = false;
	this.cards0Sprite.showArrow(false);
	this.cards0Sprite.clickEnabled(false);
//	this.group.add(this.cards0Sprite.group);
	this.group.add(this.cards0Sprite.innerGroup);
	this.group.add(this.cards0Sprite.middleGroup);
	
	this.huDetailSprite = new LineTextSprite(0,0,0,'#ffffff');
	this.huDetailSprite.create();
	this.group.add(this.huDetailSprite.group);
	
	this.scoreSprite = new LineTextSprite(0,0,0,'#ffffff');
	this.scoreSprite.create();
	this.group.add(this.scoreSprite.group);
	
	this.huSprite = game.add.image(0, 0, 'actionshows');
	this.huSprite.frameName = 't_hu';
	this.group.add(this.huSprite);
	
	
}
SingleResultSprite.test = function(){
	var srs = new SingleResultSprite(null);
	srs.create();
	srs.layout({h:game.world.height/6});
	
	srs.show({absDir:0,relDir:0},[0,1,2,3,4],[{a:'gang',cns:[0,1,2,3],who:1,cn:-1}],100,'清一色魂一色');
}

function ResultSprite(gameSprite) {
	this.gameSprite = gameSprite;
	this.dataArr ;
	
	this.bgSprite;
	this.topSuccess;
	this.topFail;
	this.topPing;
	
	this.readyBtn;
	
	this.singleResultArr;
	
	this.roomInfoTextSprite;
	
}
ResultSprite.prototype = util.inherit(GroupSprite.prototype);
ResultSprite.prototype.constructor = ResultSprite;

ResultSprite.prototype.layout = function(){
	
	this.bgSprite.width = game.world.width;
	this.bgSprite.height = game.world.height;
	
	var btnHeight = game.world.height / 6 / 2;
	var btnWidth = btnHeight / 78 * 188;
	this.readyBtn.layout({w:btnWidth,h:btnHeight});
	this.readyBtn.group.x = game.world.width / 2 - btnWidth / 2;
	this.readyBtn.group.y = game.world.height / 6 * 5 + btnHeight / 4;
	
	var topWidth = game.world.width / 4;
	var topHeight = topWidth / 347 * 125;
	this.topSuccess.width = topWidth;
	this.topSuccess.height = topHeight;
	this.topSuccess.x = game.world.width / 2 - topWidth / 2;
	this.topPing.width = topWidth;
	this.topPing.height = topHeight;
	this.topPing.x = game.world.width / 2 - topWidth / 2;
	this.topFail.width = topWidth;
	this.topFail.height = topHeight;
	this.topFail.x = game.world.width / 2 - topWidth / 2;
	
	for(var i = 0 ;i< 4;i++){
		this.singleResultArr[i].layout({h:game.world.height/6});
		this.singleResultArr[i].group.y = (i+1)*game.world.height/6;
	}
	
	this.layoutRoomInfoSprite();
	
};

ResultSprite.prototype.layoutRoomInfoSprite = function(){
	var fh = game.world.height/6/4;
	this.roomInfoTextSprite.layout({w:game.world.width,h:fh,fh:fh});
	this.roomInfoTextSprite.group.y = game.world.height/6 - 1.5*fh;
	this.roomInfoTextSprite.setText(this.roomInfoTextSprite.getText());
}

ResultSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.bgSprite = game.add.sprite(0, 0, 'result');
	this.bgSprite.frameName= 'background';
	this.group.add(this.bgSprite);
	
	this.readyBtn = new ButtonSprite(0,0,'ready_normal','ready_press',function(btn){
		btn.context.clear();
		btn.context.handler();
	},this);
	this.readyBtn.create();
	this.group.add(this.readyBtn.group);
	
	
	
	this.topPing = game.add.image(0, 0, 'result');
	this.topPing.frameName = 'pingju';
	this.group.add(this.topPing);
	this.topSuccess = game.add.image(0, 0, 'result');
	this.topSuccess.frameName = 'win' ;
	this.group.add(this.topSuccess);
	this.topFail = game.add.image(0, 0, 'result');
	this.topFail.frameName = 'lose';
	this.group.add(this.topFail);
	
	
	this.singleResultArr = [];
	for(var i = 0 ;i< 4;i++){
		var srs = new SingleResultSprite(this.gameSprite);
		srs.create();
		this.singleResultArr.push(srs);
		this.group.add(srs.group);
	}
	
	
	this.roomInfoTextSprite = new LineTextSprite(0,0,0,'#ffffff');
	this.roomInfoTextSprite.create();
	this.group.add(this.roomInfoTextSprite.group);
}



ResultSprite.prototype.show = function(dataArr,handler,roomInfo) {
	if (!dataArr || dataArr.length == 0) {
		return;
	}
	this.dataArr = dataArr;
	this.handler = handler;
	
	this.bgSprite.visible = true;
	this.readyBtn.group.visible = true;
	
	var i, len = dataArr.length, data, dir, ics, mcs, score, hu, singleResult, authorHuRet, sprite;
	for (i = 0; i < dataArr.length; i++) {
		data = dataArr[i];
		dir = data.dir;
		ics = data.i;
		mcs = data.m;
		score = data.score;
		hu = data.hu;
		if (dir.abs == dir.author) {
			authorHuRet = score;
		}
		singleResult = this.singleResultArr[i];
		singleResult.show(dir,ics,mcs,score,hu);
	}
	if (authorHuRet == 0) { // 平局
		this.topPing.visible  = true;
		if(game.audioManager){
			game.audioManager.playByKey('lose');
		}
	} else if (authorHuRet > 0) { // 胜利
		this.topSuccess.visible = true;
		if(game.audioManager){
			game.audioManager.playByKey('win');
		}
	} else { // 失败
		this.topFail.visible = true;
		if(game.audioManager){
			game.audioManager.playByKey('lose');
		}
	}
	
	this.roomInfoTextSprite.setText(roomInfo);
	this.layoutRoomInfoSprite();
	
	game.world.bringToTop(this.group);
	
	
}

ResultSprite.prototype.clear = function() {
//	this.group.destroy();
//	this.group = game.add.group();
	
	this.topFail.visible = false;
	this.topPing.visible = false;
	this.topSuccess.visible = false;
	for(var i = 0;i< 4;i++){
		this.singleResultArr[i].clear();
	}
	
	
	
	
	this.group.visible = false;
	
}
ResultSprite.test = function(){
	var rs = new ResultSprite(null);
	rs.create();
	rs.layout();
	
	var dataArr = [];
	for(var i = 0;i<4;i++){
		var data ={};
		data.dir = {absDir:i,relDir:i};
		data.i = [0,1,2,3,4,5,6,7,8,9,10];
		data.m = [{a:'gang',cns:[0,1,2,3],who:1,cn:-1}];
		data.score = 100;
		data.hu = '清一色魂一色';
		dataArr.push(data);
	}
	rs.show(dataArr,function(btn){console.log('click')});
}
	