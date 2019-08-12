function RecordGameSprite(){
	this.recordPlayBtn;
	this.recordNextBtn;
	this.recordLastBtn;
	this.recordPlayText;
	this.recordNextText;
	this.recordLastText;
	
	this.recordGameIndex;
	
	this.gameIndex = 0;
	this.actionIndex = 0;
	this.canNext = true;
}

RecordGameSprite.prototype = util.inherit(GameSprite.prototype);
RecordGameSprite.prototype.constructor = RecordGameSprite;

RecordGameSprite.prototype.layout = function(){
	GameSprite.prototype.layout.apply(this, arguments);
	
	var middleWidth = this.middleSprite.width;
	var width = middleWidth/3 *2;
	var height = width/60 * 53;
	var wh = {w:width,h:height};
	this.recordPlayBtn.layout(wh);
	this.recordNextBtn.layout(wh);
	this.recordLastBtn.layout(wh);
	wh={w:width,h:height/3,fh:height/3};
	this.recordPlayText.layout(wh);
	this.recordNextText.layout(wh);
	this.recordLastText.layout(wh);
	this.recordPlayText.setText('下一步');
	this.recordNextText.setText('下一局');
	this.recordLastText.setText('上一局');
	
	this.recordPlayBtn.group.y = game.world.height/2 + game.world.height/8*2.3;
	this.recordNextBtn.group.y = game.world.height/2 + game.world.height/8*2.3;
	this.recordLastBtn.group.y = game.world.height/2 + game.world.height/8*2.3;
	
	this.recordPlayBtn.group.x = game.world.width/2 + middleWidth/2;
	this.recordNextBtn.group.x = game.world.width/2-width;
	this.recordLastBtn.group.x = game.world.width/2-2*width;
	
	this.recordPlayText.group.y = this.recordPlayBtn.group.y +height;
	this.recordNextText.group.y = this.recordNextBtn.group.y +height;
	this.recordLastText.group.y = this.recordLastBtn.group.y +height;
	this.recordPlayText.group.x = this.recordPlayBtn.group.x;
	this.recordNextText.group.x = this.recordNextBtn.group.x;
	this.recordLastText.group.x = this.recordLastBtn.group.x;
	
	this.recordGameIndex.layout(wh);
	this.recordGameIndex.group.x = game.world.width/2 - width/2;
	this.recordGameIndex.group.y = game.world.height/2;
	this.recordGameIndex.setText(this.recordGameIndex.getText());
	
	var width = game.world.width/16;
	

	var sprite;
	var innerWidth, middleWidth, outterWidth;
	
	innerWidth = game.world.width / 16;
	middleWidth = innerWidth;
	outterWidth = (game.world.width - 11 * innerWidth) / 9; 
	
	var card3ch = (game.world.height - innerWidth) / 19;
	innerWidth = card3ch / 68 * 124 / 124 * 60;
	middleWidth = card3ch / 70 * 105 / 10 * 13;
	var outterCh = outterWidth / 105 * 70;
	outterWidth = outterWidth / 10 * 13;
	sprite = this.cardsSpriteArr[3];
	sprite.layout({iw:middleWidth,mw:middleWidth,ow:outterWidth});
	sprite.group.x = 1.1 * this.userSpriteArr[0].width;
	sprite.group.y = 1.7 * card3ch;
	sprite.innerGroup.x = (middleWidth - innerWidth) / 2;
	sprite.innerGroup.y = 0;
	sprite.outterGroup.x = (1 + 1 / 5) * middleWidth;
	sprite.outterGroup.y = -2 * card3ch + game.world.height / 2 - 5 * outterCh;
	sprite.ajustInnerGroup();
	sprite.showArrow(sprite.spriteArrowGroup.visible);
	
	sprite = this.cardsSpriteArr[1];
	sprite.layout({iw:middleWidth,mw:middleWidth,ow:outterWidth});
	sprite.group.y = 1.7 * card3ch;
	sprite.innerGroup.x = game.world.width - 1.1 * this.userSpriteArr[0].width
			-1.3* middleWidth;
	sprite.middleGroup.x = sprite.innerGroup.x - (middleWidth - innerWidth) / 2;
	sprite.middleGroup.y = 0;
	sprite.outterGroup.x = sprite.innerGroup.x - 1 / 3 * middleWidth - 3
			* outterWidth;
	sprite.outterGroup.y = -2 * card3ch + game.world.height / 2 - 6 * outterCh;
	sprite.ajustMiddleGroup();
	sprite.showArrow(sprite.spriteArrowGroup.visible);
	
	
	
	
	
	this.cardsSpriteArr[1].innerGroup.x = this.cardsSpriteArr[1].middleGroup.x;
//	this.cardsSpriteArr[1].group.x -= width*0.4;
	this.cardsSpriteArr[3].middleGroup.x = this.cardsSpriteArr[3].innerGroup.x;
	this.cardsSpriteArr[3].outterGroup.x += 0.2* width;
	this.cardsSpriteArr[3].innerGroup.y += 0.2*width;
	
}

RecordGameSprite.prototype.create = function(){
	GameSprite.prototype.create.apply(this, arguments);
	
	this.recordPlayBtn = new ButtonSprite(0,0,'playbtn.png','playbtn_h.png',function(btn){
			btn.context.canNext = true;
		},this);
	this.recordPlayBtn.image = 'recordBtn';
	this.recordPlayBtn.create();
	
	this.recordNextBtn = new ButtonSprite(0,0,'kuaijin.png','kuaijin_h.png',function(btn){
			if(btn.context.gameIndex == RecordReceiver.msgQueue.length - 1){
				var messageBox = new TextBoxSprite(['已经是最后一局了']);
				messageBox.create();
				messageBox.layout(null);
			}else{
				btn.context.gameIndex ++;
				btn.context.actionIndex = 0;
				btn.context.canNext = true;
			}
		},this);
	this.recordNextBtn.image = 'recordBtn';
	this.recordNextBtn.create();
	
	this.recordLastBtn = new ButtonSprite(0,0,'kuaitui.png','kuaitui_j.png',function(btn){
			if(btn.context.gameIndex == 0){
				var messageBox = new TextBoxSprite(['这已经是第一局了']);
				messageBox.create();
				messageBox.layout(null);
			}else{
				btn.context.gameIndex --;
				btn.context.actionIndex = 0;
				btn.context.canNext = true;
			}
		},this);
	this.recordLastBtn.image = 'recordBtn';
	this.recordLastBtn.create();
	
	this.recordPlayText = new LineTextSprite(0,0,0,'#ffffff');
	this.recordPlayText.create();
	this.recordNextText = new LineTextSprite(0,0,0,'#ffffff');
	this.recordNextText.create();
	this.recordLastText = new LineTextSprite(0,0,0,'#ffffff');
	this.recordLastText.create();
	
	
	this.recordGameIndex = new LineTextSprite(0,0,0,'#ff0000');
	this.recordGameIndex.create();
	
	
	
};

RecordGameSprite.prototype.createCards = function(){
	this.cardsSpriteArr = [];
	var cs;
	cs = new Cards0Sprite(0,0,0,this.commandHandler);
	cs.create();
	this.cardsSpriteArr.push(cs);
	cs = new RecordCards1Sprite(0,0,0);
	cs.create();
	this.cardsSpriteArr.push(cs);
	cs = new RecordCards2Sprite(0,0,0);
	cs.create();
	this.cardsSpriteArr.push(cs);
	cs = new RecordCards3Sprite(0,0,0);
	cs.create();
	this.cardsSpriteArr.push(cs);
}