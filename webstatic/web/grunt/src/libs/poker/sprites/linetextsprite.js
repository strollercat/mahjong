function LineTextSprite(width,height,fontHeight,styleOrColor){
	this.width = width;
	this.height = height;
	this.fontWidth = fontHeight *0.6;
	this.fontHeight = fontHeight;
	this.styleOrColor = styleOrColor;
	this.textSprite;
};


LineTextSprite.prototype = util.inherit(GroupSprite.prototype);
LineTextSprite.prototype.constructor = LineTextSprite;

LineTextSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	var style;
	if(typeof(this.styleOrColor) == 'string'){
		style = {
			font : "30px 'Arial'",
			fontWeight :'bold',
			fill : this.styleOrColor,
			boundsAlignH : "center",
			boundsAlignV : "middle"
		};
	}else{
		style = this.styleOrColor;
	}
	this.textSprite = game.add.text(0, 0, '', style);
	this.group.add(this.textSprite);
}
LineTextSprite.prototype.layout = function(wh){
	this.width = wh.w;
	this.height = wh.h;
	this.fontHeight = wh.fh;
	this.fontWidth = this.fontHeight *0.6;
};
LineTextSprite.prototype.setTextToLeft = function(text) {
	if(text !== 0  && !text){
		this.textSprite.setText('');
		return ;
	}
	text =  text + '';
	this.textSprite.scale.x = 1;
	this.textSprite.scale.y = 1;
	this.textSprite.setText(text);
	var textWidth = this.textSprite.width;
	var textHeight = this.textSprite.height;
	this.textSprite.scale.x = text.length * this.fontWidth / textWidth;
	this.textSprite.scale.y = 1 * this.fontHeight / textHeight;
	this.textSprite.y = this.height/2 - this.textSprite.height/2;
	return this.textSprite.width;
}


LineTextSprite.prototype.setText = function(text) {
	if(text !== 0  && !text){
		this.textSprite.setText('');
		return ;
	}
	text =  text + '';
	this.textSprite.scale.x = 1;
	this.textSprite.scale.y = 1;
	this.textSprite.setText(text);
	var textWidth = this.textSprite.width;
	var textHeight = this.textSprite.height;
	this.textSprite.scale.x = text.length * this.fontWidth / textWidth;
	this.textSprite.scale.y = 1 * this.fontHeight / textHeight;
	this.textSprite.x = this.width /2 - this.textSprite.width/2;
	this.textSprite.y = this.height/2 - this.textSprite.height/2;
	return this.textSprite.width;
}
LineTextSprite.prototype.getText = function() {
	return this.textSprite.text;
};
LineTextSprite.test = function(){
	var lts = new LineTextSprite(0,0,0,'#ff0000');
	lts.create();
	
	lts.layout({w:100,h:20,fh:20});
	lts.setTextToLeft('尼玛比');
	
	setTimeout(function(){
		lts.layout({w:200,h:40,fh:40});
		lts.setTextToLeft('尼玛比');
	},2000);
}