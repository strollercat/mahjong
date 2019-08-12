function ActionButtonSprite(name, btnCallBack, width) {
	this.name = name;
	this.btnCallBack = btnCallBack;
	this.width = width;

	this.btn;
}
ActionButtonSprite.prototype = util.inherit(GroupSprite.prototype);
ActionButtonSprite.prototype.constructor = ActionButtonSprite;

ActionButtonSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	var width = this.width, height = width;
	this.btn.width = width;
	this.btn.height = height;
}



ActionButtonSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	var btnCallBack = this.btnCallBack;
	if (this.name == "chi") {
		this.btn = game.add.button(0, 0, 'actioninputs', btnCallBack, this,
				'youxizhong-2_59', 'youxizhong-2_59', 'youxizhong-2_09');
	} else if (this.name == 'peng') {
		this.btn = game.add.button(0, 0, 'actioninputs', btnCallBack, this,
				'youxizhong-2_57', 'youxizhong-2_57', 'youxizhong-2_07');
	} else if (this.name == "gang") {
		this.btn = game.add.button(0, 0, 'actioninputs', btnCallBack, this,
				'youxizhong-2_55', 'youxizhong-2_55', 'youxizhong-2_05');
	} else if (this.name == 'hu') {
		this.btn = game.add.button(0, 0, 'actioninputs', btnCallBack, this, 'youxizhong-2_53',
				'youxizhong-2_53', 'youxizhong-2_03');
	} else if (this.name == 'guo') {
		this.btn = game.add.button(0, 0, 'actioninputs', btnCallBack, this,
				'youxizhong-2_61', 'youxizhong-2_61', 'youxizhong-2_11');
	} else if (this.name == 'gen') {
		this.btn = game.add.button(0, 0, 'actioninputs', btnCallBack, this,
				'z_play_normal', 'z_play_normal', 'z_play_press');
	} else if (this.name == 'bugen') {
		this.btn = game.add.button(0, 0, 'actioninputs', btnCallBack, this,
				'z_no_play_normal', 'z_no_play_normal', 'z_no_play_press');
	}
	this.btn.btn = this;
	this.group.add(this.btn);
}

function ActionButtonsSprite(btnWidth, cardWidth, commandHandler) {
	this.btnWidth = btnWidth;
	this.cardWidth = cardWidth;
	this.commandHandler = commandHandler;

	this.btns;
	this.btnsArr;
	this.cmcsArr;
}
ActionButtonsSprite.prototype = util.inherit(GroupSprite.prototype);
ActionButtonsSprite.prototype.constructor = ActionButtonsSprite;



ActionButtonsSprite.prototype.layout = function(wh) {
	this.btnWidth = wh.bw;
	this.cardWidth = wh.cw;

	for (var i = 0; i < this.btnsArr.length; i++) {
		this.btnsArr[i].layout({w:this.btnWidth});
	}
}



ActionButtonsSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);

	this.btns = {};
	this.btnsArr = [];

	this.btns['chi'] = new ActionButtonSprite('chi', this.btnCallBack,
			0);
	this.btns['peng'] = new ActionButtonSprite('peng', this.btnCallBack,
			0);
	this.btns['hu'] = new ActionButtonSprite('hu', this.btnCallBack,
			0);
	this.btns['gang'] = new ActionButtonSprite('gang', this.btnCallBack,
			0);
	this.btns['guo'] = new ActionButtonSprite('guo', this.btnCallBack,
			0);
	this.btns['gen'] = new ActionButtonSprite('gen', this.btnCallBack,
			0);
	this.btns['bugen'] = new ActionButtonSprite('bugen', this.btnCallBack,
			0);

	this.btnsArr.push(this.btns['chi']);
	this.btnsArr.push(this.btns['peng']);
	this.btnsArr.push(this.btns['hu']);
	this.btnsArr.push(this.btns['gang']);
	this.btnsArr.push(this.btns['guo']);
	this.btnsArr.push(this.btns['gen']);
	this.btnsArr.push(this.btns['bugen']);

	for (var i = 0; i < this.btnsArr.length; i++) {
		this.btnsArr[i].create();
		var group = this.btnsArr[i].group;
		group.visible = false;
		this.group.add(group);
		this.btnsArr[i].btns = this;
	}
}
ActionButtonsSprite.prototype.hideBtn = function(btnName){
	if(this.btns[btnName]){
		this.btns[btnName].group.visible = false;
	}
}
ActionButtonsSprite.prototype.show = function(btnName, cns) {
	var btn, i, index = 6;
	if (btnName == 'gen' || btnName == 'bugen') {
		for (i = 0; i < this.btnsArr.length; i++) {
			btn = this.btnsArr[i];
			if (btn.name == 'gen' || btn.name == 'bugen') {
				btn.group.visible = true;
			} else {
				btn.group.visible = false;
			}
		}
	} else {
		this.btns['gen'].group.visible = false;
		this.btns['bugen'].group.visible = false;
		this.btns['guo'].group.visible = true;
		this.btns[btnName].group.visible = true;
		if (btnName == 'chi' || btnName == 'gang') {
			this.btns[btnName].cns = cns;
		}
	}
	for (i = this.btnsArr.length - 1; i >= 0; i--) {
		btn = this.btnsArr[i];
		if (btn.group.visible) {
			btn.group.x = index * this.btnWidth;
			index -= 1.5;
		}
	}
}
ActionButtonsSprite.prototype.clear = function() {
	for (var i = 0; i < this.btnsArr.length; i++) {
		this.btnsArr[i].group.visible = false;
		this.btnsArr[i].cns = null;

	}
	if (this.cmcsArr && this.cmcsArr.length >0 ) {
//		console.log(this.cmcsArr.length);
		for(var i = 0;i<this.cmcsArr.length;i++){
			this.group.remove(this.cmcsArr[i].group);
			this.cmcsArr[i].group.destroy();
		}
		this.cmcsArr = null;
	}
}
ActionButtonsSprite.prototype.chooseCards = function(btn) {
//	console.log('chooseCards');
	var i, tmpcns, cmcs, cns = btn.cns, len = cns.length, name = btn.name, middleName, btns = btn.btns;


	if (btns.cmcsArr && btns.cmcsArr.length >0 ) {
//		console.log(this.cmcsArr.length);
		for(var i = 0;i<btns.cmcsArr.length;i++){
			btns.group.remove(btns.cmcsArr[i].group);
			btns.cmcsArr[i].group.destroy();
		}
		btns.cmcsArr = null;
	}


	if (name == 'gang') {
		middleName = 'angang';
	} else {
		middleName = 'chi';
	}
	
	btns.cmcsArr = [] ;
	
	for (i = 0; i < len; i++) {
		tmpcns = cns[i];
		btns.cmcsArr[i] = new CardsMiddleSprite(0, middleName, tmpcns, btns.cardWidth, 3,
				tmpcns[0]);
		btns.cmcsArr[i].create();
		btns.cmcsArr[i].layout({w:btns.cardWidth});
		btns.cmcsArr[i].group.x = btn.group.x + btn.group.width / 2 - btns.cmcsArr[i].group.width / 2;
		btns.cmcsArr[i].group.y = btn.group.y + (i - len) * btns.cmcsArr[i].ch;
		btns.cmcsArr[i].click(function(kind, cns) {
			btns.commandHandler.listen(kind, cns);
	//		btns.clear();
		});
		btns.group.add(btns.cmcsArr[i].group);
	}
	game.world.bringToTop(btns.group);
}
ActionButtonsSprite.prototype.btnCallBack = function(btn) {
	if(game.audioManager){
		game.audioManager.playByKey('click');
	}
	var context = btn.btn.btns;
	if (!context.commandHandler) {
		return;
	}
	var btnName = btn.btn.name;
	if (btnName == 'peng' || btnName == 'hu' || btnName == 'guo'
			|| btnName == 'gen' || btnName == 'bugen') {
		context.commandHandler.listen(btnName, null);
	} else {
		if (!(btn.btn.cns instanceof Array)) {
			context.commandHandler.listen(btnName, btn.btn.cns);
		} else if(btn.btn.cns.length == 1){
			var tmpCns = [];
			tmpCns[0] = btn.btn.cns[0][1];
			tmpCns[1] = btn.btn.cns[0][2];
			context.commandHandler.listen(btnName, tmpCns);
		}else {
			context.chooseCards(btn.btn);
		}
	}
}

ActionButtonsSprite.test = function() {
	// var actionBtnSprite = new ActionButtonSprite('peng',function(btn){
	// console.log(btn.btn.name);
	// },game.config.authorBoxCw,)
	// actionBtnSprite.create();

	var actionbtnssprite = new ActionButtonsSprite(0,0, new CommandHandler());
	actionbtnssprite.create();
	actionbtnssprite.layout({bw:game.world.width/17,cw:game.world.width/17/5 *3});
	actionbtnssprite.group.y = game.world.height / 2;

//	actionbtnssprite.show('gen',null);
//	actionbtnssprite.show('bugen',null);

	actionbtnssprite.show('chi', [ [ 0, 4, 8 ], [ 4, 8, 12 ], [ 8, 12, 16 ],
			[ 8, 12, 16 ] ]);
	actionbtnssprite.show('peng', null);
    actionbtnssprite.show('gang', [ [ 0, 1, 2, 3 ], [ 4, 5, 6, 7 ],
	 [ 8, 9, 10, 11 ], [ 12, 13, 14, 15 ] ]);
	// actionbtnssprite.show('gang', 0);
	actionbtnssprite.show('hu', null);
	//
	// actionbtnssprite.group.y = game.world.height / 2;
	// actionbtnssprite.group.x = game.world.width / 4;

	// actionbtnssprite.hideAll();
}
