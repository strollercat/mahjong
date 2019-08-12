function DirSprite() {
	this.r1;
	this.r2;
	this.dirWidth;

	this.spriteDirArr;
	this.originArr;
	
	this.originQuanArr;
	this.quan =0 ;
}
DirSprite.prototype = util.inherit(GroupSprite.prototype);
DirSprite.prototype.constructor = DirSprite;

DirSprite.prototype.layout = function(wh){
	this.r1 = wh.r1;
	this.r2 = wh.r2;
	this.dirWidth = this.r2 - this.r1;
	
	var sprite, i;
	for (i = 0; i < 4; i++) {
		sprite = this.originArr[i];
		sprite.width = this.dirWidth;
		sprite.height = this.dirWidth;
	};
	for (i = 0; i < 4; i++) {
		sprite = this.originQuanArr[i];
		sprite.width = this.dirWidth;
		sprite.height = this.dirWidth;
	};
	this.setDirXY();
	
	var spriteQuan = this.originQuanArr[this.quan];
	sprite = this.originArr[this.quan];
	spriteQuan.x = sprite.x;
	spriteQuan.y = sprite.y;
}

DirSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	var sprite, i;
	this.spriteDirArr = [];
	this.originArr = [];
	this.originQuanArr = [];
	for (i = 0; i < 4; i++) {
		sprite = game.add.image(0,0,'middle');
		sprite.frameName = 'dir_normal_'+i+'.png';
		this.spriteDirArr.push(sprite);
		this.originArr.push(sprite);
		this.group.add(sprite);
	};
	for (i = 0; i < 4; i++) {
		sprite = game.add.image(0,0,'middle');
		sprite.frameName = 'dir_press_'+i+'.png';
		sprite.visible = false;
		this.originQuanArr.push(sprite);
		this.group.add(sprite);
	};
}
DirSprite.prototype.setEast = function(dir) {
	this.spriteDirArr = util.getRightMoveArr(this.originArr, dir);
	this.setDirXY();
};
DirSprite.prototype.setQuan = function(dir){
	this.quan = dir;
	for(var i = 0;i< 4;i++){
		this.originQuanArr[i].visible = false;
		this.originArr[i].visible = true;
	};
	var spriteQuan = this.originQuanArr[dir];
	var sprite = this.originArr[dir];
	spriteQuan.x = sprite.x;
	spriteQuan.y = sprite.y;
	spriteQuan.visible = true;
	sprite.visible = false;
};
DirSprite.prototype.setDirXY = function() {
	var sprite;

	sprite = this.spriteDirArr[0];
	sprite.x = this.r2 - 0.5 * this.dirWidth;
	sprite.y = 2 * this.r1 + this.dirWidth;

	sprite = this.spriteDirArr[1];
	sprite.x = 2 * this.r1 + this.dirWidth;
	sprite.y = this.r2 - 0.5 * this.dirWidth;

	sprite = this.spriteDirArr[2];
	sprite.x = this.r2 - 0.5 * this.dirWidth;
	sprite.y = 0;

	sprite = this.spriteDirArr[3];
	sprite.x = 0;
	sprite.y = this.r2 - 0.5 * this.dirWidth;
}
function ArrowSprite() {
	this.width;
	this.arrowWidth;
	this.spriteArrow;
	this.dir = 0;
}
ArrowSprite.prototype = util.inherit(GroupSprite.prototype);
ArrowSprite.prototype.constructor = ArrowSprite;

