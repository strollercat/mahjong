/**
 * dir:0,1,2 type:0:inner,1:outter white,2:outter back,3:inner black(when dir=0)
 */
function CardSprite(dir, type, cn, width) {

	this.dir = dir;
	this.type = type;
	this.cn = cn;
	this.width = width;

	this.ch;
	this.cw;
	this.baida;
	
	this.spriteBox;
	this.spriteMj;

}



CardSprite.getCardKey = function(number) {
	if (number <= 143 && number >= 136) {
		return 'card.card' + (number - 102);
	} else {
		return 'card.card' + (parseInt(number / 4));
	}
};
CardSprite.sameKind = function(cn1, cn2) {
	return parseInt(cn1 / 4) == parseInt(cn2 / 4);
};
CardSprite.getHeightByWidth = function(dir, width) {
	if (dir == 0 || dir == 2) {
		return 1.5 * width;
	} else if (dir == 1 || dir == 3) {
		return width / 13 * 10
	}
	return -1;
};
CardSprite.getWidthByHeight = function(dir, height) {
	if (dir == 0 || dir == 2) {
		return height / 1.5;
	} else if (dir == 1 || dir == 3) {
		return height / 10 * 13
	}
	return -1;
};
CardSprite.compare = function(c1, c2) {
	if ((c1.baida && c2.baida) || (!c1.baida && !c2.baida)) {
		if (c1.cn < c2.cn) {
			return -1;
		} else if (c1.cn == c2.cn) {
			return 0;
		} else {
			return 1;
		}
	} else if (c1.baida && !c2.baida) {
		return -1;
	} else if (!c1.baida && c2.baida) {
		return 1;
	}
}
CardSprite.prototype = util.inherit(GroupSprite.prototype);
CardSprite.prototype.constructor = CardSprite;

CardSprite.prototype.calBaida = function() {
	if (this.cn == -1) {
		return false;
	}
	if (!game.config.baidas || game.config.baidas.length == 0) {
		this.baida = false;
		return;
	}

	for (var i = 0; i < game.config.baidas.length; i++) {
		if (this.cn == game.config.baidas[i]) {
			this.baida = true;
			return;
		}
	}
	this.baida = false;
}

CardSprite.prototype.layout = function(wh){
	this.width = wh.w;
	if(this.dir == 0){
		this.layout0();
	}else if(this.dir == 1){
		this.layout1();
	}else if(this.dir ==2){
		this.layout2();
	}else if(this.dir == 3){
		this.layout3();
	}
}


CardSprite.prototype.layout0 = function() {
	var spriteBox = this.spriteBox, spriteMj = this.spriteMj;
	if (this.type == 0) { // dir0 in
		spriteBox.width = this.width;
		spriteBox.height = 1.5 * this.width;

		spriteMj.x = 0.07 * spriteBox.width;
		spriteMj.y = 0.20 * spriteBox.height;
		spriteMj.width = 0.8 * spriteBox.width;
		spriteMj.height = spriteMj.width / 74 * 100;
	} else if (this.type == 1) { // dir0 out white
		spriteBox.width = this.width;
		spriteBox.height = 1.5 * this.width;

		spriteMj.width = 0.8 * spriteBox.width;
		spriteMj.height = spriteMj.width / 74 * 100;
		spriteMj.x = 0.05 * spriteBox.width;
		spriteMj.y = 0.05 * spriteBox.height;
	} else if (this.type == 2) { // dir 0 out black
		spriteBox.width = this.width;
		spriteBox.height = 1.5 * this.width;
	} else if(this.type == 3){
		spriteBox.width = this.width;
		spriteBox.height = 1.5 * this.width;
	}
	this.cw = spriteBox.width * 100 / 105;
	this.ch = spriteBox.height * 0.8;
}

