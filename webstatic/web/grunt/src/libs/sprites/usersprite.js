function ChatText(height){
	this.height = height;
	this.width ;
	this.lineTextSprite;
	this.chatTextBox;
	
	this.lastTimer;
};
ChatText.prototype = util.inherit(GroupSprite.prototype);
ChatText.prototype.constructor = ChatText;

ChatText.prototype.layout = function(wh){
	this.height = wh.h;
	
   
	this.lineTextSprite.layout({w:0,h:this.height,fh:this.height});
	var width = this.lineTextSprite.setTextToLeft(this.lineTextSprite.getText());
	
	this.chatTextBox.height = this.height ;
	this.chatTextBox.width = width + 1 * this.height;
	this.width = width + 1 * this.height;
	this.chatTextBox.x = - 0.5*this.height;
};

ChatText.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
//	this.chatTextBox = game.add.sprite(0,0,'chat.textbox');
	this.chatTextBox = game.add.image(0,0,'other');
	this.chatTextBox.frameName = 'box';
   	this.group.add(this.chatTextBox);
   	
   	this.lineTextSprite = new LineTextSprite(0,0,0,'#ffffff');
	this.lineTextSprite.create();
//	this.lineTextSprite.group.visible = false;
	this.group.add(this.lineTextSprite.group);
};
ChatText.prototype.showText = function(text){
	if(!text){
		return ;
	}
	this.chatTextBox.visible = true;
	this.lineTextSprite.group.visible = true;
	
	var width = this.lineTextSprite.setTextToLeft('.'+text);
	this.chatTextBox .width = width + 1 * this.height;
	this.width = width + 1 * this.height;
	this.chatTextBox.x = - 0.5*this.height;
	
	if(this.lastTimer){
		game.time.events.remove(this.lastTimer);
	}
	this.lastTimer = game.time.events.add(Phaser.Timer.SECOND * 3, function(){
		this.chatTextBox.visible = false;
		this.lineTextSprite.group.visible = false;
	}, this);
} 
ChatText.test=  function(){
	var ct = new ChatText(40);
	ct.create();
	ct.layout({h:20});
	ct.showText("你麻痹");
}

function HeaderUserSprite(width) {
	this.width = width;
	this.margin;

	this.boxSprite;
	this.emptySprite;
	this.userSprite;
	this.zuanSprite;
	this.readySprite;
	this.fangzhuSprite;
	this.offlineSprite;

	this.imgUrl;
}
HeaderUserSprite.prototype = util.inherit(GroupSprite.prototype);
HeaderUserSprite.prototype.constructor = HeaderUserSprite;

HeaderUserSprite.prototype.layout = function(wh){
	this.width = wh.w;
	this.margin = 10 / 113 * this.width;
	
	this.boxSprite.width = this.width;
	this.boxSprite.height = this.width;
	
	this.emptySprite.x = this.margin;
	this.emptySprite.y = this.margin;
	this.emptySprite.width = this.width - 2 * this.margin;
	this.emptySprite.height = this.width - 2 * this.margin;
	
	if(this.userSprite){
		this.userSprite.x = this.margin;
		this.userSprite.y = this.margin;
		this.userSprite.width = this.width - 2 * this.margin;
		this.userSprite.height = this.width - 2 * this.margin;
	}

	this.zuanSprite.x = this.width/2;
	this.zuanSprite.width = this.width / 2;
	this.zuanSprite.height = this.width / 2;

	var readyHeight = this.width / 88 * 39;
	this.readySprite.y = -readyHeight;
	this.readySprite.width = this.width;
	this.readySprite.height = readyHeight;

	this.fangzhuSprite.width = this.width / 2;
	this.fangzhuSprite.height = this.width / 4;
	
	this.offlineSprite.width = this.width - 2 * this.margin;
	this.offlineSprite.height = this.offlineSprite.width / 44 * 16;
	this.offlineSprite.y = this.width / 2 - this.offlineSprite.height / 2;
	this.offlineSprite.x = this.margin;

}

HeaderUserSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);


	this.emptySprite = game.add.image(0, 0, 'userinfo');
	this.emptySprite.frameName = "head_empty";
	this.group.add(this.emptySprite);

	this.boxSprite = game.add.image(0, 0, 'userinfo');
	this.boxSprite.frameName = "userinfo_head";
	this.group.add(this.boxSprite);

	this.zuanSprite = game.add.image(0, 0, 'userinfo');
	this.zuanSprite.frameName = "userinfo_zuan";
	this.group.add(this.zuanSprite);

	this.readySprite = game.add.image(0, 0, 'userinfo');
	this.readySprite.frameName = 'ready';
	this.group.add(this.readySprite);

	this.fangzhuSprite = game.add.image(0, 0, 'userinfo');
	this.fangzhuSprite.frameName = 'fangzhu';
	this.group.add(this.fangzhuSprite);
	
	this.offlineSprite = game.add.image(0, 0, 'userinfo');
	this.offlineSprite.frameName = 'offline';
	this.group.add(this.offlineSprite);

	game.load.onFileComplete.add(function(progress, cacheKey, success,
			totalLoaded, totalFiles) {
		if (this.imgUrl == cacheKey) {
			this.changeUserImage(cacheKey);
		}
	}, this);
}

HeaderUserSprite.prototype.changeUserImage = function(url) {
	if (this.userSprite) {
		this.group.remove(this.userSprite);
		this.userSprite.destroy();
	}
	this.userSprite = game.add.image(this.margin, this.margin, url);
	this.userSprite.width = this.width - 2 * this.margin;
	this.userSprite.height = this.width - 2 * this.margin;
	this.group.add(this.userSprite);

	this.group.bringToTop(this.boxSprite);
	this.group.bringToTop(this.zuanSprite);
	this.group.bringToTop(this.readySprite);
	this.group.bringToTop(this.fangzhuSprite);
};
HeaderUserSprite.prototype.removeHeadImage = function() {
	if (this.userSprite) {
		this.group.remove(this.userSprite);
		this.userSprite.destroy();
		this.imgUrl = null;
	}
}
HeaderUserSprite.prototype.changeHeadUrl = function(url) {
	if(this.imgUrl == url){
		return ;
	}
	if(game.cache.checkImageKey(url)){
		this.removeHeadImage();
		this.imgUrl = url;
		this.changeUserImage(url);
		return ;
	}
	this.imgUrl = url;
	game.load.image(url, url);
	game.load.start();
}
HeaderUserSprite.prototype.zuan = function(zuan) {
	this.zuanSprite.visible = zuan;
}
HeaderUserSprite.prototype.ready = function(ready) {
	this.readySprite.visible = ready;
}
HeaderUserSprite.prototype.fangZhu = function(fangzhu) {
	this.fangzhuSprite.visible = fangzhu;
}

function HuaUserSprite(width) {
	this.width = width;

	this.normalHuaSpriteArr;
	this.huaSpriteArr;
}

HuaUserSprite.prototype = util.inherit(GroupSprite.prototype);
HuaUserSprite.prototype.constructor = HuaUserSprite;



HuaUserSprite.prototype.layout = function(wh) {
	this.width = wh.w;

	var sprite, i;
	var boxWidth = this.width;

	for (i = 0; i < 8; i++) {
		sprite = this.normalHuaSpriteArr[i];
		if (i < 4) {
			sprite.x = (i % 4) * (boxWidth / 4);
			sprite.y = boxWidth / 4 * 0;
		} else {
			sprite.x = (i % 4) * (boxWidth / 4);
			sprite.y = boxWidth / 4 * 1;
		}
		sprite.width = boxWidth / 4;
		sprite.height = boxWidth / 4;

		sprite = this.huaSpriteArr[i];
		if (i < 4) {
			sprite.x = (i % 4) * (boxWidth / 4);
			sprite.y = 0;
		} else {
			sprite.x = (i % 4) * (boxWidth / 4);
			sprite.y = boxWidth / 4 * 1;
		}
		sprite.width = boxWidth / 4;
		sprite.height = boxWidth / 4;
	}
};

HuaUserSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.normalHuaSpriteArr = [];
	this.huaSpriteArr = [];

	var sprite, i;

	for (i = 0; i < 8; i++) {
		if (i < 4) {
			sprite = game.add.image(0,0, 'userinfo' );
			sprite.frameName = 'flower_normal_'+i;
		} else {
			sprite = game.add.image(0,0, 'userinfo');
			sprite.frameName = 'flower_normal_'+i;
		}
		this.group.add(sprite);
		this.normalHuaSpriteArr.push(sprite);

		if (i < 4) {
			sprite = game.add.image(0,0, 'userinfo');
			sprite.frameName = 'flower_press_'+i;
		} else {
			sprite = game.add.image(0,0, 'userinfo');
			sprite.frameName = 'flower_press_'+i;
		}
		sprite.visible = false;
		this.group.add(sprite);
		this.huaSpriteArr.push(sprite);
	}
};

HuaUserSprite.prototype.addHuaArr = function(arr) {
	if (arr && arr.length > 0) {
		var i, number;
		for (i = 0; i < arr.length; i++) {
			number = arr[i];
			number = number - 136;
			this.huaSpriteArr[number].visible = true;
			this.normalHuaSpriteArr[number].visible = false;
		}
	}
	this.group.visible = true;
}
HuaUserSprite.prototype.getHuaArr = function() {
	var i, len = this.huaSpriteArr.length, sprite, retArr = [];
	for (i = 0; i < len; i++) {
		sprite = this.huaSpriteArr[i];
		if (sprite.visible) {
			retArr.push(i + 136);
		}
	}
	return retArr;
}
HuaUserSprite.prototype.clearHua = function() {
	var i;
	for (i = 0; i < 8; i++) {
		this.huaSpriteArr[i].visible = false;
		this.normalHuaSpriteArr[i].visible = true;
	}
}
function UserSprite(width) {
	this.width = width;

	this.headUs;
	this.twoLineSprite;
	this.huaUs;
	
	this.faceAnimate;
	this.voiceAnimate;
	this.chatText;
	
	this.fightInfo;
	this.fightInfoSprite;
	
}

UserSprite.prototype = util.inherit(GroupSprite.prototype);
UserSprite.prototype.constructor = UserSprite;

UserSprite.prototype.setName = function(name) {
	this.twoLineSprite.setText1(name);
};
UserSprite.prototype.setScore = function(score) {
	this.twoLineSprite.setText2(score);
};
UserSprite.prototype.getName = function() {
	return this.twoLineSprite.getText1();
};
UserSprite.prototype.getScore = function() {
	return this.twoLineSprite.getText2();
};
UserSprite.prototype.addHuaArr = function(arr) {
	this.huaUs.addHuaArr(arr);
}
UserSprite.prototype.getHuaArr = function() {
	return this.huaUs.getHuaArr();
}


UserSprite.prototype.layout = function(wh) {
	this.width = wh.w;

	var headWidth = this.width / 5 * 4;

	this.headUs.layout({w:headWidth});
	this.headUs.group.x = this.width / 2 - headWidth / 2;
	
	this.twoLineSprite.layout(wh);
	this.twoLineSprite.group.y = headWidth;
	
	this.huaUs.layout(wh);
	this.huaUs.group.y = headWidth + this.twoLineSprite.ch;
	
	this.faceAnimate.layout({w:this.width/3*2});
	this.faceAnimate.group.x = this.width/3*1;
	this.faceAnimate.group.y = 0;
	
   	this.voiceAnimate.width = this.width/2;
   	this.voiceAnimate.height = this.width/2;
   	this.voiceAnimate.x = this.width/2 - this.voiceAnimate.width/2;
   	this.voiceAnimate.y = this.voiceAnimate.x - this.voiceAnimate.width/10;
   	
   	this.chatText.layout({h:this.width/3});
	this.chatText.group.x = this.width/2;
	this.chatText.group.y = 0;
	
	
	if(this.fightInfoSprite){
		this.fightInfoSprite.layout({w:game.world.width/2,h:0});
		this.fightInfoSprite.group.x = game.world.width/2 - this.fightInfoSprite.width/2;
		this.fightInfoSprite.group.y = game.world.height/2 - this.fightInfoSprite.height/2;
	}
	
}



UserSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);


	this.headUs = new HeaderUserSprite(0);
	this.headUs.create();
	this.group.add(this.headUs.group);

	this.twoLineSprite = new TwoLineTextSprite(0,'#DCDCDC','#ffff00');
	this.twoLineSprite.create();
	this.group.add(this.twoLineSprite.group);

	this.huaUs = new HuaUserSprite(0);
	this.huaUs.create();
	this.group.add(this.huaUs.group);
	
	
	this.faceAnimate = new FaceAnimateSprite(0);
	this.faceAnimate.create();
	this.group.add(this.faceAnimate.group);
	this.faceAnimate.group.visible = false;
	
	this.voiceAnimate = game.add.sprite(0, 0, 'chat.showvoice');
   	this.voiceAnimate.animations.add('run');
   	this.group.add(this.voiceAnimate);
   	this.voiceAnimate.visible = false;
   	
	this.chatText = new  ChatText(0);
	this.chatText.create();
	this.group.add(this.chatText.group);
	this.chatText.group.visible = false;
	
	this.headUs.boxSprite.inputEnabled = true;
   	this.headUs.boxSprite.events.onInputDown.add(function(){
   		this.showFightInfo();
   	}, this);
   
}
//UserSprite.prototype.createResult = function() {
//	GroupSprite.prototype.create.apply(this, arguments);
//
//	var headWidth = this.width;
//
//	this.headUs = new HeaderUserSprite(headWidth);
//	this.headUs.create();
//	this.headUs.imgUrl="";
//	this.group.add(this.headUs.group);
//
//	this.twoLineSprite = new TwoLineTextSprite(this.width,'#ffffff','#ffff00');
//	this.twoLineSprite.create();
//	this.twoLineSprite.group.x = this.width;
//	this.group.add(this.twoLineSprite.group);
//
//	this.huaUs = new HuaUserSprite(this.width);
//	this.huaUs.create();
//	this.huaUs.group.x = this.width;
//	this.huaUs.group.y = this.width / 2;
//	this.group.add(this.huaUs.group);
//
//}
UserSprite.prototype.ready = function(ready) {
	this.headUs.ready(ready);
}
UserSprite.prototype.zuan = function(show) {
	this.headUs.zuan(show);
}
UserSprite.prototype.fangZhu = function(show) {
	this.headUs.fangZhu(show);
}
UserSprite.prototype.changeHeadUrl = function(url) {
	this.headUs.changeHeadUrl(url);
}
UserSprite.prototype.removeHeadImage = function() {
	this.headUs.removeHeadImage();
}
UserSprite.prototype.getHeadUrl = function() {
	return this.headUs.imgUrl;
}
UserSprite.prototype.clear = function() {
//	if (this.headUs.userSprite) {
//		this.headUs.group.remove(this.headUs.userSprite);
//		this.headUs.userSprite.destroy();
//		this.headUs.userSprite = null;
//	}

	this.setName('');
	this.setScore('');
	this.huaUs.clearHua();
	this.headUs.zuanSprite.visible = false;
	this.headUs.fangzhuSprite.visible = false;
	this.headUs.offlineSprite.visible = false;
	this.headUs.readySprite.visible = false;
	this.headUs.imgUrl = '';
	this.huaUs.group.visible = false;

}
//UserSprite.prototype.toResult = function(width) {
//	var userSprite = new UserSprite(width);
//	userSprite.createResult();
//	userSprite.setName(this.getName());
//	userSprite.setScore(this.getScore());
//	userSprite.addHuaArr(this.getHuaArr());
//	userSprite.changeHeadUrl(this.getHeadUrl());
//	userSprite.zuan(this.headUs.zuanSprite.visible);
//	return userSprite;
//}
UserSprite.prototype.offline = function() {
	this.headUs.offlineSprite.visible = true;
	this.headUs.group.bringToTop(this.headUs.offlineSprite);
}
UserSprite.prototype.online = function() {
	this.headUs.offlineSprite.visible = false;
}
UserSprite.prototype.showFace = function(name,sec) {
	this.faceAnimate.group.visible = true;
	this.faceAnimate.play(name,sec);
}
UserSprite.prototype.showVoice = function(sec) {
	this.voiceAnimate.visible = true;
    this.voiceAnimate.animations.play('run', sec, true);
    game.time.events.add(Phaser.Timer.SECOND * sec, function(){
		this.voiceAnimate.animations.stop('run',false);
		this.voiceAnimate.visible = false;
	}, this);
}
UserSprite.prototype.showText = function(text,rightPos){
	this.chatText.group.visible = true;
	this.chatText.showText(text);
	if(!rightPos){
		this.chatText.group.x = -(this.chatText.width - this.width);
	}
};



