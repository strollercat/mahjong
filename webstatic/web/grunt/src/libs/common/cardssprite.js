function CardsSprite() {
	
};
CardsSprite.prototype = util.inherit(GroupSprite.prototype);
CardsSprite.prototype.constructor = CardsSprite;

CardsSprite.prototype.innerGroup = null;
CardsSprite.prototype.middleGroup = null;
CardsSprite.prototype.outterGroup = null;
CardsSprite.prototype.innerDownGroup = null;
CardsSprite.prototype.innerUpGroup = null;

CardsSprite.prototype.innerArr = null;
CardsSprite.prototype.middleArr = null;
CardsSprite.prototype.outterArr = null;
CardsSprite.prototype.innerDownArr = null;
CardsSprite.prototype.innerUpArr = null;

CardsSprite.prototype.spriteArrowGroup = null;
CardsSprite.prototype.spriteArrow = null;
CardsSprite.prototype.tweenArrow = null;

CardsSprite.prototype.innerWidth = null;
CardsSprite.prototype.middleWidth = null;
CardsSprite.prototype.outterWidth = null;

CardsSprite.prototype.addMiddleCardsInner = function(kind, cns, who, cn) {
	var width = this.middleWidth;
	var cmcs = new CardsMiddleSprite(this.getDir(), kind, cns, width, who, cn);
	cmcs.create();
	cmcs.layout({w:this.middleWidth});
	this.middleArr.push(cmcs);
	this.middleGroup.add(cmcs.group);
	return cmcs;
}

CardsSprite.prototype.layoutInner = function(){
	for(var i = 0;i<this.innerArr.length;i++){
		this.innerArr[i].layout({w:this.innerWidth});
	}
	this.ajustInnerCard();
};
CardsSprite.prototype.layoutMiddle = function(){
	for(var i = 0;i< this.middleArr.length;i++){
		this.middleArr[i].layout({w:this.middleWidth});
	}
	this.ajustMiddleCard();
};
CardsSprite.prototype.layoutOutter = function(){
	for(var i = 0;i<this.outterArr.length;i++){
		this.outterArr[i].layout({w:this.outterWidth});
	}
	this.ajustOutterCard();
};


CardsSprite.prototype.layout = function(wh) {
	this.innerWidth = wh.iw;
	this.middleWidth = wh.mw;
	this.outterWidth = wh.ow;
	
	this.layoutInner();
	this.layoutMiddle();
	this.layoutOutter();
	if(this.getDir() == 0){
		this.ajustInnerGroup();
	}else if(this.getDir() ==1){
		this.ajustMiddleGroup();
	}else if(this.getDir()==2){
		this.ajustMiddleGroup();
	}else if(this.getDir() == 3){
		this.ajustInnerGroup();
	}
	
	if(this.getDir() == 0 || this.getDir() == 2){
		this.spriteArrow.width = this.outterWidth / 3 * 2;
	}else{
		this.spriteArrow.width = this.outterWidth /100 * 70 / 3 * 2 ;
	}
	this.spriteArrow.height = 74 * this.spriteArrow.width / 54;
	
	
	if(this.tweenArrow){
		this.tweenArrow.stop();
	}
	var property = {
		x : 0,
		y : 0.3 * this.spriteArrow.height
	};
	this.tweenArrow = game.add.tween(this.spriteArrow);
	this.tweenArrow.to(property, 500, Phaser.Easing.Linear.None).yoyo(true)
			.loop().start();
	this.outterGroup.bringToTop(this.spriteArrowGroup);
	
}

CardsSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
//	console.log('cardssprite create');
	
	this.innerGroup = game.add.group();
	this.middleGroup = game.add.group();
	this.outterGroup = game.add.group();
	this.innerDownGroup = game.add.group();
	this.innerDownGroup.visible = false;
	this.innerUpGroup = game.add.group();
	this.innerUpGroup.visible = false;

	this.group.add(this.innerGroup);
	this.group.add(this.middleGroup);
	this.group.add(this.outterGroup);
	this.group.add(this.innerDownGroup);
    this.group.add(this.innerUpGroup);
    
	this.innerArr = [];
	this.middleArr = [];
	this.outterArr = [];
	this.innerDownArr = [];
	this.innerUpArr = [];
	
	this.spriteArrowGroup = game.add.group();
	this.spriteArrow = game.add.image(0, 0, 'other');
	this.spriteArrow.frameName = 'red_arrow';
	this.spriteArrowGroup.add(this.spriteArrow);
	this.spriteArrowGroup.visible = false;
	this.outterGroup.add(this.spriteArrowGroup);
	
	
	

}


