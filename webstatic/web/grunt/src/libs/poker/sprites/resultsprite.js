function SingleResultSprite() {
	
	
	this.cardsArr =[];
	this.nameArr= [];
	this.scoreSprite ;
	this.headUs;
	
	this.height;
	
	
}
SingleResultSprite.prototype = util.inherit(GroupSprite.prototype);
SingleResultSprite.prototype.constructor = SingleResultSprite;

SingleResultSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	
	this.headUs = new HeaderUserSprite(0);
	this.headUs.create();
	this.headUs.fangZhu(false);
	this.headUs.offlineSprite.visible = false;
	this.headUs.zuan(false);
	this.headUs.ready(false);
	this.group.add(this.headUs.group);

	for(var i = 0;i< 3;i++){
		var s = new CardsSprite();
		s.create();
		s.setRatio(0.4);
		this.group.add(s.group);
		this.cardsArr.push(s);
		
		s =  new LineTextSprite(0,0,0,'#ffffff');
		s.create();
		this.group.add(s.group);
		this.nameArr.push(s);
	}
		
	this.scoreSprite = new LineTextSprite(0,0,0,'#ffffff');
	this.scoreSprite.create();
	this.group.add(this.scoreSprite.group);
	
	
	
}

SingleResultSprite.prototype.layout = function(wh){
	this.height = wh.h;
	
	var userWidth = game.world.width/5;
	var cardWidth = userWidth/2.5;
	var cardHeight = this.height/10 *6;
	
	
	this.headUs.layout({w:userWidth});
	this.headUs.group.y = this.height - cardHeight;
	this.headUs.group.x = 0;
	
	var cardWidth = userWidth/2.5;
	var cardHeight = this.height/10 *6;
//	var cardWidth = 80;
//	var cardHeight = 100;
	var fontHeight = this.height - cardHeight;
	for(var i = 0;i< 3;i++){
		this.cardsArr[i].layout({w:cardWidth,h:cardHeight});
		this.cardsArr[i].group.y = this.height - cardHeight;
		
		this.nameArr[i].layout({w:this.cardsArr[i].getTotalWidth(),h:fontHeight,fh:fontHeight});
		this.nameArr[i].group.y = 0;
	}
	console.log(cardWidth +" "+this.cardsArr[0].getTotalWidth());
//	this.cardsArr[0].group.x = game.world.width;
//	
	this.cardsArr[0].group.x = this.headUs.group.x + 1.1*userWidth ;
	this.cardsArr[1].group.x = this.cardsArr[0].group.x + 0.5 *cardWidth +this.cardsArr[0].getTotalWidth();
	this.cardsArr[2].group.x = this.cardsArr[1].group.x +0.5* cardWidth +this.cardsArr[1].getTotalWidth();
	for(var i = 0;i< 3;i++){
		this.nameArr[i].group.x = this.cardsArr[i].group.x;
		this.nameArr[i].setText(this.nameArr[i].getText());
	}
	
	
	var textWidth = this.height / 4;
	this.scoreSprite.layout({w:textWidth,h:textWidth,fh:textWidth});
	this.scoreSprite.setText(this.scoreSprite.getText());
	this.scoreSprite.group.x = game.world.width  - 1.5* textWidth;
	this.scoreSprite.group.y = (this.height - textWidth) / 2;
	
};

SingleResultSprite.prototype.clear = function(){
	this.headUs.zuan(false);
	this.scoreSprite.setText('');
	for(var i = 0;i< 3;i++){
		this.nameArr[i].setText('');
		this.cardsArr[i].clear();
	}
};
SingleResultSprite.prototype.show = function(headUrl,cns,nameArr, score){
	
	this.scoreSprite.setText(score+'');
	
	this.cardsArr[0].addCard(cns.slice(0, 3));
	this.cardsArr[1].addCard(cns.slice(3, 8));
	this.cardsArr[2].addCard(cns.slice(8, 13));
	
	for(var i = 0;i< nameArr.length;i++){
		this.nameArr[i].setText(nameArr[i]);
	}
	
	this.headUs.group.visible = true;
	this.headUs.changeHeadUrl(headUrl);
	
	this.layout({h:this.height});
	
};




SingleResultSprite.test = function(){
	var srs = new SingleResultSprite();
	srs.create();
	srs.layout({h:game.world.height/6});
	
	srs.show('',[0,1,2,3,4,5,6,7,8,9,10,11,12],['对子','两对','乌龙'],-1);
	srs.group.y = game.world.height/2;
}

function ResultSprite(gameSprite) {
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
		var srs = new SingleResultSprite();
		srs.create();
		this.singleResultArr.push(srs);
		this.group.add(srs.group);
	}
	
	
	this.roomInfoTextSprite = new LineTextSprite(0,0,0,'#ffffff');
	this.roomInfoTextSprite.create();
	this.group.add(this.roomInfoTextSprite.group);
}


ResultSprite.prototype.layout = function(){
	
	this.bgSprite.width = game.world.width;
	this.bgSprite.height = game.world.height;
	
	var btnHeight = game.world.height / 6 / 2;
	var btnWidth = btnHeight / 78 * 188;
	this.readyBtn.layout({w:btnWidth,h:btnHeight});
	this.readyBtn.group.x = game.world.width / 2 - btnWidth / 2;
	this.readyBtn.group.y = game.world.height / 6 * 5 + btnHeight / 4;
	
	var topWidth = game.world.width / 2;
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




ResultSprite.prototype.show = function(dataArr,handler,roomInfo,score) {
	if (!dataArr || dataArr.length == 0) {
		return;
	}
	this.dataArr = dataArr;
	this.handler = handler;
	
	this.bgSprite.visible = true;
	this.readyBtn.group.visible = true;
	
	for (var i = 0; i < dataArr.length; i++) {
		data = dataArr[i];
		singleResult = this.singleResultArr[i];
		singleResult.show(data.headUrl,data.cns,data.nameArr,data.score);
	}
	if (score == 0) { // 平局
		this.topPing.visible  = true;
		if(game.audioManager){
			game.audioManager.playByKey('lose');
		}
	} else if (score > 0) { // 胜利
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
		this.singleResultArr[i].headUs.group.visible = false;
	}
	this.group.visible = false;
	
}
ResultSprite.test = function(){
	var rs = new ResultSprite();
	rs.create();
	rs.layout();
	
	var dataArr = [];
	for(var i = 0;i<4;i++){
		var data ={};
//		data.dir = {absDir:i,relDir:i};
		data.nameArr = ['对子','两对','乌龙'];
		data.cns = [0,1,2,3,4,5,6,7,8,9,10,11,12];
		data.score = 100;
		data.headUrl = '';
		dataArr.push(data);
	}
	rs.show(dataArr,function(btn){console.log('click')},'fuck',1);
}
	