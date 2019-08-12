function ChatTextInput(width,commandHandler){
	this.width = width;
	this.height;
	this.commandHandler = commandHandler;
	
	
	this.bgSprite;
	
	this.bottomRect;
	
	this.upFaceBtn;
	this.upFaceLts;
	this.upTextBtn;
	this.upTextLts;
	
	this.faceGroup;
	this.faceSpriteArr;
	
	
	this.textGroup;
	this.textLtsArr;
	this.textArr;
};
ChatTextInput.btnCallBack = function(btn){
	
	var context = btn.context;
	var name = btn.name;
	if(name == 'face'){
		context.upFaceBtn.tint =0xE6E6FA ;
		context.upTextBtn.tint = 0xF8F8FF;
		context.faceGroup.visible = true;
		context.textGroup.visible = false;
	}else if(name == 'text'){
		context.upTextBtn.tint = 0xE6E6FA ;
		context.upFaceBtn.tint = 0xF8F8FF;
		context.faceGroup.visible = false;
		context.textGroup.visible = true;
	}
};



ChatTextInput.textCallBack = function(btn){
	
	console.log(btn.name);
	var context = btn.context;
	var text = btn.name;
	context.group.visible = false;
	if(context.commandHandler){
		context.commandHandler.listen('chatText',btn.name);
	}
}




ChatTextInput.prototype = util.inherit(GroupSprite.prototype);
ChatTextInput.prototype.constructor = ChatTextInput;


ChatTextInput.prototype.getTextByIndex = function(index){
	return this.textArr[index];
};



ChatTextInput.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	
	
	this.bgSprite = new MessageBoxSprite(0,0,function(btn){
		btn.context.group.visible = false;
	},this);
	this.bgSprite.create();
	this.bgSprite.setTop('');
	this.bgSprite.btnConfirm.group.visible = false;
	this.group.add(this.bgSprite.group);
	
//	this.upFaceBtn = game.add.graphics(0, 0);
//	this.upFaceBtn.beginFill(0xffffff);
//	this.upFaceBtn.drawRect(0,0,10,10);
//	this.upFaceBtn.endFill();
//	this.upFaceBtn.tint = 0xE6E6FA;

//	this.upFaceBtn = game.add.image(0,0,'chatTop');
	this.upFaceBtn = game.add.image(0,0,'other');
	this.upFaceBtn.frameName ='yellowBg';
	this.upFaceBtn.alpha = 0.2;
	this.upFaceBtn.inputEnabled = true;
	this.upFaceBtn.events.onInputDown.add(ChatTextInput.btnCallBack, this);
	this.upFaceBtn.name = 'face';
	this.upFaceBtn.context = this;
	this.group.add(this.upFaceBtn);
	this.upFaceLts = new LineTextSprite(0,0,0,'#87CEFA');
	this.upFaceLts.create();
	this.upFaceLts.setText('表情');
	var textStyle = this.upFaceLts.textSprite;
	textStyle.font = 'Arial Black';
	textStyle.fontWeight = 'bold';
	textStyle.setShadow(5, 5, 'rgba(0,0,0,0.5)', 10);
	textStyle.stroke = '#000000';
	textStyle.strokeThickness = 3;
	this.group.add(this.upFaceLts.group);
	
//	this.upTextBtn = game.add.graphics(0, 0);
//	this.upTextBtn.beginFill(0xffffff);
//	this.upTextBtn.drawRect(0,0,10,10);
//	this.upTextBtn.endFill();
//	this.upTextBtn = game.add.image(0,0,'chatTop');

	this.upTextBtn = game.add.image(0,0,'other');
	this.upTextBtn.frameName ='yellowBg';
	this.upTextBtn.alpha = 0.2;
	this.upTextBtn.tint = 0xF8F8FF;
	this.upTextBtn.inputEnabled = true;
	this.upTextBtn.events.onInputDown.add(ChatTextInput.btnCallBack, this);
	this.upTextBtn.name = 'text';
	this.upTextBtn.context = this;
	this.group.add(this.upTextBtn);
	this.upTextLts = new LineTextSprite(0,0,0,'#87CEFA');
	this.upTextLts.create();
	this.upTextLts.setText('常用短语');
	var textStyle = this.upTextLts.textSprite;
	textStyle.font = 'Arial Black';
	textStyle.fontWeight = 'bold';
	textStyle.setShadow(5, 5, 'rgba(0,0,0,0.5)', 10);
	textStyle.stroke = '#000000';
	textStyle.strokeThickness = 3;
	this.group.add(this.upTextLts.group);
	
