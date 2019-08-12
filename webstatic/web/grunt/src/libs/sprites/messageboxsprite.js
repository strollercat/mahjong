function MessageBoxSprite(width,height,closeHandler,context){
	this.width = width;
	this.height = height;
	this.closeHandler = closeHandler;
	this.context = context;
	
	this.background;
	this.topHome;
	this.topHint;
	
	this.btnConfirm;
	this.btnClose;
}

MessageBoxSprite.prototype = util.inherit(GroupSprite.prototype);
MessageBoxSprite.prototype.constructor = MessageBoxSprite;


MessageBoxSprite.prototype.layout = function(wh){
	this.width=wh.w;
	this.height = wh.h == 0?this.width/794 * 515:wh.h;
	
	this.background.width = this.width;
	this.background.height = this.height;
	
	this.btnClose.layout({w:this.background.width/8,h:this.background.width/8});
	this.btnClose.group.x = 0.9 * this.background.width;
	this.btnClose.group.y = -0.02 * this.background.height;
	
	var btnWidth = this.width/3;
	var btnHeight = btnWidth/275 * 108;
	this.btnConfirm.layout({w:btnWidth,h:btnHeight});
	this.btnConfirm.group.x = this.width/2 - btnWidth/2;
	this.btnConfirm.group.y = this.height -1.6 * btnHeight;
	
	this.topHome.width = this.width/2;
	this.topHome.height = this.topHome.width/443 * 103;
	this.topHome.x = this.width/2 - this.topHome.width/2;
	this.topHome.y = -10 / 103 * this.topHome.height;
	
	this.topHint.width = this.width/2;
	this.topHint.height = this.topHint.width/443 * 103;
	this.topHint.x = this.width/2 - this.topHint.width/2;
	this.topHint.y = -10 / 103 * this.topHint.height;
};
MessageBoxSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.background = game.add.image(0,0,'messagebox');
	this.background.frameName = 'background';
	this.group.add(this.background);
	
	this.btnClose = new ButtonSprite(0,0,'btn_x_normal','btn_x_press',this.closeHandler,this.context);
	this.btnClose.create();
	this.group.add(this.btnClose.group);
	
	this.btnConfirm = new ButtonSprite(0,0,'confirm_normal','confirm_press',this.closeHandler,this.context);
	this.btnConfirm.create();
	this.group.add(this.btnConfirm.group);

	this.topHome =  game.add.image(0,0,'messagebox');
	this.topHome.frameName = 'home';
	this.group.add(this.topHome);
	
	this.topHint =  game.add.image(0,0,'messagebox');
	this.topHint.frameName = 'hint';
	this.group.add(this.topHint);
}

MessageBoxSprite.prototype.setTop = function(top){
	if(top == 'home'){
		this.topHome.visible = true;
		this.topHint.visible = false;
	}else if(top == 'hint'){
		this.topHint.visible = true;
		this.topHome.visible = false;
	}else{
		this.topHint.visible = false;
		this.topHome.visible = false;
	}
};
MessageBoxSprite.prototype.showConfirmBtn = function(confirm){
	this.btnConfirm.group.visible = confirm;
}

MessageBoxSprite.prototype.show = function(){
	this.group.visible = true;
}

MessageBoxSprite.test = function(){
	var mbs = new MessageBoxSprite(0,0,function(){
		console.log('click');
	},null);
	mbs.create();
	mbs.layout({w:200,h:0});
	mbs.setTop('hint');
	mbs.showConfirmBtn(true);
	mbs.group.y = 100;
	mbs.group.x = 100;
	
	setTimeout(function(){mbs.layout({w:400,h:0});},2000)
}





/**
 * btn1Info {image:***,image1:**,image2:**,callBack:**}
 */
function MessageBox1Sprite(btn1Info,btn2Info,textArr,closeCallBack){
	this.btn1Info = btn1Info;
	this.btn2Info = btn2Info;
	this.textArr = textArr;
	this.closeCallBack = closeCallBack;
}
MessageBox1Sprite.prototype = util.inherit(GroupSprite.prototype);
MessageBox1Sprite.prototype.constructor = MessageBox1Sprite;


