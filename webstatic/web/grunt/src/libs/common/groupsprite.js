function GroupSprite(){
	
};
GroupSprite.prototype.group = null;
GroupSprite.prototype.create = function(){
	this.group = game.add.group();
};
GroupSprite.prototype.destroy = function(){
	if(this.group){
		this.group.destroy();
		this.group = null;
	}
};
GroupSprite.prototype.layout = function(wh){
};
GroupSprite.prototype.clear = function(){
};