CardsSprite.prototype.addInnerCardSingle = function(cn) {
	
};
CardsSprite.prototype.addInnerCard = function(cn) {
	if (cn instanceof Array) {
		for (var i = 0; i < cn.length; i++) {
			this.addInnerCardSingle(cn[i]);
		}
	} else {
		this.addInnerCardSingle(cn);
	}
}
/**
 * 排序
 */
CardsSprite.prototype.sortInnerCard = function(){
	
};
/**
 *  如果14张，最后一张留点边
 */
CardsSprite.prototype.seperateInnerCard = function(){
	
};
CardsSprite.prototype.removeInnerCard = function(cn) {
	if (this.innerArr.length == 0) {
		return;
	}
	this.innerGroup.remove(this.innerArr[0].group);
	this.innerArr[0].group.destroy();
	this.innerArr.shift();
	this.ajustInnerCard();
	this.ajustMiddleGroup();
};

CardsSprite.prototype.addMiddlePengCard = function(cn, who) {
	this.addMiddleCardsInner('peng', [ cn, cn, cn ], who,cn);
	this.ajustMiddleCard();
}
CardsSprite.prototype.addMiddleChiCard = function(cns, who, cn) {
	this.addMiddleCardsInner('chi', cns, who, cn);
	this.ajustMiddleCard();
}
CardsSprite.prototype.addMiddleAnGangCard = function(cn) {
	this.addMiddleCardsInner('angang', [ cn, cn, cn, cn ],
			-1, -1);
	this.ajustMiddleCard();
}
CardsSprite.prototype.addMiddleXianGangCard = function(cn) {
	var i, cmcs;
	if (this.middleArr && this.middleArr.length > 0) {
		for (i = 0; i < this.middleArr.length; i++) {
			// console.log('middleArr.length '+ " "+this.middleArr.length);
			cmcs = this.middleArr[i];
			// console.log(cmcs.cns[0]+ " "+cn+" "+cmcs.kind);
			if (cmcs.kind == 'peng' && CardSprite.sameKind(cmcs.cns[0], cn)) {
				// console.log('coming');
				var gx = cmcs.group.x;
				cmcs.group.destroy();
				this.middleGroup.remove(cmcs.group);
				cmcs = new CardsMiddleSprite(this.getDir(), 'minggang', [ cn,
						cn, cn, cn ],this.middleWidth, cmcs.who, cmcs.cn);
				cmcs.create();
				cmcs.layout({w:this.middleWidth});
				cmcs.group.x = gx;
				this.middleArr[i] = cmcs;
				this.middleGroup.add(cmcs.group);
				return;
			}
		}
	}
}
CardsSprite.prototype.addMiddleMingGangCard = function(cn, who) {
	this.addMiddleCardsInner('minggang', [ cn, cn, cn, cn ],
			who, cn);
	this.ajustMiddleCard();
}

CardsSprite.prototype.addOutterCard = function(cn) {
	var width = this.outterWidth;
	var cs = new CardSprite(this.getDir(), 1, cn, width);
	cs.create();
	cs.layout({w:this.outterWidth});
	this.outterArr.push(cs);
	this.outterGroup.add(cs.group);
	this.ajustOutterCard();
}
CardsSprite.prototype.removeOutterCard = function(cn) {
	var cs = this.outterArr[this.outterArr.length - 1];
	if (cs.cn != cn) {
		return;
	}
	this.outterGroup.remove(cs.group);
	cs.group.destroy();
	this.outterArr.length = this.outterArr.length - 1;
}

CardsSprite.prototype.upInner = function(cards){
	
	if(!cards){
		return ;
	}
	if(cards.length == 0){
		return ;
	}
	
	if(cards.length!= this.innerArr.length){
		return ;
	}
	
	this.innerGroup.visible = false;
	this.innerUpGroup.visible = true;
	this.innerUpGroup.x = this.innerGroup.x;
	this.innerUpGroup.y = this.innerGroup.y;
	
	if(this.getDir() ==1 ){
		this.innerUpGroup.x = this.middleGroup.x;
	}else if(this.getDir() ==3){
		this.innerUpGroup.x = this.middleGroup.x;
	}
	
	var len = cards.length, i, cs;
	for (i = 0; i < len; i++) {
		if(this.getDir() == 1 || this.getDir() == 2){
			cs = new CardSprite(this.getDir(), 1, cards[len -1 - i], this.innerWidth);
		}else{
			cs = new CardSprite(this.getDir(), 1, cards[i], this.innerWidth);
		}
		cs.create();
		var width;
		if(this.getDir() == 0 || this.getDir() == 2){
			width = this.innerWidth;
		}else{
			width = this.middleWidth;
		}
		cs.layout({w:width});
		cs.group.x = this.innerArr[i].group.x;
		cs.group.y = this.innerArr[i].group.y;
		this.innerUpArr.push(cs);
		this.innerUpGroup.add(cs.group);
	}	
}