//	this.bottomRect = game.add.graphics(0, 0);
//	this.bottomRect.beginFill(0xF8F8FF);
//	this.bottomRect.drawRect(0,0,10,10);
//	this.bottomRect.endFill();
	this.bottomRect = game.add.image(0,0,'other');
	this.bottomRect.frameName = 'yellowBg';
	this.bottomRect.alpha = 0.2;
	this.group.add(this.bottomRect);
	
	
	
	
	var faceArr = [];
	faceArr.push('angry');
	faceArr.push('fanu');
	faceArr.push('han');
	faceArr.push('happy');
	faceArr.push('huaixiao');
	faceArr.push('jiong');
	faceArr.push('lihai');
	faceArr.push('se');
	faceArr.push('shaoxiang');
	faceArr.push('shihua');
	faceArr.push('sleep');
	faceArr.push('smile');
	faceArr.push('touxiang');
	faceArr.push('yun');
	faceArr.push('zhiya');
	
	this.faceGroup = game.add.group();
	this.faceSpriteArr = [];
	var face,k = 0;
	for(var i = 0;i<3;i++){
		for(var j = 0;j<5;j++){
			face = game.add.image(0 ,0,'chat.showfaces');
			if(faceArr[k] == 'smile'){
				face.frameName = 'smaile0'
			}else if(faceArr[k] == 'fanu'){
				face.frameName ='fennu0';
			}else if(faceArr[k] == 'lihai'){
				face.frameName ='lihai12';
			}else{
				face.frameName = faceArr[k] +'0';
			}
			face.name = '#face:'+faceArr[k];
			face.context =  this;
			face.inputEnabled = true;
			face.events.onInputDown.add(ChatTextInput.textCallBack, this);
			this.faceGroup.add(face);
			this.faceSpriteArr.push(face);
			k++;
		}
	}
	this.group.add(this.faceGroup);
	
	this.textLtsArr = [];
	this.textArr = [];
	var lts;
	this.textArr.push('快点啊，都等的我花儿都谢了');
	this.textArr.push('怎么又断线了，网络怎么这么差啊');
	this.textArr.push('不要走，决战到天亮');
	this.textArr.push('你的牌打的也太好了');
	this.textArr.push('你是MM还是GG啊');
	this.textArr.push('和你合作真是太愉快了');
	this.textArr.push('大家好，很高兴认识各位');
	this.textArr.push('各位，我得离开一会儿');
	this.textArr.push('不要吵了，专心玩游戏吧');
	this.textGroup = game.add.group();
	for(var i = 0;i< 6;i++){
		lts = new LineTextSprite(0,0,0,'#87CEFA');
		lts.create();
		var textStyle = lts.textSprite;
		textStyle.font = 'Arial Black';
		textStyle.fontWeight = 'bold';
		textStyle.setShadow(5, 5, 'rgba(0,0,0,0.5)', 10);
		textStyle.stroke = '#000000';
		textStyle.strokeThickness = 3;
		lts.textSprite.indexindex = i;
		lts.textSprite.inputEnabled = true;
		lts.textSprite.events.onInputDown.add(function(textSprite){
			this.group.visible = false;
			console.log('#text:'+textSprite.indexindex);
			if(this.commandHandler){
				this.commandHandler.listen('chatText','#text:'+textSprite.indexindex);
			}
		}, this);
		this.textGroup.add(lts.group);
		this.textLtsArr.push(lts);
//		if(i >=6){
//			lts.group.visible = false;
//		}
	}
	this.textGroup.visible = false;
	this.group.add(this.textGroup);
}

