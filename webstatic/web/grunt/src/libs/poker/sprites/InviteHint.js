function InviteHintSprite(){
	
}
InviteHintSprite.prototype = util.inherit(GroupSprite.prototype);
InviteHintSprite.prototype.constructor = InviteHintSprite;


InviteHintSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.inviteSprite = game.add.image(0,0,'other');
	this.inviteSprite.frameName = 'inviteHint' ;
	this.group.add(this.inviteSprite);
	
	
	
}

InviteHintSprite.prototype.layout = function(wh){ 
	this.width = wh.w;
	this.height = wh.h;
	
	this.inviteSprite.width = this.width;
	this.inviteSprite.height = this.height;
	
	if(this.tween){
		return ;
	}
	
	var property = {
		x : 0.2 * this.inviteSprite.width,
		y : 0
	};
	this.tween = game.add.tween(this.inviteSprite);
	this.tween.to(property, 500, Phaser.Easing.Linear.None).yoyo(true)
			.loop().start();
}

InviteHintSprite.test = function(){
	var invite = new InviteHintSprite();
	invite.create();
	invite.layout({w:20,h:20});
}
