function CardSprite(cn) {
	this.cn = cn;
	this.sprite;
}

CardSprite.prototype = util.inherit(GroupSprite.prototype);
CardSprite.prototype.constructor = CardSprite;

CardSprite.prototype.create = function() {

	GroupSprite.prototype.create.apply(this, arguments);
	if(this.cn >=0 ){
		this.sprite = game.add.image(0, 0, 'pokercards');
		this.sprite.frameName = CardSprite.getCardKey(this.cn);
	}else{
		this.sprite = game.add.image(0, 0, 'thirteen');
		this.sprite.frameName = 'thirteen_poker_backview';
	}
	this.group.add(this.sprite);

}
CardSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	this.height = wh.h;
	this.sprite.width = this.width;
	this.sprite.height = this.height;
}

CardSprite.getCardKey = function(cn) {
	if (cn >= 0 && cn <= 12) {
		cn += 49;
		return 'thirteen_' + cn;
	}
	if (cn >= 13 && cn <= 25) {
		cn = cn + 20;
		return 'thirteen_' + cn;
	}
	if (cn >= 26 && cn <= 38) {
		cn -= 9;
		return 'thirteen_' + cn;
	}
	if (cn >= 39 && cn <= 51) {
		cn -= 38;
		return 'thirteen_' + cn;
	}
	if (cn == 52 || cn == 53) {
		cn += 26;
		return 'thirteen_' + cn;
	}
	if (cn > 53) {
		cn = cn % 54;
		return CardSprite.getCardKey(cn);
	}
}



CardSprite.compare = function(c1, c2) {
	c1 = c1.cn % 54;
	c2 = c2.cn % 54;
	var n1 = c1 % 13;
	var n2 = c2 % 13;
	if (n1 < n2) {
		return -1;
	} else if (n1 == n2) {
		if (c1 == c2) {
			return 0;
		} else if (c1 < c2) {
			return -1;
		} else {
			return 1;
		}
	} else {
		return 1;
	}
}

CardSprite.test = function() {

	 var sprite = new CardSprite(-1);
	 sprite.create();
	 sprite.layout({w:80,h:120});
}
