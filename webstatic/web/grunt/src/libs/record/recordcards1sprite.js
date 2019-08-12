function RecordCards1Sprite(){
}

RecordCards1Sprite.prototype = util.inherit(Cards1Sprite.prototype);
RecordCards1Sprite.prototype.constructor = RecordCards1Sprite;

RecordCards1Sprite.prototype.addInnerCardSingle = function(cn) {
	cs = new CardSprite(this.getDir(), 1, cn, this.middleWidth);
	cs.create();
	cs.layout({w:this.middleWidth});
	if (cs.baida) {
		cs.mask("0xFFFF00");
	}
	this.innerArr.push(cs);
	this.innerGroup.add(cs.group);
	this.ajustInnerCard();
	this.ajustMiddleGroup();
};
RecordCards1Sprite.prototype.ajustInnerCard = function() {
	var card, i;
	var len = this.innerArr.length;
	for (i = 0; i < len; i++) {
		card = this.innerArr[i];
		card.group.y = (len -1 - i) * card.ch;
	}
	for(i = len -1 ;i >=0 ;i--){
		this.innerGroup.bringToTop(this.innerArr[i].group);
	}
	this.ajustMiddleGroup();
}
RecordCards1Sprite.prototype.seperateInnerCard = function(){
	var group = this.innerArr[this.innerArr.length - 1].group;
	group.y -= this.innerWidth/3 ;
};
RecordCards1Sprite.prototype.sortInnerCard = function(){
	this.innerArr.sort(CardSprite.compare);
	this.ajustInnerCard();
};
RecordCards1Sprite.prototype.removeInnerCard = function(cn) {
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