MessageBox1Sprite.prototype.setMessage = function(msg){
	if(!msg){
		return ;
	}
	this.textArr = msg;
	if(msg.length == this.ltsArr.length){
		for(var i = 0;i<this.textArr.length;i++){
			this.ltsArr[i].setText(msg[i]);
		}	
	}else if(msg.length > this.ltsArr.length){
		for(var i = this.ltsArr.length;i < msg.length;i++){
			this.ltsArr[i] = new LineTextSprite(0,0,0,'#ffffff');
			this.ltsArr[i].create();
			this.group.add(this.ltsArr[i].group);
		}
		for(var i = 0;i< msg.length;i++){
			this.ltsArr[i].setText(msg[i]);
		}
		this.layoutTextMessage();
	}else{
		for(var i = msg.length;i< this.ltsArr.length;i++){
			this.group.remove(this.ltsArr[i].group);
			this.ltsArr[i].group.destroy();
			this.ltsArr[i] = null;
		}
		this.ltsArr.length = msg.length;
		for(var i = 0;i<this.textArr.length;i++){
			this.ltsArr[i].setText(msg[i]);
		}
		this.layoutTextMessage();
	}
}
MessageBox1Sprite.prototype.layoutTextMessage = function(){
	var textWidth = this.width;
	var textHeight = this.height/7;
	var totalHeight = this.textArr.length * textHeight;
	var offy = this.height /2 -totalHeight/2 -0.5* textHeight;
	for(var i = 0;i<this.textArr.length;i++){
		this.ltsArr[i].layout({w:textWidth,h:textHeight,fh:textHeight});
		this.ltsArr[i].setText(this.textArr[i]);
		this.ltsArr[i].group.y = offy + i*textHeight;
	}	
}
MessageBox1Sprite.prototype.layout = function(wh){
	this.width=wh.w;
	this.height = (wh.h == 0?this.width/794 * 515:wh.h);
	
	this.background.width = this.width;
	this.background.height = this.height;
	
	this.topHint.width = this.width/2;
	this.topHint.height = this.topHint.width/443 * 103;
	this.topHint.x = this.width/2 - this.topHint.width/2;
	this.topHint.y = -10 / 103 * this.topHint.height;
	
	this.btnClose.layout({w:this.background.width/8,h:this.background.width/8});
	this.btnClose.group.x = 0.9 * this.background.width;
	this.btnClose.group.y = -0.02 * this.background.height;
	
	var btnWidth = this.width/3;
	var btnHeight = btnWidth/275 * 108;
	if(this.btn1){
		this.btn1.layout({w:btnWidth,h:btnHeight});
	}
	if(this.btn2){
		this.btn2.layout({w:btnWidth,h:btnHeight});
	}
	if(this.btn1 && this.btn2){
		this.btn1.group.x = (this.width - 2 * btnWidth)/3;
		this.btn1.group.y = this.height -1.6 * btnHeight;
		
		this.btn2.group.x = this.btn1.group.x *2 + btnWidth;
		this.btn2.group.y = this.height -1.6 * btnHeight;
	}else{
		if(this.btn1){
			this.btn1.group.x = this.width/2 - btnWidth/2;
			this.btn1.group.y = this.height -1.6 * btnHeight;
		}
		if(this.btn2){
			this.btn2.group.x = this.width/2 - btnWidth/2;
			this.btn2.group.y = this.height -1.6 * btnHeight;
		}
	}
	this.layoutTextMessage();
};


MessageBox1Sprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.background = game.add.image(0,0,'messagebox');
	this.background.frameName = 'background';
	this.group.add(this.background);
	
	this.btnClose = new ButtonSprite(0,0,'btn_x_normal','btn_x_press',this.closeCallBack,this);
	this.btnClose.create();
//	this.btnClose.group.visible = false;
	this.group.add(this.btnClose.group);
	
	if(this.btn1Info){
		this.btn1 = new ButtonSprite(0,0,this.btn1Info.image1,this.btn1Info.image2,this.btn1Info.callBack,this);
		this.btn1.image = this.btn1Info.image;
		this.btn1.create();
		this.group.add(this.btn1.group);
	}
	
	if(this.btn2Info){
		this.btn2 = new ButtonSprite(0,0,this.btn2Info.image1,this.btn2Info.image2,this.btn2Info.callBack,this);
		this.btn2.image = this.btn2Info.image;
		this.btn2.create();
		this.group.add(this.btn2.group);
	}
	
	this.ltsArr = [];
	for(var i = 0 ;i<this.textArr.length;i++){
		this.ltsArr[i] = new LineTextSprite(0,0,0,'#ffffff');
		this.ltsArr[i].create();
		this.group.add(this.ltsArr[i].group);
		var text = this.ltsArr[i].textSprite;
//		text.align = 'center';
		text.font = 'Arial Black';
	    text.fontWeight = 'bold';
	    text.setShadow(5, 5, 'rgba(0,0,0,0.5)', 10);
	    text.stroke = '#000000';
	    text.strokeThickness = 3;
	    // 文本颜色
//	    text.addColor('#ff00ff', 9);
//	    text.addColor('#43d637', 13);
	    // 文本描边的颜色
//	    text.addStrokeColor('#ff0000', 13);
//	    text.addStrokeColor('#000000', 20);
		
	}
	
	this.topHint =  game.add.image(0,0,'messagebox');
	this.topHint.frameName = 'hint';
	this.group.add(this.topHint);
}
MessageBox1Sprite.test = function(){
//	var btn1Info = {image:'dismissroom',image1:'agree',image2:'agree_press',callBack:function(btn){
//			btn.context.group.destroy();
//		}
//	};
//	var btn2Info = {image:'dismissroom',image1:'refuse',image2:'refuse_press',callBack:function(btn){btn.context.group.destroy();}};
//	var mb = new MessageBox1Sprite(btn1Info,btn2Info,['hello','hello1','hello2']);
//	mb.create();
//	mb.layout({w:game.world.width/2,h:0});
//	
//	mb.group.x = game.world.width/4;
//	mb.group.y = game.world.height/4;


	var btn1Info = {image:'buttons',image1:'confirm_normal',image2:'confirm_press',callBack:function(btn){
			btn.context.group.destroy();
		}
	};
	var mb = new MessageBox1Sprite(btn1Info,null,['我操','我操'],false);
	mb.create();
	mb.layout({w:game.world.width/2,h:0});
	
	mb.group.x = game.world.width/4;
	mb.group.y = game.world.height/4;
	
	
//	mb.setMessage(['我操','我操']);
	mb.setMessage(['我操']);
	mb.setMessage(['我操','我操','我操操操']);
}

