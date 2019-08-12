function BackgroundSprite(){
	this.sprite;
}


BackgroundSprite.prototype = util.inherit(GroupSprite.prototype);
BackgroundSprite.prototype.constructor = BackgroundSprite;

BackgroundSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
//	this.sprite = game.add.graphics(0, 0);
//	this.sprite.beginFill(0x005c75);
//	this.sprite.drawRect(0,0,game.world.width,game.world.height);
//	this.sprite.endFill();
	this.sprite = game.add.image(0, 0, 'background_paizhuo');
	this.group.add(this.sprite);
	
}
BackgroundSprite.prototype.layout = function(wh){
	this.sprite.width = game.world.width;
	this.sprite.height = game.world.height;
};
BackgroundSprite.test = function(){
	var  bg = new BackgroundSprite();
	bg.create();
	bg.layout();
	return bg;
}