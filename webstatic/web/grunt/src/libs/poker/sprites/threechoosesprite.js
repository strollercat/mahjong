function ThreeChooseSprite() {
	this.bg;
	this.texts = [];
	this.cnsArr;
};

ThreeChooseSprite.prototype = util.inherit(GroupSprite.prototype);
ThreeChooseSprite.prototype.constructor = ThreeChooseSprite;

ThreeChooseSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	// this.grayBackGround = game.add.graphics(0, 0);
	// this.grayBackGround.beginFill(0x000000);
	// this.grayBackGround.drawRect(0,0,game.world.width,game.world.height);
	// // this.grayBackGround.alpha = 0.7;
	// this.grayBackGround.endFill();
	// this.group.add(this.grayBackGround);

	this.bg = game.add.image(0, 0, 'thirteen');
	this.bg.frameName = 'thirteen_result_light_frame';
	this.group.add(this.bg);
	this.bg.context = this;

	for (var i = 0; i < 3; i++) {
		var s = new LineTextSprite(0, 0, 0, '#ffffff');
		s.create();
		this.group.add(s.group);
		this.texts.push(s);
	}

}
ThreeChooseSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	this.height = wh.h;

	this.bg.width = this.width;
	this.bg.height = this.height;
	//	
	// this.grayBackGround .width = this.width;
	// this.grayBackGround .height = this.height;

	this.texts[0].layout({
		w : this.width,
		h : this.height / 3,
		fh : this.height / 3
	});

	this.texts[1].layout({
		w : this.width,
		h : this.height / 3,
		fh : this.height / 3
	});
	this.texts[1].group.y = this.height / 3;

	this.texts[2].layout({
		w : this.width,
		h : this.height / 3,
		fh : this.height / 3
	});
	this.texts[2].group.y = this.height / 3 * 2;

};
ThreeChooseSprite.prototype.setChoose = function(cnsArr) {
	this.cnsArr = cnsArr;
	console.log(cnsArr.length);
	for (var i = 0; i < this.cnsArr.length; i++) {
		console.log(this.cnsArr[i].name + ' fuck');
		this.texts[i].setText(this.cnsArr[i].name);
	}
}
ThreeChooseSprite.prototype.getCardsCns = function() {
	if(this.cnsArr == null){
		return null;
	}
	var retArr = [];
	for (var i = 0; i < this.cnsArr.length; i++) {
		retArr = retArr.concat(this.cnsArr[i].cns);
	}
	return retArr;
}
ThreeChooseSprite.prototype.clear = function() {
	for (var i = 0; i < 3; i++) {
		this.texts[i].setText('');
	}
}
ThreeChooseSprite.test = function() {
	var three = new ThreeChooseSprite();
	three.create();
	three.layout({
		w : 80,
		h : 100
	});
	three.setChoose([ {
		name : "兩都",
		cns:[0,1,2]
	}, {
		name : "對子",
		cns:[3,4]
	}, {
		name : "烏龍",
		cns:[6,7]
	} ]);
	three.group.y = game.world.height / 2;
	console.log(three.getCardsCns());
}

function CandidateSprite(clickHandle) {
	this.bg;
	this.threeChooseArr = [];
	this.clickHandle = clickHandle;
	this.specialArr = [];
	this.specialMap = [];
	
};

CandidateSprite.prototype = util.inherit(GroupSprite.prototype);
CandidateSprite.prototype.constructor = CandidateSprite;

CandidateSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.bg = game.add.graphics(0, 0);
	this.bg.beginFill(0x636363);
	this.bg.drawRect(0, 0, game.world.width, game.world.height);
	this.bg.alpha = 0.9;
	this.bg.endFill();
	this.group.add(this.bg);

	var context = this;

	for (var i = 0; i < 6; i++) {
		var tc = new ThreeChooseSprite();
		tc.create();
		this.threeChooseArr.push(tc);
		this.group.add(tc.group);
		tc.bg.inputEnabled = true;
		tc.bg.input.enableDrag();
	
		tc.bg.events.onDragStop.add(function(sprite) {
			console.log('stop');
			for (var i = 0; i < context.threeChooseArr.length; i++) {
				var three = context.threeChooseArr[i];
				three.oriX = three.group.x;
			}
			context.clickHandle(sprite.context.getCardsCns());
			for (var i = 0; i < context.threeChooseArr.length; i++) {
				var three = context.threeChooseArr[i];
				three.bg.tint = '0xffffff';
			}
			sprite.tint = '0xFF0000';
		});
		tc.bg.events.onDragUpdate.add(function(sprite, pointer, dragX, dragY,
				snapPoint) {
			// console.log('drag update X Y ' + dragX + ' ' + dragY);
			sprite.y = 0;
			sprite.x = 0;
			for (var i = 0; i < context.threeChooseArr.length; i++) {
				var three = context.threeChooseArr[i];
				three.group.x = three.oriX + dragX;
			}
//			sprite.context
			
		});
	}
	
	var sprite;
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_24';
	this.group.add(sprite);
	this.specialMap['至尊青龙'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_23';
	this.group.add(sprite);
	this.specialMap['十二皇族'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_22';
	this.group.add(sprite);
	this.specialMap['全黑'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_21';
	this.group.add(sprite);
	this.specialMap['全红'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_20';
	this.group.add(sprite);
	this.specialMap['三分天下'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_19';
	this.group.add(sprite);
	this.specialMap['四套三条'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_18';
	this.group.add(sprite);
	this.specialMap['一条龙'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_14';
	this.group.add(sprite);
	this.specialMap['三同花顺'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_12';
	this.group.add(sprite);
	this.specialMap['五对三条'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_7';
	this.group.add(sprite);
	this.specialMap['六对半'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_6';
	this.group.add(sprite);
	this.specialMap['全大'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_5';
	this.group.add(sprite);
	this.specialMap['全小'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_2';
	this.group.add(sprite);
	this.specialMap['三顺子'] = sprite;
	this.specialArr.push(sprite);
	
	sprite = game.add.image(0,0,'thirteen_special');
	sprite.frameName = 'thirteen_special_1';
	this.group.add(sprite);
	this.specialMap['三同花'] = sprite;
	this.specialArr.push(sprite);
	
}
CandidateSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	this.height = wh.h;

	this.bg.width = this.width;
	this.bg.height = this.height;

	var width = this.width * 2 / 7;
	var height = this.height / 11 * 10;
	for (var i = 0; i < 6; i++) {
		var tc = this.threeChooseArr[i];
		tc.layout({
			w : width,
			h : height
		});
		tc.group.x = 0.125 * width + i * 1.125 * width;
		tc.group.y = 1 / 20 * height;
		tc.oriX = tc.group.x;
	}
	
	this.specialMap['至尊青龙'].width = game.world.width;
	this.specialMap['至尊青龙'].height = this.height; 
	
	this.specialMap['十二皇族'].width = game.world.width;
	this.specialMap['十二皇族'].height = this.height;
	
	this.specialMap['全黑'].width = game.world.width/2;
	this.specialMap['全黑'].height = this.height;
	
	this.specialMap['全红'].width = game.world.width/2;
	this.specialMap['全红'].height = this.height;
	this.specialMap['全红'].x = game.world.width/2;
	
	this.specialMap['三分天下'].width = game.world.width;
	this.specialMap['三分天下'].height = this.height; 
	
	this.specialMap['四套三条'].width = game.world.width;
	this.specialMap['四套三条'].height = this.height;
	
	this.specialMap['一条龙'].width = game.world.width;
	this.specialMap['一条龙'].height = this.height; 
	
	this.specialMap['三同花顺'].width = game.world.width;
	this.specialMap['三同花顺'].height = this.height;
	
	
	this.specialMap['五对三条'].width = game.world.width;
	this.specialMap['五对三条'].height = this.height;
	
	this.specialMap['六对半'].width = game.world.width;
	this.specialMap['六对半'].height = this.height; 
	
	this.specialMap['全大'].width = game.world.width;
	this.specialMap['全大'].height = this.height;
	
	
	this.specialMap['全小'].width = game.world.width;
	this.specialMap['全小'].height = this.height;
	
	this.specialMap['三顺子'].width = game.world.width;
	this.specialMap['三顺子'].height = this.height; 
	
	this.specialMap['三同花'].width = game.world.width;
	this.specialMap['三同花'].height = this.height;
	
	
	
	
	
	
	

};
CandidateSprite.prototype.setCandidate = function(cnsArr) {

	this.clear();
	if(cnsArr.length >= 6){
		for(var i = 0;i< 6;i++){
			this.threeChooseArr[i].setChoose(cnsArr[i]);
			this.threeChooseArr[i].group.visible = true;
		}
	}else{
		for(var i = 0;i< 6;i++){
			if(i < cnsArr.length){
				this.threeChooseArr[i].setChoose(cnsArr[i]);
				this.threeChooseArr[i].group.visible = true;
			}else{
				this.threeChooseArr[i].group.visible = false;
			}
		}
	}
}


CandidateSprite.prototype.setSpecial= function(name) {
	this.clear();
	if(name == '凑一色'){
		this.specialMap['全黑'].visible = true;
		this.specialMap['全红'].visible = true;
	}else{
		this.specialMap[name].visible = true;
	}
}
CandidateSprite.prototype.clearSpecial = function(){
	for(var i = 0;i< this.specialArr.length;i++){
		this.specialArr[i].visible = false;
	}
}

CandidateSprite.prototype.clear = function() {
	var tc = this.threeChooseArr;
	for (var i = 0; i < tc.length; i++) {
		tc[i].clear();
		tc[i].group.visible = false;
	}
	this.clearSpecial();
	
	for (var i = 0; i < this.threeChooseArr.length; i++) {
		var three = this.threeChooseArr[i];
		three.bg.tint = '0xffffff';
	}
}

// ThreeChooseSprite.prototype.setChoose = function(cnsArr) {
// this.cnsArr = cnsArr;
// console.log(cnsArr.length);
// for(var i = 0;i< this.cnsArr.length;i++){
// console.log(this.cnsArr[i].name+' fuck');
// this.texts[i].setText(this.cnsArr[i].name);
// }
// }

CandidateSprite.test = function() {
	var three = new CandidateSprite(function(cns){console.log(cns);});
	three.create();
	three.layout({
		w : game.world.width,
		h : 100
	});

	var cnsArr = [];
	cnsArr.push([ {
		name : "兩都",
		cns:[0,1,2]
	}, {
		name : "對子",
		cns:[0,1,2]
	}, {
		name : "烏龍",
		cns:[0,1,2]
	} ]);
	cnsArr.push([ {
		name : "兩都",
		cns:[0,1,2]
	}, {
		name : "對子",
		cns:[0,1,2]
	}, {
		name : "烏龍",
		cns:[0,1,2]
	} ]);
	cnsArr.push([ {
		name : "兩都",
		cns:[0,1,2]
	}, {
		name : "對子"
	}, {
		name : "烏龍"
	} ]);
	cnsArr.push([ {
		name : "兩都"
	}, {
		name : "對子"
	}, {
		name : "烏龍"
	} ]);
	cnsArr.push([ {
		name : "兩都"
	}, {
		name : "對子"
	}, {
		name : "烏龍"
	} ]);
	cnsArr.push([ {
		name : "兩都"
	}, {
		name : "對子"
	}, {
		name : "烏龍"
	} ]);
	cnsArr.push([ {
		name : "兩都"
	}, {
		name : "對子"
	}, {
		name : "烏龍"
	} ]);
	
	three.setCandidate(cnsArr);
//	three.clear();
	
	three.group.y = game.world.height / 2;
	three.setSpecial('至尊青龙');
	three.setCandidate(cnsArr);
	
	
	
	// three.clear();
	// three.setChoose([{name:"兩都"},{name:"對子"},{name:"烏龍"}]);
}