CardSprite.prototype.layout1 = function() {
	var spriteBox = this.spriteBox, spriteMj = this.spriteMj;
	if (this.type == 0) { // dir1 in
		spriteBox.width = this.width;
		spriteBox.height = this.width / 60 * 124;

		this.cw = spriteBox.width;
		this.ch = spriteBox.height / 124 * 68;
	} else if (this.type == 1) { // dir1 out white
		spriteBox.width = this.width;
		spriteBox.height = this.width / 13 * 10;
		
		this.cw = spriteBox.width / 130 * 125;
		this.ch = spriteBox.height * 70 / 105;
		
		spriteMj.width = 0.85*this.ch;
		spriteMj.height = 0.85*this.cw;
		spriteMj.y = 0.9*this.ch ;
		spriteMj.x =0.05*this.cw;
		spriteMj.angle =  -90;

//		spriteMj.width = 7 / 15 * spriteBox.width;
//		spriteMj.height = 1.3 * spriteMj.width;
//		spriteMj.x = 0.1 * spriteBox.width;
//		spriteMj.y = 0.1 * spriteBox.height;
//		spriteMj.angle = -90;
//		spriteMj.y += 0.6 * spriteBox.height;


	} else if (this.type == 2) { // dir 1 out black
		spriteBox.width = this.width;
		spriteBox.height = this.width / 13 * 10;

		this.cw = spriteBox.width / 130 * 125;
		this.ch = spriteBox.height * 70 / 105;
	}
}

CardSprite.prototype.layout2 = function() {
	var spriteBox = this.spriteBox, spriteMj = this.spriteMj;
	if (this.type == 0) { // dir2 in
		spriteBox.width = this.width;
		spriteBox.height = 1.5 * this.width;

	} else if (this.type == 1) { // dir2 out white
		spriteBox.width = this.width;
		spriteBox.height = 1.5 * this.width;

		spriteMj.x = spriteBox.x + 0.07 * spriteBox.width;
		spriteMj.y = spriteBox.y + 0.05 * spriteBox.height;
		spriteMj.width = 0.8 * spriteBox.width;
		spriteMj.height = spriteMj.width / 74 * 100;
		spriteMj.anchor.setTo(1, 1);
		spriteMj.angle = 180;

	} else if (this.type == 2) { // dir2 out black
		spriteBox.width = this.width;
		spriteBox.height = 1.5 * this.width;
	}
	this.cw = spriteBox.width * 100 / 105;
	this.ch = spriteBox.height * 0.8;
}

CardSprite.prototype.layout3 = function() {
	var spriteBox = this.spriteBox, spriteMj = this.spriteMj;
	if (this.type == 0) { // dir3 in
		spriteBox.width = this.width;
		spriteBox.height = this.width / 60 * 124;

		this.cw = spriteBox.width / 130 * 125;
		this.ch = spriteBox.height / 124 * 68;
	} else if (this.type == 1) { // dir3 out white
		spriteBox.width = this.width;
		spriteBox.height = this.width / 13 * 10;
	
		spriteMj.width = 7 / 15 * spriteBox.width;
		spriteMj.height = 1.8 * spriteMj.width;
		spriteMj.x = spriteBox.x + 0.9*this.width;
		spriteMj.y = spriteBox.y ;
		spriteMj.angle = 90;
//		spriteMj.x += 1 * 10 / 15 * this.width;
//		spriteMj.y -=  10 / 15 * this.width;

		this.cw = spriteBox.width / 130 * 125;
		this.ch = spriteBox.height * 70 / 105;

	} else if (this.type == 2) { // dir 3 out black
		spriteBox.width = this.width;
		spriteBox.height = this.width / 13 * 10;

		this.cw = spriteBox.width / 130 * 125;
		this.ch = spriteBox.height * 70 / 105;
	}
};



