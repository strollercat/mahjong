function Cards2Sprite(innerWidth, middleWidth, outterWidth) {
	this.innerWidth = innerWidth;
	this.middleWidth = middleWidth;
	this.outterWidth = outterWidth;
};

Cards2Sprite.prototype = util.inherit(CardsSprite.prototype);
Cards2Sprite.prototype.constructor = Cards2Sprite;






Cards2Sprite.prototype.addInnerCardSingle = function(cn) {
	cs = new CardSprite(this.getDir(), 0, cn, this.innerWidth);
	cs.create();
	cs.layout({w:this.innerWidth});
	this.innerArr.push(cs);
	this.innerGroup.add(cs.group);
	this.ajustInnerCard();
	this.ajustMiddleGroup();
};

/**
 *  如果14张，最后一张留点边
 */
Cards2Sprite.prototype.seperateInnerCard = function(){
	var group = this.innerArr[0].group;
	group.x -= this.innerWidth * 0.2;
};



Cards2Sprite.prototype.ajustInnerCard = function() {
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		card.group.x = i * card.cw;
	}
	this.ajustMiddleGroup();
}
Cards2Sprite.prototype.ajustMiddleCard = function() {
	var cmcs;
	if (this.middleArr && this.middleArr.length > 0) {
		for (var i = 0; i < this.middleArr.length; i++) {
			cmcs = this.middleArr[i];
			cmcs.group.x = i * cmcs.cw;
		}
	}
}
Cards2Sprite.prototype.ajustOutterCard = function() {
	var cs;
	if (this.outterArr && this.outterArr.length > 0) {
		for (var i = 0; i < this.outterArr.length; i++) {
			cs = this.outterArr[i];
			cs.group.x = (9-(i) % 10) * cs.cw;
			cs.group.y = parseInt((i) / 10) * cs.ch;
		}
	}
}
Cards2Sprite.prototype.getDir = function() {
	return 2;
}
Cards2Sprite.prototype.ajustMiddleGroup = function() {
	if(this.innerArr.length == 0 ){
		return ;
	}
	var cs = this.innerArr[0];
	this.middleGroup.x = this.innerArr.length * cs.cw + 0.2 * this.innerWidth;
}


Cards2Sprite.test = function() {
	var c2s = new Cards2Sprite(game.config.otherBoxCw, game.config.otherBoxCw,
			game.config.otherBoxCw);
	c2s.create();
	c2s.layout({iw:game.config.otherBoxCw,mw:game.config.otherBoxCw,ow:game.config.otherBoxCw});
	c2s.outterGroup.y = CardSprite.getHeightByWidth(2,c2s.innerWidth);
	c2s.addInnerCard([ 0, 1, 2, 3 ]);
	c2s.addInnerCard(13);
//	c2s.removeInnerCard(0);

	for (var i = 0; i < 25; i++) {
		c2s.addOutterCard(i + 50);
	}
	c2s.removeOutterCard(74);

	c2s.addMiddlePengCard(0, 1);
	c2s.addMiddleChiCard([ 0, 4, 8 ], 1, 8);
	c2s.addMiddleAnGangCard(0);
	// c0s.addMiddleMingGangCard(13, 1);
	c2s.addMiddleXianGangCard(1);
	
	
	c2s.showArrow(true);
//	c2s.showArrow(false);
	
//	c2s.downInner();
	c2s.upInner([ 0, 1, 2, 3, 4 ]);
	
}