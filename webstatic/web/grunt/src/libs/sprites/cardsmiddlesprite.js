/**
 * dir:0,1,2,3 kind:peng,chi,minggang,angang,xiangang
 */
function CardsMiddleSprite(dir, kind, cns, width, who, cn) {

	this.dir = dir;
	this.kind = kind;
	this.cns = cns;
	this.width = width;
	this.who = who;
	this.cn = cn;

	this.clickHandler;

	this.cw;
	this.ch;

	this.csArr;
	this.arrow;
};

CardsMiddleSprite.ajustMiddle = function(cns, cn) {
	if (cns[1] != cn) {
		var cni;
		for (var i = 0; i < cns.length; i++) {
			if (cns[i] == cn) {
				cni = i;
				break;
			}
		}
		cns[cni] = cns[1];
		cns[1] = cn;
	}
	return cns;
}
CardsMiddleSprite.prototype = util.inherit(GroupSprite.prototype);
CardsMiddleSprite.prototype.constructor = CardsMiddleSprite;



CardsMiddleSprite.prototype.layout = function(wh) {
	this.width = wh.w;

	var cs, type = 0;
	for (var i = 0; i < 3; i++) {
		cs = this.csArr[i];
		cs.layout(wh);
		if (this.dir == 0) {
			cs.group.x = i * cs.cw;
		} else if (this.dir == 1) {
			cs.group.y = (2 - i) * cs.ch;
		} else if (this.dir == 2) {
			cs.group.x = (2 - i) * cs.cw;
		} else if (this.dir == 3) {
			cs.group.y = i * cs.ch;
		}
	}
	if (this.dir == 0 || this.dir == 2) {
		this.cw = cs.cw * 3;
		this.ch = cs.ch;
	} else if (this.dir == 1 || this.dir == 3) {
		this.cw = cs.cw;
		this.ch = 3 * cs.ch;
	}

	if (this.kind == 'xiangang' || this.kind == 'minggang') {
		type = 1;
	} else if (this.kind == 'angang') {
		type = 2;
	}
	if (type != 0) {
		cs = this.csArr[3];
		cs.layout(wh);
		if (this.dir == 0) {
			cs.group.x = 1 * cs.cw;
			cs.group.y = cs.ch - cs.group.height;
		} else if (this.dir == 1) {
			cs.group.y = 1 * cs.ch - (cs.group.height - cs.ch);
		} else if (this.dir == 2) {
			cs.group.x = 1 * cs.cw;
			cs.group.y = cs.ch - cs.group.height;
		} else if (this.dir == 3) {
			cs.group.y = 1 * cs.ch - (cs.group.height - cs.ch);
		}
	}
	this.layoutArrow();
}
CardsMiddleSprite.prototype.layoutArrow = function() {
	if (this.kind == 'angang' || this.who == this.dir) {
		return;
	}
	var middleCs = this.csArr[1], cw = middleCs.cw, ch = middleCs.ch, arrow = this.arrow;
	if (this.kind == 'minggang' || this.kind == 'xiangang') {
		middleCs = this.csArr[3];
	}

	
	arrow.x = middleCs.group.x;
	arrow.y = middleCs.group.y;
	arrow.height = middleCs.ch / 3 * 2;
	if(this.dir == 1|| this.dir ==3){
		arrow.height = middleCs.cw /100 *80 /3 * 2;
	}
	arrow.width = arrow.height / 34 * 26;
	if (this.who == 3) {
		arrow.agnle = 0;
		arrow.x += 0.5 * (middleCs.cw - arrow.width);
		arrow.y += 0.5 * (middleCs.ch - arrow.height);
	} else if (this.who == 2) {
		arrow.angle = 90;
		arrow.x += (middleCs.cw * 0.5 + arrow.width / 3 * 2);
		if(this.dir == 0){
			arrow.y += 0.5 * arrow.height ;
		}else{
			arrow.y += 0.2 * arrow.height ;
		}
	} else if (this.who == 1) {
		arrow.angle = 180;
		arrow.x += middleCs.cw * 0.5 + 0.5 * arrow.width;
		arrow.y += 0.5 * (arrow.height + middleCs.ch);
	} else if (this.who == 0) {
		arrow.angle = -90;
		arrow.x += (middleCs.cw - arrow.width) / 3;
		arrow.y += 0.5 * (middleCs.ch + arrow.height);
	}
}






CardsMiddleSprite.prototype.create = function() {

	GroupSprite.prototype.create.apply(this, arguments);

	this.csArr = [];
	this.cns.sort();

	var cs, type = 0;
	if (this.kind == 'chi') {
		CardsMiddleSprite.ajustMiddle(this.cns, this.cn);
	}
	for (var i = 0; i < 3; i++) {
		cs = new CardSprite(this.dir, 1, this.cns[i], 0);
		cs.create();
		this.group.add(cs.group);
		this.csArr.push(cs);
	}
	if (this.dir == 1) {
		this.group.bringToTop(this.csArr[1].group);
		this.group.bringToTop(this.csArr[0].group);
	}
	

	if (this.kind == 'xiangang' || this.kind == 'minggang') {
		type = 1;
	} else if (this.kind == 'angang') {
		type = 2;
	}
	if (type != 0) {
		cs = new CardSprite(this.dir, type, this.cns[3], 0);
		cs.create();
		this.group.add(cs.group);
		this.csArr.push(cs);
	}
	this.createDirArrow();
}
CardsMiddleSprite.prototype.createDirArrow = function() {
	if (this.kind == 'angang' || this.who == this.dir) {
		return;
	}
	var middleCs = this.csArr[1], cw = middleCs.cw, ch = middleCs.ch, arrow;
	if (this.kind == 'minggang' || this.kind == 'xiangang') {
		middleCs = this.csArr[3];
	}

	middleCs.mask("0xA9A9A9");
	arrow = game.add.image(0, 0, 'other');
	arrow.frameName = 'arrow_1' ;
	arrow.tint = "0xFF0000";
	this.group.add(arrow);
	this.arrow = arrow;

}

CardsMiddleSprite.spriteBoxHandle = function(sprite) {
	var mcs = sprite.mcs;

	if (!mcs.clickHandler) {
		return;
	}
	if (mcs.kind == 'chi') {
		mcs.clickHandler('chi', util.getRemoveItemArr(mcs.cns, mcs.cn));
	} else if (mcs.kind == 'angang') {
		mcs.clickHandler('angang', mcs.cns);
	} else {
		mcs.clickHandler(mcs.kind, mcs.cns);
	}
}

CardsMiddleSprite.prototype.click = function(clickHandler) {
	this.clickHandler = clickHandler;

	var len = this.cns.length;
	var i, csgroup, spriteBox;
	for (var i = 0; i < len; i++) {
		csgroup = this.group.getChildAt(i)
		spriteBox = csgroup.getChildAt(0);
		spriteBox.inputEnabled = true;
		spriteBox.events.onInputDown.add(CardsMiddleSprite.spriteBoxHandle,
				this);
		spriteBox.mcs = this;
	}
}

CardsMiddleSprite.test = function() {
	var i, j, group, mcs, offy = 20;
	for (i = 0; i < 4; i++) {
		group = game.add.group();
		group.y = offy;

		for (j = 0; j < 4; j++) {
			mcs = new CardsMiddleSprite(i, 'peng', [ 0, 1, 2 ],
					0, j, 1);
			mcs.create();
			mcs.layout({w:game.world.width/17 * 1.5});
			mcs.group.x = j * mcs.cw;
			group.add(mcs.group);
		}
		offy += mcs.ch;
	}
}