function Cards0Sprite(innerWidth, middleWidth, outterWidth, commandHandler) {
	this.clickEnable;
	this.commandHandler = commandHandler;
	this.middleWidth = middleWidth;
	this.innerWidth = innerWidth;
	this.outterWidth = outterWidth;
};

Cards0Sprite.prototype = util.inherit(CardsSprite.prototype);
Cards0Sprite.prototype.constructor = Cards0Sprite;

Cards0Sprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.innerGroup = game.add.group();
	this.middleGroup = game.add.group();
	this.outterGroup = game.add.group();
	this.innerDownGroup = game.add.group();
	this.innerDownGroup.visible = false;
	this.innerUpGroup = game.add.group();
	this.innerUpGroup.visible = false;
//	this.group.add(this.innerGroup);
//	this.group.add(this.middleGroup);
//	this.group.add(this.outterGroup);
//	this.group.add(this.innerDownGroup);

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



Cards0Sprite.prototype.addInnerCardSingle = function(cn) {
	var cs = new CardSprite(this.getDir(), 0, cn, this.innerWidth);
	cs.create();
	cs.layout({w:this.innerWidth});
	if (cs.baida) {
		cs.mask("0xFFFF00");
	}
	this.innerArr.push(cs);
	this.innerGroup.add(cs.group);
	this.ajustInnerCard();
	
	var spriteBox = cs.group.getChildAt(0);
	spriteBox.c0 = this;
	spriteBox.cs = cs;
	spriteBox.click = false;
	spriteBox.inputEnabled = true;
	spriteBox.events.onInputUp.add(this.clickHandle, this);
	
	var spriteMj = cs.group.getChildAt(1);
    spriteMj.oriX = spriteMj.x;
    spriteMj.oriY = spriteMj.y;

	var cards0 = this;

    spriteBox.events.onDragStart.add(function(sprite, pointer, dragX, dragY, snapPoint){
//    	console.log('drag start');
		if(game.audioManager){
			game.audioManager.playByKey('uiclick');
		}
//		game.world.bringToTop(cards0.group);
//		cards0.group.bringToTop(cards0.innerGroup);
		game.world.bringToTop(cards0.innerGroup);
		cards0.innerGroup.bringToTop(spriteBox.cs.group);
    });
    spriteBox.events.onDragUpdate.add(function(sprite, pointer, dragX, dragY, snapPoint){
//    	console.log('drag update X Y '+ dragX + ' ' + dragY);
//    	console.log(game.world.angle);
    	if(game.world.angle == -90){
    		var spriteMj1 = sprite.cs.group.getChildAt(1);
			spriteMj1.x =  spriteMj1.oriX - dragY;
			spriteMj1.y = spriteMj1.oriY + dragX;
			sprite.x = -dragY;
			sprite.y = dragX;
    	}else{
    		var spriteMj1 = sprite.cs.group.getChildAt(1);
			spriteMj1.x =  spriteMj1.oriX + dragX;
			spriteMj1.y = spriteMj1.oriY + dragY;
    	}
    });
    spriteBox.events.onDragStop.add(function(sprite){
//    	console.log('drag stop');
//    	console.log(sprite.y + " "+ sprite.c0.innerWidth);
    	if(Math.abs(sprite.y)  > 1 * sprite.c0.innerWidth){
//    		console.log('da chuqu !');
    		if(sprite.c0.commandHandler){
    			sprite.c0.commandHandler.listen('da', sprite.cs.cn);
    		}
    		return ;
    	}
    	
		sprite.x = 0;
		sprite.y = 0;
		var spriteMj1 = sprite.cs.group.getChildAt(1);
		spriteMj1.x = spriteMj1.oriX;
    	spriteMj1.y = spriteMj1.oriY;
    });
};

/**
 * 排序
 */
Cards0Sprite.prototype.sortInnerCard = function(){
	this.innerArr.sort(CardSprite.compare);
	this.ajustInnerCard();
};
/**
 *  如果14张，最后一张留点边
 */
