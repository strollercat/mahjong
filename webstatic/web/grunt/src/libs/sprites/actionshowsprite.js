function ActionShowSprite(name, width, stopHandler) {
	this.name = name;
	this.sprite;
	this.width = width;
	this.stopHandler = stopHandler;
}
ActionShowSprite.prototype = util.inherit(GroupSprite.prototype);
ActionShowSprite.prototype.constructor = ActionShowSprite;



ActionShowSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	this.sprite.width = this.width;
	this.sprite.height = this.width;
}

ActionShowSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	var sprite;
	if (this.name == 'hua') {
		sprite = game.add.image(0,0,'actionshows');
		sprite.frameName = 't_hua';
	} else if (this.name == 'hu') {
		sprite = game.add.image(0,0,'actionshows');
		sprite.frameName = 't_hu';
	} else if (this.name == 'gang') {
		sprite = game.add.image(0,0,'actionshows');
		sprite.frameName = 't_gang';
	} else if (this.name == 'peng') {
		sprite = game.add.image(0,0,'actionshows');
		sprite.frameName = 't_peng';
	} else if (this.name == 'chi') {
		sprite = game.add.image(0,0,'actionshows');
		sprite.frameName = 't_chi';
	}
	this.sprite = sprite;
	this.group.add(sprite);
	this.group.visible = false;
}

ActionShowSprite.prototype.show = function(dir) {
//	console.log('show '+dir);
	var property  = this.calTweenProperty(dir);
	
	this.group.visible = true;
	this.group.x = property.x;
	this.group.y = property.y;

	var tween = game.add.tween(this.group.getChildAt(0));
	tween.to({x:0,y:0}, 800, Phaser.Easing.Linear.None, false,
			0, 0, false);
	tween.onComplete.add(function(tween) {
		this.group.visible = false;
		if(this.stopHandler){
			this.stopHandler(this);
		}
	}, this);
	tween.start();
}
ActionShowSprite.prototype.calTweenProperty = function(dir) {
	var retObj = {};
	
	var authorBoxCw = game.world.width / 17;
	var authorBoxCh = authorBoxCw * 1.5;
	var otherBoxCw = authorBoxCw / 5 * 3;
	var otherBoxCh = 1.5 * otherBoxCw;
	if (dir == 0) {
		retObj.x = game.world.width / 2 - authorBoxCw / 2;
		retObj.y = game.world.height - 1.6 *authorBoxCh;
	} else if (dir == 1) {
		retObj.x = game.world.width - 3 * otherBoxCh;
		retObj.y = game.world.height / 2 - authorBoxCw / 2;
	} else if (dir == 2) {
		retObj.x = game.world.width / 2 - authorBoxCw / 2;
		retObj.y = 1.3 * otherBoxCw;
	} else if (dir == 3) {
		retObj.x = 1.5 * authorBoxCw;
		retObj.y = game.world.height / 2 - authorBoxCw / 2;
	}
	return retObj;
};

function ActionShowsSprite(width){
	this.width = width;
	
	this.shows;
	this.completeHandler;
	
}

ActionShowsSprite.complete = function(ass){
//	console.log(ass.name +" done");
	ass.asss.completeHandler();
}

ActionShowsSprite.prototype = util.inherit(GroupSprite.prototype);
ActionShowsSprite.prototype.constructor = ActionShowsSprite;

ActionShowsSprite.prototype.layout = function(wh){
	this.shows['hua'].layout(wh);
	this.shows['hu'].layout(wh);
	this.shows['peng'].layout(wh);
	this.shows['gang'].layout(wh);
	this.shows['chi'].layout(wh);
}

ActionShowsSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.shows = {};
	
	var ass ;
	
	ass= new ActionShowSprite('hua',this.width,ActionShowsSprite.complete);
	ass.create();
	ass.asss = this;
	this.shows['hua']  = ass;
	this.group.add(ass.group);
	
	ass= new ActionShowSprite('hu',this.width,ActionShowsSprite.complete);
	ass.create();
	ass.asss = this;
	this.shows['hu']  = ass;
	this.group.add(ass.group);
	
	ass= new ActionShowSprite('peng',this.width,ActionShowsSprite.complete);
	ass.create();
	ass.asss = this;
	this.shows['peng']  = ass;
	this.group.add(ass.group);
	
	ass= new ActionShowSprite('gang',this.width,ActionShowsSprite.complete);
	ass.create();
	ass.asss = this;
	this.shows['gang']  = ass;
	this.group.add(ass.group);
	
	ass= new ActionShowSprite('chi',this.width,ActionShowsSprite.complete);
	ass.create();
	ass.asss = this;
	this.shows['chi']  = ass;
	this.group.add(ass.group);
	
	
	
}
ActionShowsSprite.prototype.show = function(name,dir,completeHandler){
	this.completeHandler = completeHandler;
	this.shows[name].show(dir);
	if(game.audioManager){
		game.audioManager.playByKey('mj.'+name);
	}
}
ActionShowsSprite.test = function(){
	
	var ass =new ActionShowsSprite(0);
	ass.create();
	ass.layout({w:game.world.width/17});
	ass.show('gang',3,function(){});
}
