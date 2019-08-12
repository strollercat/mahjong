function GroupSprite(){
	
};
GroupSprite.prototype.create = function(){
	this.group = game.add.group();
};
GroupSprite.prototype.layout = function(wh){
};
GroupSprite.prototype.clear = function(){
};
GroupSprite.prototype.destroy = function(){
	if(this.group){
		this.group.destroy();
		this.group = null;
	}
};
GroupSprite.prototype.getTotalWidth = function(){
	return this.width;
};
GroupSprite.prototype.getTotalHeight = function(){
	return this.height;
};