Cards0Sprite.prototype.seperateInnerCard = function(){
	var group = this.innerArr[this.innerArr.length - 1].group;
	group.x += this.innerWidth * 0.2;
};
Cards0Sprite.prototype.removeInnerCard = function(cn) {
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

Cards0Sprite.prototype.getDir = function() {
	return 0;
}
Cards0Sprite.prototype.ajustInnerCard = function(c) {
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		card.group.x = i * card.cw;
	}
};
Cards0Sprite.prototype.ajustMiddleCard = function(c){
	
	var cmcs;
	for (var i = 0; i < this.middleArr.length; i++) {
		cmcs = this.middleArr[i];
		cmcs.group.x = i * cmcs.cw;
	}
	this.ajustInnerGroup();
	
}
Cards0Sprite.prototype.ajustOutterCard = function(c){
	
	var cs;
	for(var i = 0;i< this.outterArr.length;i++){
		cs = this.outterArr[i];
		cs.group.x = (i % 10) * cs.cw;
		cs.group.y = (2 - parseInt(i / 10)) * cs.ch;
	}
	var len = this.outterArr.length;
	for (var i = len - 1; i >= 0; i--) {
		this.outterGroup.bringToTop(this.outterArr[i].group);
	}
	
}
Cards0Sprite.prototype.ajustInnerGroup = function() {
	if (this.middleArr.length == 0) {
		return;
	}
	this.innerGroup.x = this.middleGroup.x + this.middleArr.length * this.middleArr[0].cw + 0.1* this.middleWidth;
}

Cards0Sprite.prototype.clickHandle = function(sprite) {
//	console.log('clickHandle');
	if (this.clickEnable) {
		var i, c0 = sprite.c0, cs = sprite.cs, click = sprite.click, tmpcs, spriteBox;
		if (click) {
			if (c0.commandHandler) {
				c0.commandHandler.listen('da', cs.cn);
			}
		} else {
			sprite.click = true;
			
			cs.group.y -= 0.2 * cs.ch;
			for (i = 0; i < c0.innerArr.length; i++) {
				tmpcs = c0.innerArr[i];
				spriteBox = tmpcs.group.getChildAt(0);
				if (spriteBox === sprite) {
					continue;
				}
				if (spriteBox.click) {
					spriteBox.click = false;
					tmpcs.group.y += 0.2 * tmpcs.ch;
				}
			}
		}
	}
};
Cards0Sprite.prototype.clickEnabled = function(enable) {
	this.clickEnable = enable;
	
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		if(enable){
			card.group.getChildAt(0).input.enableDrag();
			card.group.getChildAt(0).inputEnabled = true;
		}else{
			card.group.getChildAt(0).input.disableDrag();
			card.group.getChildAt(0).inputEnabled = false;
		}
	};
};

Cards0Sprite.prototype.clickDisable = function(cardsArr) {
	if(!cardsArr || cardsArr.length == 0){
		return ;
	}
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		if(util.hasNumber(cardsArr,card.cn)){
			card.group.getChildAt(0).input.disableDrag();
			card.group.getChildAt(0).inputEnabled = false;
			card.mask('0x808080'); 
		}
	};
};
Cards0Sprite.prototype.recoverColor = function() {
	for (var i = 0; i < this.innerArr.length; i++) {
		var card = this.innerArr[i];
		if (card.baida) {
			card.mask("0xFFFF00");
		}else{
			card.mask("0xFFFFFF");
		}
	};
};

Cards0Sprite.prototype.clear = function(){
	CardsSprite.prototype.clear.apply(this, arguments);
//	this.ajustInnerGroup();
	this.innerGroup.x = this.middleGroup.x;
	this.clickEnabled(false);
}

Cards0Sprite.test = function() {
	var authorBoxCw = game.world.width/17;
	var otherBoxCw = authorBoxCw / 5 * 3;
	var cs = new Cards0Sprite(0,0,0,null);
	cs.create();
	cs.outterGroup.y = 4 * authorBoxCw;
	
	cs.addInnerCard([ 102, 101, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ]);
	cs.addInnerCard(13);
	cs.layout({iw:authorBoxCw,mw:authorBoxCw,ow:otherBoxCw});
	
	
	
	cs.clickEnabled(true);
	cs.sortInnerCard();
	cs.seperateInnerCard();
	
//	cs.downInner();
	cs.upInner([ 102, 101, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13 ]);
//	cs.removeInnerCard(102);

	for (var i = 0; i < 25; i++) {
		cs.addOutterCard(i + 50);
	}
//	// c0s.removeOutterCard(74);
//	//
//	cs.addMiddlePengCard(0, 1);
//	cs.addMiddleChiCard([ 0, 4, 8 ], 1, 8);
//	cs.addMiddleAnGangCard(0);
//	cs.addMiddleMingGangCard(13, 1);
//	cs.addMiddleXianGangCard(1);
	
	
	//
	cs.showArrow(true);
	
	cs.layout({iw:authorBoxCw,mw:authorBoxCw,ow:otherBoxCw});
//	cs.showArrow(false);
	//
	// //c0s.downInner();

}