function CardsSprite() {
	this.sprites = [];
	this.ratio = 1;
}

CardsSprite.prototype = util.inherit(GroupSprite.prototype);
CardsSprite.prototype.constructor = CardsSprite;

CardsSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
}
CardsSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	this.height = wh.h;

	for (var i = 0; i < this.sprites.length; i++) {
		this.sprites[i].layout(wh);
		this.sprites[i].group.x = i * this.ratio * this.width;
		this.group.bringToTop(this.sprites[i].group);
	}
}
CardsSprite.prototype.clear = function() {
	for (var i = 0; i < this.sprites.length; i++) {
		this.group.remove(this.sprites[i].group);
		this.sprites[i].group.destroy();
	}
	this.sprites.length = 0;
}
CardsSprite.prototype.getTotalWidth = function(){
	if(this.sprites.length == 0){
		return 0;
	}
	return (this.sprites.length -1 )*this.ratio * this.width + this.width;
}
CardsSprite.prototype.setRatio = function(ratio) {
	this.ratio = ratio;
	this.layout({
		w : this.width,
		h : this.height
	});
}
CardsSprite.prototype.sort = function() {
	this.sprites.sort(PokerCardSprite.compare);
	this.layout({
		w : this.width,
		h : this.height
	});
}
CardsSprite.prototype.addCard = function(cn) {
	if (cn instanceof Array) {
		for (var i = 0; i < cn.length; i++) {
			var sprite = new CardSprite(cn[i]);
			sprite.create();
			this.sprites.push(sprite);
			this.group.add(sprite.group);
		}
	} else {
		var sprite = new PokerCardSprite(cn);
		sprite.create();
		this.sprites.push(sprite);
		this.group.add(sprite.group);
	}
	this.layout({
		w : this.width,
		h : this.height
	});
};

CardsSprite.prototype.removeCardByCn = function(cn) {
	for (var i = 0; i < this.sprites.length; i++) {
		if (this.sprites[i].cn == cn) {
			this.group.remove(this.sprites[i].group);
			this.sprites.splice(i, 1);
			break;
		}
	}
	this.layout({
		w : this.width,
		h : this.height
	});
};
CardsSprite.prototype.removeCardByIndex = function(index) {

	this.group.remove(this.sprites[index].group);
	this.sprites.splice(index, 1);
	this.layout({
		w : this.width,
		h : this.height
	});
};

CardsSprite.prototype.getCardsCns = function() {
	var cns = [];
	for (var i = 0; i < this.sprites.length; i++) {
		cns.push(this.sprites[i].cn);
	}
	return cns;
};

CardsSprite.test = function() {

	var cs = new CardsSprite();
	cs.create();
	cs.layout({
		w : 80,
		h : 120
	});

	cs.addCard(3);
	cs.addCard(2);
	cs.addCard(4);
	cs.addCard(5);
	// cs.removeCard(4);
	// cs.sort();
	// cs.setRatio(0.5);

	// cs.removeCardByIndex(2);

	// cs.clear();
}
