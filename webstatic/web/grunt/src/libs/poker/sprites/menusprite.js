function SubMenuSprite(text,callBack){
	this.text = text;
	this.callBack = callBack;
}
SubMenuSprite.prototype = util.inherit(GroupSprite.prototype);
SubMenuSprite.prototype.constructor = SubMenuSprite;

SubMenuSprite.prototype.layout = function(wh){
	var height = wh.h;
	
//	this.imageS.width = height;
//	this.imageS.height= height;
	
	var fh = height/3*2;
	this.textS.layout({w:fh,h:fh,fh:fh});
	this.textS.setTextToLeft(this.textS.getText());
	this.textS.group.x = height/2;
	this.textS.group.y = (height - fh)/2;
}

SubMenuSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
//	this.imageS = game.add.image(0,0,'dismissroom');
//	this.imageS.frameName = this.image;
//	this.imageS.tint = 0xFFFFFF;
//	this.group.add(this.imageS);
//	this.imageS.visible = false;
	
	this.textS = new LineTextSprite(0,0,0,'#ffffff');
	this.textS.create();
	var text = this.textS.textSprite;
	text.font = 'Arial Black';
	text.fontWeight = 'bold';
//	text.setShadow(5, 5, 'rgba(0,0,0,0.5)', 10);
	text.stroke = '#000000';
	text.strokeThickness = 3;
	this.textS.setTextToLeft(this.text);
	this.textS.textSprite.inputEnabled = true;
	this.textS.textSprite.events.onInputDown.add(this.callBack, this);
	this.group.add(this.textS.group);
}
SubMenuSprite.test = function(){
	var s = new SubMenuSprite('icon_standUp','邀请',null);
	s.create();
	s.layout({w:40,h:40});
	s.group.x = game.world.width/2;
	s.group.y = game.world.height/2;
}


function MenuSprite(callBack){
	this.callBack = callBack;
	this.subMenus = [];
	this.wh ;
}
MenuSprite.prototype = util.inherit(GroupSprite.prototype);
MenuSprite.prototype.constructor = MenuSprite;

MenuSprite.prototype.layout = function(wh){
	this.wh =  wh;
	var width = wh.w;
	
	this.menuBtn.layout(wh);
	
	this.subMenuGroup.y = width;
	
	
	
	var btnWidth = 1.5 * wh.w;
	var btnWh = {w:btnWidth,h:btnWidth}
	var index = 0;
	for(var i = 0;i< this.subMenus.length;i++){
		var subMenu = this.subMenus[i];
		if(subMenu.group.visible){
			subMenu.layout(btnWh);
			subMenu.group.y = btnWidth * index;
			index += 1;
		}
	}

	this.bgSprite.width = 4 * width;
	this.bgSprite.height = 1.5 * index * width;
}

MenuSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.menuBtn = new ButtonSprite(0,0,'shezhi_normal','shezhi_press',function(btn){
		btn.context.subMenuGroup.visible = !btn.context.subMenuGroup.visible;
	},this);
	this.menuBtn.create();
	this.group.add(this.menuBtn.group);
	
	
	
	this.subMenuGroup = game.add.group();
	this.group.add(this.subMenuGroup);
	
	this.bgSprite = game.add.image(0,0,'other');
	this.bgSprite.frameName='yellowBg';
	this.bgSprite.alpha = 0.2;
	this.subMenuGroup.add(this.bgSprite);
	
	this.inviteS = new SubMenuSprite('邀请好友',this.callBack);
	this.inviteS.create();
	this.subMenuGroup.add(this.inviteS.group);
	this.subMenus.push(this.inviteS);
	
	
	this.gameRuleS = new SubMenuSprite('游戏规则',this.callBack);
	this.gameRuleS.create();
	this.subMenuGroup.add(this.gameRuleS.group);
	this.subMenus.push(this.gameRuleS);
	
	this.leaveS = new SubMenuSprite('返回大厅',this.callBack);
	this.leaveS.create();
	this.subMenuGroup.add(this.leaveS.group);
	this.subMenus.push(this.leaveS);
	
	this.dismissS = new SubMenuSprite('解散房间',this.callBack);
	this.dismissS.create();
	this.subMenuGroup.add(this.dismissS.group);
	this.subMenus.push(this.dismissS);
	
	
}

MenuSprite.prototype.hide = function(number){
	if(number <0 || number >= this.subMenus.length){
		return ;
	}
	this.subMenus[number].group.visible = false;
	this.layout(this.wh);
}
MenuSprite.prototype.showAll = function(){
	for(var i = 0;i< this.subMenus.length;i++){
		this.subMenus[i].group.visible = true;
	}
	this.layout(this.wh);
}
MenuSprite.test = function(){
	var menu = new MenuSprite(function(textSprite){console.log(this.textS.getText())});
	menu.create();
	menu.layout({w:40,h:40});
	menu.group.x = 20;
}