CardSprite.prototype.createDir0 = function() {
	var spriteBox, spriteMj;
	if (this.type == 0) { // dir0 in
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.upcard4'];
		spriteMj = game.add.image(0, 0, 'mjcards');
		spriteMj.frameName = CardSprite.cardMap[CardSprite.getCardKey(this.cn)];
	} else if (this.type == 1) { // dir0 out white
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.upcard0'];
		spriteMj = game.add.image(0, 0, 'mjcards');
		spriteMj.frameName = CardSprite.cardMap[CardSprite.getCardKey(this.cn)];
	} else if (this.type == 2) { // dir 0 out black
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.downcard0'];
	} else if(this.type == 3){
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = 'Mj_2';
	}
	this.group.add(spriteBox);
	if (spriteMj) {
		this.group.add(spriteMj);
	}
	this.spriteBox = spriteBox;
	this.spriteMj = spriteMj;
}

CardSprite.prototype.createDir1 = function() {
	var spriteBox, spriteMj;
	if (this.type == 0) { // dir1 in
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.downcard4'];
	} else if (this.type == 1) { // dir1 out white
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.upcard3'];
		spriteMj = game.add.image(0, 0, 'mjcards');
		spriteMj.frameName = CardSprite.cardMap[CardSprite.getCardKey(this.cn)];
	} else if (this.type == 2) { // dir 1 out black
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.downcard1'];
	}
	this.group.add(spriteBox);
	if (spriteMj) {
		this.group.add(spriteMj);
	}
	this.spriteBox = spriteBox;
	this.spriteMj = spriteMj;
}

CardSprite.prototype.createDir2 = function() {
	var spriteBox, spriteMj;
	if (this.type == 0) { // dir2 in
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.downcard2'];

	} else if (this.type == 1) { // dir1 out white
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.upcard0'];

		spriteMj = game.add.image(0, 0, 'mjcards');
		spriteMj.frameName = CardSprite.cardMap[CardSprite.getCardKey(this.cn)];
	} else if (this.type == 2) { // dir 1 out black
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.downcard0'];
	}
	this.cw = spriteBox.width * 100 / 105;
	this.ch = spriteBox.height * 0.8;
	this.group.add(spriteBox);
	if (spriteMj) {
		this.group.add(spriteMj);
	}
	this.spriteBox = spriteBox;
	this.spriteMj = spriteMj;
}

CardSprite.prototype.createDir3 = function() {
	var spriteBox, spriteMj;
	if (this.type == 0) { // dir3 in
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.downcard3'];
	} else if (this.type == 1) { // dir3 out white
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.upcard1'];
		spriteMj = game.add.image(0, 0, 'mjcards');
		spriteMj.frameName = CardSprite.cardMap[CardSprite.getCardKey(this.cn)];
	} else if (this.type == 2) { // dir 3 out black
		spriteBox = game.add.image(0, 0, 'mjcards');
		spriteBox.frameName = CardSprite.cardMap['card.downcard1'];
	}
	this.group.add(spriteBox);
	if (spriteMj) {
		this.group.add(spriteMj);
	}
	this.spriteBox = spriteBox;
	this.spriteMj = spriteMj;
};

CardSprite.createCardMap = function(){
	CardSprite.cardMap = {};
	var i, j, index = 0;
	for (j = 0; j < 3; j++) {
		for (i = 0; i < 9; i++) {
			if (j == 0) {
				CardSprite.cardMap['card.card' + index] = 'Character_' + (i + 1) ;
			} else if (j == 1) {
				CardSprite.cardMap['card.card' + index] = 'Dot_' + (i + 1) ;
			} else {
				CardSprite.cardMap['card.card' + index] = 'Bamboo_' + (i + 1);
			}
			index = index + 1;
		}
	}
	CardSprite.cardMap['card.card' + index] = 'zi_east';
	index = index + 1;
	CardSprite.cardMap['card.card' + index] = 'zi_south';
	index = index + 1;
	CardSprite.cardMap['card.card' + index] = 'zi_west';
	index = index + 1;
	CardSprite.cardMap['card.card' + index] = 'zi_north';
	index = index + 1;
	CardSprite.cardMap['card.card' + index] = 'zi_zhong';
	index = index + 1;
	CardSprite.cardMap['card.card' + index] = 'zi_fa';
	index = index + 1;
	CardSprite.cardMap['card.card' + index] = 'zi_bai';
	index = index + 1;
	for (i = 0; i < 8; i++) {
		CardSprite.cardMap['card.card' + index] = 'flower_' + i;
		index = index + 1;
	}
	for (i = 0; i < 5; i++) {
		CardSprite.cardMap['card.upcard' + i] = 'Mj_up_' + i;
	}
	for (i = 0; i < 5; i++) {
		CardSprite.cardMap['card.downcard' + i] = 'Mj_' + i;
	}
};

