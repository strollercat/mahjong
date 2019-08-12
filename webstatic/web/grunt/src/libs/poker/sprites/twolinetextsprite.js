function TwoLineTextSprite(width,color1,color2) {
	this.width = width;
	this.color1 = color1;
	this.color2 = color2;

	this.text1Sprite;
	this.text2Sprite;
	
	this.box1 ;
	this.box2 ;

	this.cw = width;
	this.ch;

}
TwoLineTextSprite.prototype = util.inherit(GroupSprite.prototype);
TwoLineTextSprite.prototype.constructor = TwoLineTextSprite;

TwoLineTextSprite.prototype.layout = function(wh){
	this.width = wh.w;
	this.cw = this.width;
	var height = this.width / 125 * 30;
	this.ch = 2 * height;
	var offx = 12 / 125 * this.width;
	var offy = 3 / 30 * height;
	var textWidth = (112 - 12) / 125 * this.width;
	var textHeight = (30 - 3) / 30 * height;

	sprite = this.box1;
	sprite.width = this.width;
	sprite.height = height;

	this.text1Sprite.layout({w:textWidth,h:textHeight,fh:textHeight});
	this.text1Sprite.group.x = offx;
	this.text1Sprite.group.y = offy;
	this.text1Sprite.setText(this.text1Sprite.getText());

	sprite = this.box2;
	sprite.width = this.width;
	sprite.height = height;
	sprite.y = height;

	this.text2Sprite.layout({w:textWidth,h:textHeight,fh:textHeight});
	this.text2Sprite.group.x = offx;
	this.text2Sprite.group.y = offy + sprite.height;
	this.text2Sprite.setText(this.text2Sprite.getText());
}
TwoLineTextSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	var sprite;

	sprite = game.add.image(0, 0, 'other');
	sprite.frameName = 'box' ;
	this.group.add(sprite);
	this.box1 = sprite;

	this.text1Sprite = new LineTextSprite(0,0,0,this.color1);
	this.text1Sprite.create();
	this.group.add(this.text1Sprite.group);

	sprite = game.add.image(0, 0, 'other');
	sprite.frameName = 'box' ;
	this.group.add(sprite);
	this.box2 = sprite;

	this.text2Sprite = new LineTextSprite(0,0,0,this.color2);
	this.text2Sprite.create();
	this.group.add(this.text2Sprite.group);
}

TwoLineTextSprite.prototype.setText1 = function(text) {
	this.text1Sprite.setText(text);
}
TwoLineTextSprite.prototype.setText2 = function(text) {
	this.text2Sprite.setText(text);
}
TwoLineTextSprite.prototype.getText1 = function() {
	return this.text1Sprite.getText();
}
TwoLineTextSprite.prototype.getText2 = function() {
	return this.text2Sprite.getText();
}
TwoLineTextSprite.test = function() {
	var tt = new TwoLineTextSprite(100,'#ffffff','#ffff00');
	tt.create();
	tt.layout({w:100});
	
//	tt.group.x = game.world.width / 2;
//	tt.group.y = game.world.height / 2;

	tt.setText1("-5");
	tt.setText2("+0");
	console.log(tt.getText1() + " " + tt.getText2());
	
	
	setTimeout(function(){tt.layout({w:80})},2000);
	
}
//function TwoLineTextSprite1(color1,color2){
//	this.color1 = color1;
//	this.color2 = color2;
//}
//TwoLineTextSprite1.prototype = util.inherit(TwoLineTextSprite.prototype);
//TwoLineTextSprite1.prototype.constructor = TwoLineTextSprite1;
//
//
//TwoLineTextSprite1.prototype.layout = function(wh){
//	this.width = wh.w;
//	this.cw = this.width;
//	var height = this.width / 125 * 30;
//	this.ch = 2 * height;
//	var offx = 12 / 125 * this.width;
//	var offy = 3 / 30 * height;
//	var textWidth = (112 - 12) / 125 * this.width;
//	var textHeight = (30 - 3) / 30 * height;
//
//	sprite = this.box1;
//	sprite.width = this.width;
//	sprite.height = height;
//
//	this.text1Sprite.layout({w:textWidth,h:textHeight,fh:textHeight});
//	this.text1Sprite.group.x = offx;
//	this.text1Sprite.group.y = offy;
//	this.text1Sprite.setText(this.text1Sprite.getText());
//
//	sprite = this.box2;
//	sprite.width = this.width;
//	sprite.height = height;
//	sprite.y = height;
//
//	this.text2Sprite.layout({w:textWidth,h:textHeight,fh:textHeight});
//	this.text2Sprite.group.x = offx;
//	this.text2Sprite.group.y = offy + sprite.height;
//	this.text2Sprite.setText(this.text2Sprite.getText());
//}