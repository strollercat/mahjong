function CardsShootSprite(commandHandler) {
	this.commandHandler = commandHandler;

	this.bg;
	this.choose;
	this.comfirmBtn;

	this.cardsArr = [];

	this.cw;
	this.ch;
	this.offy;
	this.offx;

}

CardsShootSprite.prototype = util.inherit(GroupSprite.prototype);
CardsShootSprite.prototype.constructor = CardsShootSprite;

CardsShootSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	this.height = wh.h;

	this.bg.width = this.width;
	this.bg.height = this.height;

	this.cw = this.width / 640 * 304 / 3;
	this.ch = this.height / 1007 * 152;
	this.offx = this.width / 640 * 70;
	this.offy = this.height / 1007 * 157;

	this.layoutCards();

	this.choose.layout({
		w : game.world.width,
		h : 1.4 * this.ch
	});
	this.choose.group.y = 3.15 * this.ch + this.offy;

	this.comfirmBtn.width = 2 * this.cw;
	this.comfirmBtn.height = this.cw;
	this.comfirmBtn.x = game.world.width / 2 - this.comfirmBtn.width / 2;
	this.comfirmBtn.y = this.choose.group.y + 1.1 * this.choose.height;

}

CardsShootSprite.prototype.layoutCards = function() {

	for (var i = 0; i < this.cardsArr.length; i++) {
		var cs = this.cardsArr[i];
		cs.layout({
			w : this.cw,
			h : this.ch
		});
		cs.group.x = this.offx;
		cs.group.y = this.offy + i * this.ch;
	}
}

CardsShootSprite.prototype.getCardsCns = function() {
	if (this.cardsArr == null) {
		return null;
	}

	var cns = [];
	for (var i = 0; i < this.cardsArr.length; i++) {
		var cs = this.cardsArr[i];
		cns = cns.concat(cs.getCardsCns());
	}
	return cns;

}
CardsShootSprite.prototype.getCardsPointerByIndex = function(index) {
	var px, py;
	if (index >= 0 && index <= 2) {
		px = 0;
		py = index;
	} else if (index >= 3 && index <= 7) {
		px = 1;
		py = index - 3;
	} else {
		px = 2;
		py = index - 8;
	}
	return {
		x : px,
		y : py
	};

}

CardsShootSprite.changeCard = function(oriPoint, destPoint, context) {
	console.log(oriPoint.x + " " + oriPoint.y + " " + destPoint.x + " "
			+ destPoint.y);
	if (destPoint.x == oriPoint.x && destPoint.y == oriPoint.y) {
		console.log('same card');
		return;
	}
	if (destPoint.x == oriPoint.x) {
		console.log('same line');

		var cs = context.cardsArr[oriPoint.x];
		var oriC = cs.sprites[oriPoint.y];
		var destC = cs.sprites[destPoint.y];
		cs.sprites[oriPoint.y] = destC;
		cs.sprites[destPoint.y] = oriC;
		cs.layout({
			w : cs.width,
			h : cs.height
		});
	} else {
		var oriC = context.cardsArr[oriPoint.x].sprites[oriPoint.y];
		var oriCs = context.cardsArr[oriPoint.x];
		var destC = context.cardsArr[destPoint.x].sprites[destPoint.y];
		var destCs = context.cardsArr[destPoint.x];

		oriCs.group.remove(oriC.group);
		oriCs.group.add(destC.group);
		oriCs.sprites[oriPoint.y] = destC;
		oriCs.layout({
			w : oriCs.width,
			h : oriCs.height
		});
		// oriCs.consoleCn();

		destCs.group.remove(destC.group);
		destCs.group.add(oriC.group);
		destCs.sprites[destPoint.y] = oriC;
		destCs.layout({
			w : destCs.width,
			h : destCs.height
		});
		// destCs.consoleCn();
	}

	var sprites = context.cardsArr[0].sprites
			.concat(context.cardsArr[1].sprites);
	sprites = sprites.concat(context.cardsArr[2].sprites);
	for (var i = 0; i < sprites.length; i++) {
		sprites[i].sprite.index = i;
	}

	// console.log(context.getCardsCns());

}

