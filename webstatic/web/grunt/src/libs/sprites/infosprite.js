function InfoSprite(width) {
	this.width = width;

	this.twoLineSprite;
	
	this.leftCard;
	this.leftJu;
	this.totalJu
}

InfoSprite.prototype = util.inherit(GroupSprite.prototype);
InfoSprite.prototype.constructor = InfoSprite;

InfoSprite.prototype.layout = function(wh){
	this.twoLineSprite.layout(wh);
}
InfoSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.twoLineSprite = new TwoLineTextSprite(this.width,'#DCDCDC','#DCDCDC');
	this.twoLineSprite.create();
//	var text = this.twoLineSprite.text1Sprite.textSprite;
//	text.font = 'Arial Black';
//	text.fontWeight = 'bold';
////	text.setShadow(5, 5, 'rgba(0,0,0,0.5)', 10);
//	text.stroke = '#000000';
//	text.strokeThickness = 3;
	this.group.add(this.twoLineSprite.group);

}
InfoSprite.prototype.setLeftCard = function(leftCard) {
	this.twoLineSprite.setText1('剩 ' + leftCard + ' 张');
	this.leftCard = leftCard;
}
InfoSprite.prototype.setLeftJu = function(leftJu) {
	this.leftJu = leftJu;
	if(this.totalJu){
		leftJu = leftJu +'/'+this.totalJu;
	}
	this.twoLineSprite.setText2('第' + leftJu + '局');
	
}
InfoSprite.prototype.setTotalJu = function(totalJu) {
	this.totalJu = totalJu;
}
InfoSprite.test = function() {
	var info = new InfoSprite(game.config.authorBoxCh);
	info.create();
	info.layout({w:game.config.authorBoxCh});
	info.group.x =game.world.width/2;
	info.group.y = game.world.height/2;

	info.setLeftCard('35');
	info.setLeftJu('7');
	
	setTimeout(function(){info.layout({w:200});},2000);
}