UserSprite.prototype.showFightInfo = function(){
//	console.log('showFightInfo');
	if(this.fightInfoSprite){
		if(game.audioManager){
			game.audioManager.playByKey('click');
		}
		this.fightInfoSprite.group.visible = true;
		game.world.bringToTop(this.fightInfoSprite.group);
//		this.group.bringToTop(this.fightInfoSprite.group);
		return ;
	}
	
	if(this.fightInfo){
		this.fightInfoSprite = new  UserInfoSprite(0,0,this.getName(),this.getHeadUrl(),this.fightInfo.win,this.fightInfo.lose,this.fightInfo.ping,this.fightInfo.score,this.fightInfo.money,this.fightInfo.dir);
		this.fightInfoSprite.commandHandler = this.fightInfo.commandHandler;
		this.fightInfoSprite.authorDir = this.fightInfo.authorDir;
		this.fightInfoSprite.create();
		this.fightInfoSprite.layout({w:game.world.width/2,h:0});
		this.fightInfoSprite.group.x = game.world.width/2 - this.fightInfoSprite.width/2;
		this.fightInfoSprite.group.y = game.world.height/2 - this.fightInfoSprite.height/2;
		
		this.showFightInfo();
	}
	
};

UserSprite.test = function() {

	var userS = new UserSprite(game.config.authorBoxCh);
	userS.create();
	userS.layout({w:game.world.width/17 * 1.5});
	userS.setName('zbh');
	userS.setScore('1000');
	userS.zuan(true);
	userS.fangZhu(true);
	userS.addHuaArr([ 136, 137, 138, 139,141 ]);
	userS.changeHeadUrl("http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqmibYgzhSR2S3X916YSdxKaW7fLLp4T0NGicovpXWdicLBTWNsYcQV0mMUmj8bFMzuJXEQ8O3VZZaQg/0");
	userS.changeHeadUrl("http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqmibYgzhSR2S3X916YSdxKaW7fLLp4T0NGicovpXWdicLBTWNsYcQV0mMUmj8bFMzuJXEQ8O3VZZaQg/0");
	console.log(userS.getHuaArr());
	console.log(userS.getHeadUrl());
	userS.offline();
	userS.group.x = game.world.width / 2;
	userS.group.y = game.world.height / 2;
	
	userS.showFace('happy',3);
	userS.showVoice(3);
	
	userS.showText("快点吧，我等的花儿都谢了!");
	
	var fightInfo ={};
	fightInfo.win =3;
	fightInfo.lose = 4;
	fightInfo.ping = 5;
	fightInfo.score = -100;
	fightInfo.money = 102;
	fightInfo.dir = 0;
	userS.fightInfo = fightInfo;
	userS.showFightInfo();

//	var userS = new HeaderUserSprite(game.config.authorBoxCh);
//	userS.create();
//	userS.changeHeadUrl("http://127.0.0.1/web/majiang/img/gameTable/userinfo/head.jpg");

}
