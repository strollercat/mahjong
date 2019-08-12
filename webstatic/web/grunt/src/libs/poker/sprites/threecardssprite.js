function ThreeCardsSprite() {
	this.cardsArr = [];
	this.blackCardsArr = [];
	this.lpzSprite;
}

ThreeCardsSprite.prototype = util.inherit(GroupSprite.prototype);
ThreeCardsSprite.prototype.constructor = ThreeCardsSprite;

ThreeCardsSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	for (var i = 0; i < 3; i++) {
		var cs = new CardsSprite();
		cs.create();
		cs.setRatio(0.35);
		this.group.add(cs.group);
		this.cardsArr.push(cs);
		cs.group.visible = false;

		cs = new CardsSprite();
		cs.create();
		cs.setRatio(0.35);
		this.group.add(cs.group);
		this.blackCardsArr.push(cs);
	}
	this.blackCardsArr[0].addCard([ -1, -1, -1 ]);
	this.blackCardsArr[1].addCard([ -1, -1, -1, -1, -1 ]);
	this.blackCardsArr[2].addCard([ -1, -1, -1, -1, -1 ]);
	
	this.blackCardsArr[0].group.visible = false;
	this.blackCardsArr[1].group.visible = false;

	this.lpzSprite = game.add.image(0, 0, 'thirteen');
	this.lpzSprite.frameName = 'thirteen_poker_back_text_03';
	this.group.add(this.lpzSprite);

}
ThreeCardsSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	this.height = wh.h;

	for (var i = 0; i < 3; i++) {
		this.cardsArr[i].layout(wh);
		this.cardsArr[i].group.y = 0.4 * i * this.height;

		this.blackCardsArr[i].layout(wh);
		this.blackCardsArr[i].group.y = 0.4 * i * this.height;

	}
	this.cardsArr[0].group.x = this.cardsArr[0].ratio * this.width;
	this.blackCardsArr[0].group.x = this.blackCardsArr[0].ratio * this.width;
	this.lpzSprite.width = this.width;
	this.lpzSprite.height = this.width / 3;
	this.lpzSprite.y = this.blackCardsArr[2].group.y
			+ (this.height - this.lpzSprite.height) / 2;
	this.lpzSprite.x = 0.5 * this.width;

}
ThreeCardsSprite.prototype.clear = function() {
	for (var i = 0; i < 3; i++) {
		this.cardsArr[i].clear();
	}
}
ThreeCardsSprite.prototype.getTotalWidth = function() {
	return this.blackCardsArr[2].getTotalWidth();
}
ThreeCardsSprite.prototype.getTotalHeight = function() {
	return 1.8 * this.height;
}
ThreeCardsSprite.prototype.whiteCards = function(line) {
	this.cardsArr[line].group.visible = true;
	this.blackCardsArr[line].group.visible = false;
	this.group.bringToTop(this.cardsArr[line].group);
	this.lpzSprite.visible = false;
}

ThreeCardsSprite.prototype.addCards = function(cns) {
	this.cardsArr[0].addCard(cns.slice(0, 3));
	this.cardsArr[1].addCard(cns.slice(3, 8));
	this.cardsArr[2].addCard(cns.slice(8, 13));

}

ThreeCardsSprite.test = function() {

	var cs = new ThreeCardsSprite();
	cs.create();
	cs.layout({
		w : 80,
		h : 120
	});
	cs.addCards([ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ]);
	// cs.blackCards(0);
	// cs.blackCards(1);
	// cs.blackCards(2);
	// cs.whiteCards(0);
	// cs.whiteCards(1);
	// cs.whiteCards(2);

	// cs.addCard(3);
	// cs.addCard(2);
	// cs.addCard(4);
	// cs.addCard(5);
	// cs.removeCard(4);
	// cs.sort();
	// cs.setRatio(0.5);

	// cs.removeCardByIndex(2);

	// cs.clear();
}
