function RecordCards2Sprite(){
}

RecordCards2Sprite.prototype = util.inherit(Cards2Sprite.prototype);
RecordCards2Sprite.prototype.constructor = RecordCards2Sprite;

RecordCards2Sprite.prototype.addInnerCardSingle = function(cn) {
	cs = new CardSprite(this.getDir(), 1, cn, this.innerWidth);
	cs.create();
	cs.layout({w:this.innerWidth});
	if (cs.baida) {
		cs.mask("0xFFFF00");
	}
	this.innerArr.push(cs);
	this.innerGroup.add(cs.group);
	this.ajustInnerCard();
	this.ajustMiddleGroup();
};
RecordCards2Sprite.prototype.ajustInnerCard = function() {
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		card.group.x = (this.innerArr.length -1 -i) * card.cw;
	}
	this.ajustMiddleGroup();
};
RecordCards2Sprite.prototype.seperateInnerCard = function(){
	var group = this.innerArr[this.innerArr.length -1 ].group;
	group.x -= this.innerWidth * 0.2;
};
RecordCards2Sprite.prototype.sortInnerCard = function(){
	this.innerArr.sort(CardSprite.compare);
	this.ajustInnerCard();
};
RecordCards2Sprite.prototype.removeInnerCard = function(cn) {
	if (this.innerArr.length == 0) {
		return;
	}
	var innerArr1 = [];
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		if (card.cn == cn) {
			this.innerGroup.remove(card.group);
			card.group.destroy();
		} else {
			innerArr1.push(card);
		}
	}
	this.innerArr.length = 0;
	this.innerArr = innerArr1;
	this.ajustInnerCard();
};