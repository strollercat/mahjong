function TintSprite(){
	this.tintText;
	this.graphics;
	this.tween;
};

TintSprite.prototype = util.inherit(GroupSprite.prototype);
TintSprite.prototype.constructor = TintSprite;

TintSprite.prototype.layout = function(wh){

	this.graphics.width = wh.w;
	this.graphics.height = wh.h;
	
	this.tintText.layout({w:wh.w,h:wh.h/2,fh:wh.h/2});
	this.tintText.group.y = wh.h/2/2;
	this.tintText.setText(this.tintText.getText());
	
	
}
TintSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	
	this.graphics = game.add.graphics(0,0);
	this.graphics.beginFill(0x4682B4);
	this.graphics.drawRect(0,0,game.world.width,game.world.height/10);
	this.graphics.endFill();
	this.group.add(this.graphics);
	
	this.tintText = new LineTextSprite(0,0,0,'#FFFFFF');
	this.tintText.create();
	this.group.add(this.tintText.group);
	this.group.alpha = 0;
	
	

};
TintSprite.prototype.showTint = function(tint){
	this.tintText.setText(tint);
	if(this.tween){
		this.tween.stop();
	}
	this.group.alpha = 1;
	this.tween = game.add.tween(this.group);
	this.tween.to({alpha:0}, 5000, Phaser.Easing.Linear.None, false,
			0, 0, false);
	this.tween.start();
};
TintSprite.test = function(){
	var ts = new TintSprite();
	ts.create();
	ts.layout({w:game.world.width,h:game.world.height/10});
	ts.showTint('流浪猫请注意,您已被流浪猫吃了三下');
//	ts.showTint('zhengbinhui请注意,您已被流浪猫吃了三下');
	setTimeout(function(){
		ts.showTint('zhengbinhui请注意,您已被流浪猫吃了三下');
	},4000);
	
}