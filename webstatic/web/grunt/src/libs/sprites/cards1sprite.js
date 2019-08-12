function Cards1Sprite(innerWidth, middleWidth, outterWidth) {
	this.innerWidth = innerWidth;
	this.middleWidth = middleWidth;
	this.outterWidth = outterWidth;
};

Cards1Sprite.prototype = util.inherit(CardsSprite.prototype);
Cards1Sprite.prototype.constructor = Cards1Sprite;







Cards1Sprite.prototype.addInnerCardSingle = function(cn) {
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
Cards1Sprite.prototype.seperateInnerCard = function(){
	var group = this.innerArr[0].group;
	group.y -= this.innerWidth ;
};


Cards1Sprite.prototype.addMiddleXianGangCard = function(cn) {
	var i, cmcs;
	if (!this.middleArr || this.middleArr.length == 0) {
		return;
	}
	for (i = 0; i < this.middleArr.length; i++) {
		cmcs = this.middleArr[i];
		if (cmcs.kind == 'peng' && CardSprite.sameKind(cmcs.cns[0], cn)) {
			this.middleGroup.remove(cmcs.group);
			cmcs.group.destroy();
			cmcs = new CardsMiddleSprite(this.getDir(), 'minggang', [ cn, cn,
					cn, cn ], this.middleWidth, cmcs.who, cmcs.cn);
			cmcs.create();
			cmcs.layout({w:this.middleWidth});
			this.middleArr[i] = cmcs;
			this.middleGroup.add(cmcs.group);
		}
	}
	this.ajustMiddleCard();
}

Cards1Sprite.prototype.getDir = function() {
	return 1;
}
Cards1Sprite.prototype.ajustOutterCard = function(){
	var i,cs;
	for(i = 0 ;i<this.outterArr.length;i++){
		cs = this.outterArr[i];
		cs.group.x = (2 - parseInt((i) / 10)) * cs.cw;
		cs.group.y = (10 - (i) % 10) * cs.ch;
	}
	var len = this.outterArr.length;
	for (var i = len - 1; i >= 0; i--) {
		this.outterGroup.bringToTop(this.outterArr[i].group);
	}
};
Cards1Sprite.prototype.ajustInnerCard = function() {
	// console.log(this.innerArr.length);
	var card, i;
	for (i = 0; i < this.innerArr.length; i++) {
		// console.log(i);
		card = this.innerArr[i];
		card.group.y = i * card.ch;
		// console.log(i+" "+card.group.y);
		// this.group.bringToTop(card.group);
	}
	this.ajustMiddleGroup();
}
Cards1Sprite.prototype.ajustMiddleCard = function() {
	var cmcs, len = this.middleArr.length;
	for (var i = len - 1; i >= 0; i--) {
		cmcs = this.middleArr[i];
		cmcs.group.y = (len - i - 1) * cmcs.ch;
		this.middleGroup.bringToTop(cmcs.group);
	}
}
Cards1Sprite.prototype.ajustMiddleGroup = function() {
	if(this.innerArr.length == 0){
		return ;
	}
	var cs = this.innerArr[0];
	this.middleGroup.y = (this.innerArr.length + 1) * cs.ch;
}
Cards1Sprite.prototype.downInner = function() {
	this.innerGroup.visible = false;
	this.innerDownGroup.visible = true;
	this.innerDownGroup.x = this.middleGroup.x;
	this.innerDownGroup.y = this.innerGroup.y;
	var len = this.innerArr.length, i, cs;
	for (i = 0; i < len; i++) {
		cs = new CardSprite(this.getDir(), 2, -1, this.middleWidth);
		cs.create();
		cs.layout({w:this.middleWidth});
		cs.group.x = this.innerArr[i].group.x;
		cs.group.y = i * cs.ch;
		this.innerDownGroup.add(cs.group);
		this.innerDownArr.push(cs);
	}
//	this.middleGroup.y = (len + 1) * cs.ch;

	this.group.add(this.innerDownGroup);
}

Cards1Sprite.test = function() {
//	var authorBoxCw = game.world.width/17;
//	var otherBoxCw = authorBoxCw / 5 * 3;
//	var otherBoxCh = 1.
	
	var c1s = new Cards1Sprite(0.6 * game.config.otherBoxCw,
			game.config.otherBoxCh, game.config.otherBoxCh);
	c1s.create();
	c1s.layout({iw:0.6 * game.config.otherBoxCw,mw:game.config.otherBoxCh,ow:game.config.otherBoxCh});
	c1s.outterGroup.y = 2 * c1s.outterWidth;
	c1s.innerGroup.x = 3 * c1s.outterWidth;
	c1s.middleGroup.x = 3 * c1s.outterWidth;

	// c1s.addInnerCard([ -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1 ]);
	c1s.addInnerCard([ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1 ]);
//	c1s.addInnerCard(-1);
//	c1s.removeInnerCard(0);

	for (var i = 0; i < 25; i++)
		c1s.addOutterCard(i);

	c1s.addMiddlePengCard(1, 0);
//	c1s.removeInnerCard(1);
//	c1s.removeInnerCard(2);
	c1s.addMiddleChiCard([ 0, 4, 8 ], 0, 8);
//	c1s.removeInnerCard(1);
//	c1s.removeInnerCard(2);
//	c1s.removeInnerCard(3);
//	c1s.addMiddleAnGangCard(0);
//	c1s.removeInnerCard(1);
//	c1s.removeInnerCard(2);
//	c1s.removeInnerCard(3);
//	c1s.removeInnerCard(4);
	// c1s.addMiddleMingGangCard(13, 1);
	c1s.addMiddleXianGangCard(1);
	//
	c1s.showArrow(true);
	
	c1s.upInner([ 102, 101, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13 ]);
	// // c1s.showArrow(false);
//	c1s.downInner();
}