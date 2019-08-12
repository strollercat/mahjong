function UserInfoSprite(width,height,name,url,win,lose,ping,score,money,dir){
	this.width = width;
	this.height = height;
	
	//这两个是绝对坐标
	this.dir = dir;
	this.authorDir;
	this.commandHandler;
	
	this.name = name;
	this.url = url;
	
	this.win = win;
	this.lose = lose;
	this.ping = ping;
	this.score = score;
	this.money = money;
	
	
	this.bgSprite; 
	this.userSprite;
	
	this.nameLts;
	this.juLts;
	this.winLts;
	this.fontGroup;
	
	this.tlts;
	this.moneySprite;
	this.moneyGroup;
	
	this.bulletGroup;
	this.bulletSpriteArr;
	

	
	
}



UserInfoSprite.prototype = util.inherit(GroupSprite.prototype);
UserInfoSprite.prototype.constructor = UserInfoSprite;


UserInfoSprite.prototype.layout = function(wh) {
	this.width = wh.w;
	
	this.bgSprite.layout({w:this.width,h:0});
	this.height = this.bgSprite.height;
	
	
	var usWidth = this.height/4;
	this.userSprite.layout({w:usWidth});
	this.userSprite.group.x = usWidth;
	this.userSprite.group.y = 0.6 *usWidth;
	
	var moneyHeight = usWidth/4;
	this.tlts.layout({w:1.5 * usWidth});
	this.moneySprite.width = 1.4*moneyHeight;
	this.moneySprite.height= 1.4* moneyHeight;
	this.moneySprite.x = -0.2 * this.moneySprite.width;
	this.moneyGroup.x = usWidth + (1.2 * this.moneySprite.width - usWidth)/2;
	this.moneyGroup.y = 1.6* usWidth;
	
	var fontHeight = usWidth/3;
	var lts ; 
	
	this.nameLts.layout({w:usWidth,h:fontHeight,fh:fontHeight});
	this.nameLts.setTextToLeft(this.name);
	
	this.juLts.layout({w:usWidth,h:fontHeight,fh:fontHeight});
	var totalJushu = this.win + this.lose + this.ping;
	var ratio;
	if(totalJushu == 0 ){
		ratio = 0 + '%';
	}else{
		ratio = parseInt(this.win/totalJushu * 100) + '%'; 
	}
	this.juLts.setTextToLeft('局数:'+totalJushu+ ' 胜率:'+ratio);
	this.juLts.group.y = 1 *fontHeight;
	
	this.winLts.layout({w:usWidth,h:fontHeight,fh:fontHeight});
	this.winLts.setTextToLeft('胜负: '+this.win+'胜 '+this.lose+'负 '+this.ping+'平');
	this.winLts.group.y = 2 *fontHeight;
	
	this.fontGroup.x = 2.4 * usWidth;
	this.fontGroup.y = 0.6 *usWidth + this.moneySprite.height/2;
	
	
	var unitWidth = this.width/5;
	var bulletWidth = unitWidth/2;
	var bullet;
	for(var i = 0;i < 10;i++){
		bullet = this.bulletSpriteArr[i];
		if(i == 6){
			bullet.width = bulletWidth;
			bullet.height = bulletWidth / 76 * 24;
			bullet.x = (i % 5)* unitWidth + (unitWidth - bulletWidth)/2;
			bullet.y = 1* bulletWidth + 0.3 * bulletWidth + (bullet.width - bullet.height)/2;
		}else{
			bullet.width = bulletWidth;
			bullet.height = bulletWidth;
			bullet.x = (i % 5)* unitWidth + (unitWidth - bulletWidth)/2;
			if(i >=5){
				bullet.y = 1* bulletWidth + 0.3 * bulletWidth;
			}
			if(i == 2){
				bullet.visible = false;
			}
			if(i == 0 || i ==1 ){
				bullet.x += (unitWidth - bulletWidth);
			}else if( i == 3 || i ==4){
				bullet.x -= (unitWidth - bulletWidth);
			}
		}
		
	}
	this.bulletGroup.y = 1.6 * usWidth + this.moneySprite.height + 0.2 * bulletWidth;
	
}







UserInfoSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.bgSprite = new MessageBoxSprite(0,0,function(btn){
		btn.context.group.visible = false;
	},this);
	this.bgSprite.create();
	this.bgSprite.setTop('home');
	this.bgSprite.btnConfirm.group.visible = false;
	this.group.add(this.bgSprite.group);
	
	
	this.userSprite = new HeaderUserSprite(0);
	this.userSprite.create();
	this.userSprite.changeHeadUrl(this.url);
	this.userSprite.ready(false);
	this.userSprite.zuan(false);
	this.userSprite.fangZhu(false);
	this.group.add(this.userSprite.group);
	
	this.fontGroup = game.add.group();
	
	this.nameLts = new  LineTextSprite(0,0,0,'#ffffff');
	this.nameLts.create();
	this.nameLts.setTextToLeft(this.name);
	this.fontGroup.add(this.nameLts.group);
	
	this.juLts = new  LineTextSprite(0,0,0,'#ffffff');
	this.juLts.create();
	var totalJushu = this.win + this.lose + this.ping;
	var ratio;
	if(totalJushu == 0 ){
		ratio = 0 + '%';
	}else{
		ratio = parseInt(this.win/totalJushu * 100) + '%'; 
	}
	this.juLts.setTextToLeft('局数:'+totalJushu+ ' 胜率:'+ratio);
	this.fontGroup.add(this.juLts.group);
	
	this.winLts = new  LineTextSprite(0,0,0,'#ffffff');
	this.winLts.create();
	this.winLts.setTextToLeft('胜负: '+this.win+'胜 '+this.lose+'负 '+this.ping+'平');
	this.fontGroup.add(this.winLts.group);
	
	this.group.add(this.fontGroup);
	
	this.moneyGroup = game.add.group();
	this.tlts = new TwoLineTextSprite(0,'#ffffff','#ffffff');
	this.tlts.create();
	this.tlts.setText1(this.money);
	this.tlts.text2Sprite.group.visible = false;
	this.tlts.box2.visible = false;
	this.moneyGroup.add(this.tlts.group);
	this.moneySprite = game.add.image(0,0,'other');
	this.moneySprite.frameName = 'charge_diamond';
	this.moneyGroup.add(this.moneySprite);
	this.group.add(this.moneyGroup);
	
	this.bulletGroup = game.add.group();
	this.group.add(this.bulletGroup);
	this.bulletSpriteArr = [];
	var bullet;
	for(var i = 1;i <= 10;i++){
		bullet  = game.add.image(0,0,'animationItem');
		bullet.frameName = 'item'+i+'_1.png';
		
		bullet.index = i-1;
		bullet.inputEnabled = true;
		bullet.events.onInputUp.add(function(sprite){
			console.log('authorDir['+this.authorDir+']dir['+this.dir+']index['+sprite.index+']');
			if(this.authorDir == this.dir){
				return;
			}
			this.commandHandler.listen('bullet',[this.authorDir,this.dir,sprite.index]);
		}, this);
		
		
		this.bulletSpriteArr.push(bullet);
		this.bulletGroup.add(bullet);
	}	
}


UserInfoSprite.test = function(){
	var uis = new UserInfoSprite(400,0,'流浪猫','http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqmibYgzhSR2S3X916YSdxKaW7fLLp4T0NGicovpXWdicLBTWNsYcQV0mMUmj8bFMzuJXEQ8O3VZZaQg/0',100,20,80,300,450,0);
	uis.create();
	uis.layout({w:400,h:0});
	uis.group.x = 100;
	uis.group.y = 100;
}