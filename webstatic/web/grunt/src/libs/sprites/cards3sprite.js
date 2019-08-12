function Cards3Sprite(innerWidth, middleWidth, outterWidth) {
	this.innerWidth = innerWidth;
	this.middleWidth = middleWidth;
	this.outterWidth = outterWidth;
};

Cards3Sprite.prototype = util.inherit(CardsSprite.prototype);
Cards3Sprite.prototype.constructor = Cards3Sprite;

Cards3Sprite.prototype.clear = function(){
	CardsSprite.prototype.clear.apply(this, arguments);
//	this.ajustInnerGroup();
	this.innerGroup.y = 0;
}

Cards3Sprite.prototype.getDir = function() {
	return 3;
}

Cards3Sprite.prototype.addInnerCardSingle = function(cn) {
	cs = new CardSprite(this.getDir(), 0, cn,0);
	cs.create();
	cs.layout({w:this.innerWidth});
	this.innerArr.push(cs);
	this.innerGroup.add(cs.group);
	this.ajustInnerCard();
	this.ajustMiddleGroup();
};


Cards3Sprite.prototype.seperateInnerCard = function(){
	var group = this.innerArr[this.innerArr.length - 1].group;
	group.y += this.innerWidth * 1;
};

Cards3Sprite.prototype.addMiddleXianGangCard = function(cn) {
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
Cards3Sprite.prototype.ajustInnerCard = function() {
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		card.group.y = i * card.ch;
	}
}
Cards3Sprite.prototype.ajustMiddleCard = function() {
	var cmcs, len = this.middleArr.length;
	for (var i = len - 1; i >= 0; i--) {
		cmcs = this.middleArr[i];
		cmcs.group.y = (len - i - 1) * cmcs.ch;
		this.middleGroup.bringToTop(cmcs.group);
	}
	this.ajustInnerGroup();
}
Cards3Sprite.prototype.ajustOutterCard = function(){
	var cs;
	if (this.outterArr && this.outterArr.length > 0) {
		for (var i = 0; i < this.outterArr.length; i++) {
			cs = this.outterArr[i];
			cs.group.x = parseInt((i) / 10) * cs.cw;
			cs.group.y = (i) % 10 * cs.ch;
		}
	}
}
Cards3Sprite.prototype.ajustInnerGroup = function() {
	if(this.middleArr.length == 0){
		return ;
	}
	var cs = this.middleArr[0];
	this.innerGroup.y = (this.middleArr.length) * cs.ch;
	this.group.bringToTop(this.innerGroup);
}
Cards3Sprite.prototype.downInner = function() {
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
	this.group.add(this.innerDownGroup);
}


Cards3Sprite.test = function() {
	var c3s = new Cards3Sprite(0.6 * game.config.otherBoxCw,
			game.config.otherBoxCh, game.config.otherBoxCh);
	c3s.create();
	c3s.outterGroup.x = 100;
	c3s.layout({iw:0.6 * game.config.otherBoxCw,mw:game.config.otherBoxCh,ow:game.config.otherBoxCh});

	c3s.addInnerCard([ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 ]);
	c3s.addInnerCard(13);
	c3s.seperateInnerCard();
	// c3s.removeInnerCard(0);

	for (var i = 0; i < 25; i++) {
		c3s.addOutterCard(i);
	}
	// c1s.removeOutterCard(74);
	//
//	c3s.addMiddlePengCard(1, 0);
//	c3s.removeInnerCard(0);
//	c3s.removeInnerCard(1);
//	c3s.addMiddleChiCard([ 0, 4, 8 ], 0, 8);
//	c3s.removeInnerCard(2);
//	c3s.removeInnerCard(3);
//	c3s.removeInnerCard(4);
//	c3s.addMiddleAnGangCard(0);
//	c3s.removeInnerCard(5);
//	c3s.removeInnerCard(6);
//	c3s.removeInnerCard(7);
//	c3s.removeInnerCard(8);
//	c3s.addMiddleMingGangCard(13, 1);
//	c3s.removeInnerCard(9);
//	c3s.removeInnerCard(10);
//	c3s.removeInnerCard(11);
	// c3s.addMiddleXianGangCard(1);
	//
	c3s.showArrow(true);
	c3s.upInner([ 102, 101, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13 ]);
	// // c3s.showArrow(false);
//	c3s.downInner();
}