CardSprite.prototype.create = function() {

	GroupSprite.prototype.create.apply(this, arguments);

	this.calBaida();
	if(!CardSprite.cardMap){
		CardSprite.createCardMap();
	}
	if (this.dir == 0) {
		this.createDir0();
	} else if (this.dir == 1) {
		this.createDir1();
	} else if (this.dir == 2) {
		this.createDir2();
	} else {
		this.createDir3();
	}
}

CardSprite.prototype.mask = function(tint) {
	this.group.getChildAt(0).tint = tint;
}

CardSprite.test = function() {
	

	
	var c, group;
	for (var i = 0; i < 4; i++) {

		group = game.add.group();

		for (var j = 0; j < 3; j++) {
			c = new CardSprite(i, j, 100, 0);
			c.create();
			c.layout({w:game.world.width/17});
			c.group.x = j * c.cw;
			group.add(c.group);
		}
		for (var k = 0; k < 3; k++) {
			for (var j = 0; j < 3; j++) {
				c = new CardSprite(i, k, 100, 0);
				c.create();
				c.layout({w:game.world.width/17});
				c.group.y = (j + 1) * c.ch;
				c.group.x = k * c.cw;
				group.add(c.group);
			}
		}

		group.x = 3 * i * game.world.width/17;
	}
}

function BaidaCardSprite(){
	

}
BaidaCardSprite.prototype = util.inherit(GroupSprite.prototype);
BaidaCardSprite.prototype.constructor = BaidaCardSprite;

BaidaCardSprite.prototype.layout = function(wh){
	this.width = wh.w;
	this.height = 1.6*this.width;
	
	var offy = this.height/30;
	
	this.boxSprite.width = this.width;
	this.boxSprite.height = this.height;
	
	this.baidaName.width = this.width/4 *3;
	this.baidaName.height = this.baidaName.width  /49 * 31;
	this.baidaName.x = (this.boxSprite.width - this.baidaName.width)/2;
	this.baidaName.y = this.height - this.baidaName.height ;
	
	if(!this.whiteCardSprite){
		return ;
	}
	var cardWidth = this.width/4*3;
	this.whiteCardSprite.layout({w:cardWidth})
	this.whiteCardSprite.group.x = (this.boxSprite.width - cardWidth)/2;
	this.whiteCardSprite.group.y = offy;
}

BaidaCardSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.boxSprite = game.add.image(0,0,'baidaCard');
	this.boxSprite.frameName='baida_box.png';
	this.group.add(this.boxSprite);
	
	this.baidaName = game.add.image(0,0,'baidaCard');
	this.baidaName.frameName ='baida.png';
	this.group.add(this.baidaName);
	
	
	
	
}

BaidaCardSprite.prototype.addBaida = function(cn){
	if(this.whiteCardSprite){
		this.group.remove(this.whiteCardSprite.group);
		this.whiteCardSprite.group.destroy();
	}
	
	this.whiteCardSprite = new CardSprite(0, 1, cn,0);
	this.whiteCardSprite.create();
	this.group.add(this.whiteCardSprite.group);
	this.layout({w:this.width});
	
}
BaidaCardSprite.test  = function(){
	var bc = new BaidaCardSprite();
	bc.create();
	bc.layout({w:100});
	bc.addBaida(100);
	bc.addBaida(64);
}
