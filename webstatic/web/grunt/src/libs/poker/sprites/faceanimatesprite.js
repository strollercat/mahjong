function FaceAnimateSprite(width){
	this.width = width;
	this.height = width;
	
	this.as = {};
	this.sprite;

	this.lastTimer;	
}




FaceAnimateSprite.prototype = util.inherit(GroupSprite.prototype);
FaceAnimateSprite.prototype.constructor = FaceAnimateSprite;



FaceAnimateSprite.prototype.layout = function(wh){
	this.width = wh.w;
	this.height = this.width;
	
	
	this.as['angry'] = {w:this.width,h:this.width,s:10};
	
	this.as['fanu'] = {w:this.width,h:this.width,s:10};
	
	this.as['han'] = {w:this.width,h:this.width,s:1};
	
	this.as['happy'] = {w:this.width,h:this.width,s:10};
	
	this.as['huaixiao'] = {w:this.width,h:this.width,s:10};
	
	this.as['jiong'] = {w:this.width,h:this.width,s:10};
	
	this.as['lihai'] = {w:this.width,h:this.width,s:10};
	
	this.as['se'] = {w:this.width,h:this.width,s:10};
	
	this.as['shaoxiang'] = {w:this.width,h:this.width,s:10};
	
	this.as['shihua'] = {w:this.width,h:this.width,s:10};
	
	this.as['sleep'] = {w:this.width,h:this.width,s:8};
	
	this.as['smile'] = {w:this.width,h:this.width,s:8};
	
	this.as['touxiang'] = {w:this.width,h:this.width,s:8};
	
	this.as['yun'] = {w:this.width,h:this.width,s:8};
	
	this.as['zhiya'] = {w:this.width,h:this.width,s:8};
	
	
}

FaceAnimateSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.sprite  = game.add.image(0,0,'chat.showfaces');
	this.sprite.visible = false;
	this.group.add(this.sprite);
	
	this.sprite.animations.add('angry',['angry0','angry1','angry2','angry3','angry4','angry5','angry6','angry7']);
	
	this.sprite.animations.add('fanu',['fennu0','fennu1','fennu2']);
	
	this.sprite.animations.add('han',['han0','han1']);
	
	this.sprite.animations.add('happy',['happy0','happy1']);
	
	this.sprite.animations.add('huaixiao',['huaixiao0','huaixiao1','huaixiao2','huaixiao3','huaixiao4','huaixiao5','huaixiao6','huaixiao7','huaixiao8']);
	
	this.sprite.animations.add('jiong',['jiong0','jiong1','jiong2','jiong3','jiong4','jiong5']);
	
	this.sprite.animations.add('lihai',['lihai0','lihai1','lihai2','lihai3','lihai4','lihai5','lihai6','lihai7','lihai8','lihai9','lihai10','lihai11','lihai12']);
	
	this.sprite.animations.add('se',['se0','se1','se2','se3']);
	
	this.sprite.animations.add('shaoxiang',['shaoxiang0','shaoxiang1','shaoxiang2','shaoxiang3','shaoxiang4']);
	
	this.sprite.animations.add('shihua',['shihua0','shihua1','shihua2','shihua3','shihua4','shihua5']);
	
	this.sprite.animations.add('sleep',['sleep0','sleep1','sleep2','sleep3','sleep4','sleep5','sleep6','sleep7','sleep8']);
	
	this.sprite.animations.add('smile',['smaile0','smaile1','smaile2','smaile3','smaile4','smaile5','smaile6','smaile7','smaile8']);
	
	this.sprite.animations.add('touxiang',['touxiang0','touxiang1','touxiang2','touxiang3']);
	
	this.sprite.animations.add('yun',['yun0','yun1','yun2','yun3']);
	
	this.sprite.animations.add('zhiya',['zhiya0','zhiya1']);
	
}
FaceAnimateSprite.prototype.play = function(name,sec){
	if(!this.as[name]){
		return ;
	}
	this.sprite.visible = true;
	var wh = this.as[name];
	this.sprite.width = wh.w;
	this.sprite.height = wh.h;
	this.sprite.animations.play(name,wh.s,true);
	
	if(this.lastTimer){
		game.time.events.remove(this.lastTimer);
	}
	this.lastTimer = game.time.events.add(Phaser.Timer.SECOND * sec, function(){
		console.log();
		this.sprite.animations.stop(name,false);
		this.sprite.visible = false;
	}, this);
	
	
	
};
FaceAnimateSprite.test = function(){
	var animateSprite = new FaceAnimateSprite(0);
	animateSprite.create();
	animateSprite.layout({w:100});
	animateSprite.play('zhiya',3);
};