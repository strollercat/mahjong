function TimerTextSprite(){
	this.textSprite ;
}
TimerTextSprite.prototype = util.inherit(GroupSprite.prototype);
TimerTextSprite.prototype.constructor = TimerTextSprite;

TimerTextSprite.prototype.layout = function(wh){
	this.width = wh.w;
	this.height = wh.h;
	this.textSprite.layout(wh);
	this.textSprite.setText(this.textSprite.getText());
}

TimerTextSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.textSprite = new LineTextSprite(0,0,0,'#ffffff');
	this.textSprite.create();
	this.group.add(this.textSprite.group);
}
TimerTextSprite.prototype.setLeftTime = function(time){
	this.group.visible = true;
	
	this.leftTime = time
	var leftDate = new Date();
	leftDate.setTime(this.leftTime);
	var minute = leftDate.getMinutes();
	if(minute <= 9){
		minute ='0'+minute;
	}
	var second = leftDate.getSeconds();
	if(second <= 9){
		second ='0'+second;
	}
	this.textSprite.setText(minute+':'+second +' 后游戏未开始房间将结束');
	this.textSprite.textSprite.addColor('#ffff00', 0);
	this.textSprite.textSprite.addColor('#ffffff', 6);
	if(this.leftTimer){
		game.time.events.remove(this.leftTimer);
	}
	this.leftTimer = game.time.events.loop(Phaser.Timer.SECOND, function() {
		this.leftTime -= 1000;
		var leftDate = new Date();
		leftDate.setTime(this.leftTime);
		var minute = leftDate.getMinutes();
		if(minute <= 9){
			minute ='0'+minute;
		}
		var second = leftDate.getSeconds();
		if(second <= 9){
			second ='0'+second;
		}
		this.textSprite.setText(minute+':'+second +' 后游戏未开始房间将结束');
	}, this);
}
TimerTextSprite.prototype.clear = function(){
	this.group.visible = false;
	this.textSprite.setText('');
	if(this.leftTimer){
		game.time.events.remove(this.leftTimer);
	}
}