CardsShootSprite.prototype.setCards = function(cns) {
	this.clearCards();

	this.cardsArr[0].addCard(cns.slice(0, 3));
	this.cardsArr[1].addCard(cns.slice(3, 8));
	this.cardsArr[2].addCard(cns.slice(8, 13));
	this.layoutCards();

	var sprites = this.cardsArr[0].sprites.concat(this.cardsArr[1].sprites);
	sprites = sprites.concat(this.cardsArr[2].sprites);
	var context = this;
	for (var i = 0; i < sprites.length; i++) {
		sprites[i].sprite.inputEnabled = true;
		sprites[i].sprite.input.enableDrag();
		sprites[i].sprite.index = i;

		sprites[i].sprite.events.onDragStart.add(function(sprite, pointer,
				dragX, dragY, snapPoint) {
			var point = context.getCardsPointerByIndex(sprite.index);
			cs = context.cardsArr[point.x];
			context.group.bringToTop(cs.group);
			cs.group.bringToTop(cs.sprites[point.y].group);
		});

		sprites[i].sprite.events.onDragStop.add(function(sprite) {

			sprite.x = 0;
			sprite.y = 0;

			var mx = game.input.mousePointer.x;
			var my = game.input.mousePointer.y;
			var indexX = mx - context.offx
			var indexY = my - context.offy;

			if (indexY < 0) {
				return;
			}
			if (indexY > 3 * context.ch) {
				return;
			}
			indexY = parseInt(indexY / context.ch);
			// console.log(indexY);
			if (indexY == 0 && (indexX < 0 || indexX > 3 * context.cw)) {
				return;
			}

			if ((indexY == 1 || indexY == 2)
					&& (indexX < 0 || indexX > 5 * context.cw)) {
				return;
			}
			indexX = parseInt(indexX / context.cw);
			var destPoint = {
				x : indexY,
				y : indexX
			};
			var oriPoint = context.getCardsPointerByIndex(sprite.index);
			CardsShootSprite.changeCard(oriPoint, destPoint, context);
		});
	}

}

CardsShootSprite.prototype.clearCards = function() {
	for (var i = 0; i < this.cardsArr.length; i++) {
		var cs = this.cardsArr[i];
		cs.clear();
	}
}

CardsShootSprite.prototype.create = function() {

	GroupSprite.prototype.create.apply(this, arguments);

	this.bg = game.add.image(0, 0, 'thirteen');
	this.bg.frameName = "thirteen_seiri_poker_bg";
	this.group.add(this.bg);

	this.comfirmBtn = game.add.button(0, 0, 'thirteen', function(btn) {
		console.log(this.getCardsCns());
		this.commandHandler.listen('threeWaterShoot', this.getCardsCns());
		if (game.audioManager) {
			game.audioManager.playByKey('click');
		}
	}, this, 'thirteen_seiri_poker_chupai_btn',
			'thirteen_seiri_poker_chupai_btn',
			'thirteen_seiri_poker_chupai_btn');
	this.group.add(this.comfirmBtn);

	for (var i = 0; i < 3; i++) {
		var cs = new CardsSprite();
		cs.create();
		this.cardsArr.push(cs);
		this.group.add(cs.group);
	}

	var context = this;
	this.choose = new CandidateSprite(function(cns) {
		if (cns == null) {
			return;
		}
		context.setCards(cns);
	});
	this.choose.create();
	this.group.add(this.choose.group);
}
CardsShootSprite.prototype.clear = function() {
	this.clearCards();
	this.choose.clear();
	this.group.visible = false;
}

CardsShootSprite.test = function() {

	var sprite = new CardsShootSprite();
	sprite.create();
	sprite.layout({
		w : game.world.width,
		h : game.world.height
	});
	sprite.setCards([ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ]);

	var cnsArr = [];
	cnsArr.push([ {
		name : "对子",
		cns : [ 0, 1, 2 ]
	}, {
		name : "對子",
		cns : [ 3, 4, 5, 6, 7 ]
	}, {
		name : "烏龍",
		cns : [ 8, 9, 10, 11, 12 ]
	} ]);
	cnsArr.push([ {
		name : "兩都",
		cns : [ 12, 13, 14 ]
	}, {
		name : "對子",
		cns : [ 15, 16, 17, 18, 19 ]
	}, {
		name : "烏龍",
		cns : [ 20, 21, 22, 23, 24 ]
	} ]);
	sprite.choose.setCandidate(cnsArr);
	console.log(sprite.getCardsCns());
	// var sprite = new PokerCardSprite(13);
	// sprite.create();
	// sprite.layout({w:80});
}