ChatTextInput.prototype.layout = function(wh) {
	this.width = wh.w;	
	
	var bottomWidth = this.width;
	var bottomHeight = this.width/5 *3;
	var upWidth = this.width/2,upHeight = bottomHeight /5;
	var lts;
	this.height =  bottomHeight + upHeight;
	
	this.bgSprite.layout({w:bottomWidth * 1.3,h:(bottomHeight + upHeight) * 1.3});
	this.bgSprite.group.x =  - 0.15 * bottomWidth;
	this.bgSprite.group.y = - 0.15 * (bottomHeight + upHeight);
		
	this.upFaceBtn.width = upWidth;
	this.upFaceBtn.height = upHeight;
	this.upFaceBtn.x = 0.025 * upWidth;
	this.upFaceLts.layout({w:upWidth,h:upHeight,fh:upHeight/3 *2});
	this.upFaceLts.setText('表情');
	
	this.upTextBtn.x = upWidth - 0.025*upWidth;
	this.upTextBtn.width = upWidth;
	this.upTextBtn.height = upHeight;
	this.upTextLts.layout({w:upWidth,h:upHeight,fh:upHeight/3*2});
	this.upTextLts.group.x = upWidth;
	this.upTextLts.setText("语言");
	
	this.bottomRect.y = upHeight*0.96;
	this.bottomRect.width = bottomWidth;
	this.bottomRect.height = bottomHeight * 1.05;
	
	
	
	var k = 0;
	var face;
	var faceWidth = this.width/5;
	var faceRealWidth = faceWidth /5 *4;
	for(var i = 0;i<3;i++){
		for(var j = 0;j<5;j++){
			face = this.faceSpriteArr[k];
			if(k == 1){
				face.width = faceRealWidth/140 * 108;
				face.height = faceRealWidth;
				face.x = j *faceWidth + (face.height - face.width)/2  ;
				face.y = i*faceWidth;
			}else if(k == 7){
				face.width = faceRealWidth/142 * 89;
				face.height = faceRealWidth;
				face.x = j *faceWidth + (face.height - face.width)/2  ;
				face.y = i*faceWidth;
			}else if(k == 12){
				face.width = faceRealWidth;
				face.height = faceRealWidth/152 * 114;
				face.x = j *faceWidth ;
				face.y = i*faceWidth+ (face.width - face.height)/2;
			}else if(k == 2){
				face.width = faceRealWidth * 0.8 /129 * 115;
				face.height = faceRealWidth * 0.8;
				face.x = j *faceWidth + (faceWidth - face.width)/2  ;
				face.y = i*faceWidth+ 0.1 *faceRealWidth;
			}else if(k == 6){
				face.width = faceRealWidth/132 * 82;
				face.height = faceRealWidth;
				face.x = j *faceWidth + (face.height - face.width)/2  ;
				face.y = i*faceWidth;
			}else{
				face.width = 0.8*faceRealWidth;
				face.height = 0.8 *faceRealWidth;
				face.x = j *faceWidth + 0.1* faceRealWidth ;
				face.y = i*faceWidth+0.1*faceRealWidth;
			}
			
			k++;
		}
	}
	this.faceGroup.y = upHeight +(faceWidth - faceRealWidth)/2;
	this.faceGroup.x = (faceWidth - faceRealWidth)/2;
	
	var textHeight = this.width/15;
	for(var i = 0;i< 6;i++){
		lts = this.textLtsArr[i];
		lts.layout({w:this.width,h:textHeight,fh:textHeight});		
		lts.setTextToLeft(this.textArr[i]);
		lts.group.x = textHeight;
		lts.group.y = i * (textHeight + textHeight/2);
	}
	this.textGroup.y = upHeight * 1.2;

}
ChatTextInput.test = function(){
	var cti = new ChatTextInput(300);
	cti.create();
	cti.layout({w:300});
	
	cti.group.x = game.world.width/8;
	cti.group.y = game.world.height/8;



	
};
