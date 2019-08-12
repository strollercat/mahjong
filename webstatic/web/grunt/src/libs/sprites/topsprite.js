function TopSprite(width) {
	this.width = width;
	this.height;

	this.roomId;
	
	this.roomIdSprite;
	this.topSprite;
}
TopSprite.prototype = util.inherit(GroupSprite.prototype);
TopSprite.prototype.constructor = TopSprite;
TopSprite.prototype.layout = function(wh){
	this.width = wh.w;
	var topWidth = this.width;
	var topHeight = topWidth / 598 * 64;
	this.height = topHeight;
	var middleWidth = (394 - 195) / 598 * topWidth;
	var middleoffx = 195 / 598 * topWidth;
	var middleoffy = 13 / 64 * this.height;
	var middleHeight = (42 - 13) / 64 * this.height;
	var spTop, spRoomid;

	this.topSprite.width = topWidth;
	this.topSprite.height = topHeight;

	this.roomIdSprite.group.x = middleoffx;
	this.roomIdSprite.group.y = middleoffy;
	this.roomIdSprite.layout({w:middleWidth,h:middleHeight,fh:middleHeight});
	if(this.roomId){
		this.roomIdSprite.setText('房间号: ' + this.roomId);
	}
	
	this.quanTextSprite.layout({w:middleWidth,h:middleHeight,fh:middleHeight});
	this.quanTextSprite.group.x = middleoffx *1.25 + middleWidth;
	this.quanTextSprite.group.y = middleoffy;
	this.quanTextSprite.setTextToLeft(this.quanTextSprite.getText());
};
TopSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.topSprite = game.add.sprite(0, 0, 'other');
	this.topSprite.frameName = 'top';
	this.group.add(this.topSprite);

	var style = {
			font : "30px 'Arial'",
			fill : '#DCDCDC',
			boundsAlignH : "center",
			boundsAlignV : "middle"
	};
	this.roomIdSprite = new LineTextSprite(0,0,0,style);
	this.roomIdSprite.create();
	this.group.add(this.roomIdSprite.group);
	
	style.fill = '#FFFF00';
	this.quanTextSprite = new LineTextSprite(0,0,0,style);
	this.quanTextSprite.create();
//	this.quanTextSprite.setTextToLeft('南风圈');
	this.group.add(this.quanTextSprite.group);
	
}

TopSprite.prototype.setQuan = function(quan){
	if(quan === 0){
		this.quanTextSprite.setTextToLeft('东风圈');
	}else if(quan === 1){
		this.quanTextSprite.setTextToLeft('南风圈');
	}else if(quan === 2){
		this.quanTextSprite.setTextToLeft('西风圈');
	}else if(quan === 3){
		this.quanTextSprite.setTextToLeft('北风圈');
	}else{
		this.quanTextSprite.setTextToLeft('');
	}
	
}

TopSprite.prototype.setRoomId = function(roomId) {
	this.roomIdSprite.setText('房间号: ' + roomId);
	this.roomId = roomId;
};
TopSprite.prototype.getRoomId = function() {
	return this.roomId;
};
TopSprite.test = function() {
	var top = new TopSprite(game.world.width / 2);
	top.create();
	top.layout({w:game.world.width / 2});
	top.group.x = 10;
	top.group.y = 10;
	top.setRoomId('123456');
	setTimeout(function(){
		top.layout({w:game.world.width});
	},2000);
	
//	top.setRoomId('123456');
//	top.setRoomId('1234567');
//	console.log(top.getRoomId());
}