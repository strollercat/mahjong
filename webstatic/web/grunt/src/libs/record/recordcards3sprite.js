function RecordCards3Sprite(){
}

RecordCards3Sprite.prototype = util.inherit(Cards3Sprite.prototype);
RecordCards3Sprite.prototype.constructor = RecordCards3Sprite;

RecordCards3Sprite.prototype.addInnerCardSingle = function(cn) {
	cs = new CardSprite(this.getDir(), 1, cn,0);
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
RecordCards3Sprite.prototype.sortInnerCard = function(){
	this.innerArr.sort(CardSprite.compare);
	this.ajustInnerCard();
};
RecordCards3Sprite.prototype.removeInnerCard = function(cn) {
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
RecordCards3Sprite.prototype.ajustInnerCard = function() {
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		card.group.y = i * card.ch;
	}
	for (var i = 0; i < this.innerArr.length; i++) {
		this.innerGroup.bringToTop(this.innerArr[i].group);
	}
}
RecordCards3Sprite.prototype.ajustInnerGroup = function() {
	if(this.middleArr.length == 0){
		return ;
	}
	var cs = this.middleArr[0];
	this.innerGroup.y = (this.middleArr.length) * cs.ch;
	
	cs = this.innerArr[0];
	this.innerGroup.y += 0.3 *cs.ch;
	
	this.group.bringToTop(this.innerGroup);
}
RecordCards3Sprite.prototype.seperateInnerCard = function(){
	var group = this.innerArr[this.innerArr.length - 1].group;
	group.y += this.innerWidth/4 ;
};