ArrowSprite.prototype.layout = function(wh){
	
	this.height = wh.h;
	this.width = wh.w;
	
	
	this.spriteArrow.width = this.width;
	this.spriteArrow.height = this.height;
	this.setArrowDir(this.dir);
}
ArrowSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.spriteArrow = game.add.image(0, 0, 'middle');
	this.spriteArrow.frameName = 'z_arrow.png';
	this.group.add(this.spriteArrow);

}
ArrowSprite.prototype.setArrowDir = function(dir) {
	this.dir = dir;
	if (dir == 3) {
		this.spriteArrow.angle = 0;
		this.spriteArrow.x =  0 ;
		this.spriteArrow.y =  0;
	} else if (dir == 0) {
		this.spriteArrow.angle = -90;
		this.spriteArrow.x =  0 ;
		this.spriteArrow.y =  this.height;
	} else if (dir == 1) {
		this.spriteArrow.angle = -180;
		this.spriteArrow.x = 2* this.width ;
		this.spriteArrow.y =  this.height;
	} else if (dir == 2) {
		this.spriteArrow.angle = -270;
		this.spriteArrow.x = 2* this.width ;
		this.spriteArrow.y =  0;
	}
}
function TimeSprite() {
	this.width;
	this.textSprite;
	this.text = 10;
	
	this.quieted = true;
	
	this.timeEvent;
}
TimeSprite.prototype = util.inherit(GroupSprite.prototype);
TimeSprite.prototype.constructor = TimeSprite;
TimeSprite.prototype.layout = function(wh){
	
	this.width = wh.w;
	this.textSprite.layout({w:this.width,h:this.width,fh:this.width});
	this.textSprite.setText(this.text);

};
TimeSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.textSprite = new LineTextSprite(this.width, this.width,this.width,'#ffff00');
	this.textSprite.create();
	this.textSprite.setText(this.text);
	this.group.add(this.textSprite.group);


	this.timeEvent = game.time.events.loop(Phaser.Timer.SECOND, function() {
		this.text = (this.text == 0?10:this.text-1);
		if (this.text != 10) {
			this.textSprite.setText('0' + this.text);
		}else{
			this.textSprite.setText('10');
		}
		if(this.quieted){
			return ;
		}
		if(this.text <=2){
			if(game.audioManager){
				game.audioManager.playByKey('tick');
			}
		}
	}, this);
}
TimeSprite.prototype.resetTime = function() {
	this.text = 10;
};
TimeSprite.prototype.quiet = function(quiet){
	this.quieted = quiet;
};
TimeSprite.prototype.destroy = function(){
	GroupSprite.prototype.destroy.apply(this, arguments);
	game.time.events.remove(this.timeEvent);
}

function MiddleSprite() {
	this.width;

	this.circlSprite;
	this.arrowSprite;
	this.dirSpirit;
	this.timeSprite;
}
MiddleSprite.prototype = util.inherit(GroupSprite.prototype);
MiddleSprite.prototype.constructor = MiddleSprite;

MiddleSprite.prototype.layout = function(wh){
	this.width = wh.w;
	var middleBased = this.width;
	var based = middleBased / 82 * (82 - 15);
	var d = based;
	var r1 = 39 / 66.5 * based / 2; // 内半径
	var r2 = 58 / 66.5 * based / 2;// 外半径

	this.circlSprite.width = middleBased;
	this.circlSprite.height = middleBased;

	
	this.dirSprite.layout({r1:r1,r2:r2});
	this.dirSprite.group.x = 0.5 * (middleBased - based) + 0.5
			* this.dirSprite.group.getChildAt(0).width;
	this.dirSprite.group.y = 0.5 * (middleBased - based) + 0.5
			* this.dirSprite.group.getChildAt(0).width;

	this.arrowSprite.layout({w:this.width/2,h:this.width});
//	this.arrowSprite.group.x = 0.5 * (middleBased - based);
//	this.arrowSprite.group.y = 0.5 * (middleBased - based);

	this.timeSprite.layout({w:r1});
	this.timeSprite.group.x = 0.5 * (middleBased - r1);
	this.timeSprite.group.y = 0.5 * middleBased - 0.4*r1;
};
MiddleSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.circlSprite = game.add.image(0, 0, 'middle');
	this.circlSprite.frameName = 'middle.png';
	this.group.add(this.circlSprite);

	

	this.arrowSprite = new ArrowSprite();
	this.arrowSprite.create();
	this.group.add(this.arrowSprite.group);
	
	this.dirSprite = new DirSprite();
	this.dirSprite.create();
	this.group.add(this.dirSprite.group);

	this.timeSprite = new TimeSprite();
	this.timeSprite.create();
	this.group.add(this.timeSprite.group);

}

MiddleSprite.prototype.setArrow = function(dir) {
	this.arrowSprite.setArrowDir(dir);
}
MiddleSprite.prototype.setEast = function(dir) {
	this.dirSprite.setEast(dir);
}
MiddleSprite.prototype.resetTime = function() {
	this.timeSprite.resetTime();
}
MiddleSprite.prototype.quiet = function(quiet){
	this.timeSprite.quiet(quiet);
};
MiddleSprite.prototype.setQuan = function(dir){
	this.dirSprite.setQuan(dir);	
};
MiddleSprite.prototype.destroy = function(){
	GroupSprite.prototype.destroy.apply(this, arguments);
	this.timeSprite.destroy();
};