CardsSprite.prototype.downInner = function() {
	this.innerGroup.visible = false;
	this.innerDownGroup.visible = true;
	this.innerDownGroup.x = this.innerGroup.x;
	this.innerDownGroup.y = this.innerGroup.y;
	var len = this.innerArr.length, i, cs;
	for (i = 0; i < len; i++) {
		cs = new CardSprite(this.getDir(), 2, -1, this.innerWidth);
		cs.create();
		cs.layout({w:this.innerWidth});
		cs.group.x = this.innerArr[i].group.x;
		cs.group.y = this.innerArr[i].group.y;
		this.innerDownArr.push(cs);
		this.innerDownGroup.add(cs.group);
	}
	this.group.add(this.innerDownGroup);
}

CardsSprite.prototype.addMiddleCard = function(a, cns, who, cn) {
	if (a == 'chi') {
		this.addMiddleChiCard(cns, who, cn);
	} else if (a == 'peng') {
		this.addMiddlePengCard(cn, who);
	} else if (a == 'minggang') {
		this.addMiddleMingGangCard(cn, who);
	} else if (a == 'angang') {
		this.addMiddleAnGangCard(cns[0]);
	} else if (a == 'xiangang') {
		this.addMiddleXianGangCard(cns[0]);
	}
}

CardsSprite.prototype.showArrow = function(show) {
	if(show == false){
		this.spriteArrowGroup.visible = false;
	}else{
		if (!this.outterArr || this.outterArr.length == 0) {
			this.spriteArrowGroup.visible = false;
			return;
		}
		var cs = this.outterArr[this.outterArr.length - 1];
		this.spriteArrowGroup.visible = true;
		this.spriteArrowGroup.x = cs.group.x + cs.cw / 2 - this.spriteArrowGroup.getChildAt(0).width
				/ 2;
		this.spriteArrowGroup.y = cs.group.y + cs.ch / 3 - this.spriteArrowGroup.getChildAt(0).height;
		this.outterGroup.bringToTop(this.spriteArrowGroup);
	}
};

CardsSprite.prototype.clear = function(){
	this.spriteArrowGroup.visible = false;
	
	var cs,i;
	for(i = 0;i< this.innerArr.length;i++){
		cs = this.innerArr[i];
		this.innerGroup.remove(cs.group);
		cs.group.destroy();
	}
	this.innerArr = [];
	
	for(i = 0;i< this.middleArr.length;i++){
		cs = this.middleArr[i];
		this.middleGroup.remove(cs.group);
		cs.group.destroy();
	}
	this.middleArr = [];
	
	
	for(i = 0;i< this.outterArr.length;i++){
		cs = this.outterArr[i];
		this.outterGroup.remove(cs.group);
		cs.group.destroy();
	}
	this.outterArr = [];
	
	for(i = 0;i< this.innerDownArr.length;i++){
		cs = this.innerDownArr[i];
		this.innerDownGroup.remove(cs.group);
		cs.group.destroy();
	}
	this.innerDownArr = [];
	
	for(i = 0;i< this.innerUpArr.length;i++){
		cs = this.innerUpArr[i];
		this.innerUpGroup.remove(cs.group);
		cs.group.destroy();
	}
	this.innerUpArr = [] ;
	
	
	console.log('clear');
	this.innerGroup.visible = true;
	this.innerDownGroup.visible = false;
	this.innerUpGroup.visible = false;
	
};



CardsSprite.prototype.getDir = function() {
	
};	
CardsSprite.prototype.ajustInnerCard = function(){
	
};
CardsSprite.prototype.ajustMiddleCard = function(){
	
};
CardsSprite.prototype.ajustOutterCard = function(){
	
};
CardsSprite.prototype.ajustInnerGroup = function(){
	
};
CardsSprite.prototype.ajustMiddleGroup = function(){
	
};


