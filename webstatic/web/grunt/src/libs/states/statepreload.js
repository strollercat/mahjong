function StatePreload() {

};
StatePreload.prototype.preload = function(){
//	console.log('preload');
	this.preloadView();
	this.preloadImage();
	this.preloadAudio();
};
StatePreload.prototype.create = function(){
	game.config.authorBoxCw = game.world.width / 17; // 大牌宽
	game.config.authorBoxCh = game.config.authorBoxCw * 1.5; // 大牌高
	game.config.otherBoxCw = game.config.authorBoxCw / 5 * 3; // 小牌宽
	game.config.otherBoxCh = 1.5 * game.config.otherBoxCw; // 小牌高
	this.ok = true;
};


StatePreload.prototype.preloadView = function(){
//	wx.startRecord();
//	setTimeout(function(){
//		wx.stopRecord({
//   		 	success: function (res) {
//        		
//    		}
//		});
//	},2000);
	
	var bgSprite = game.add.image(0,0,'loading');
	bgSprite.frameName = 'background';
	bgSprite.width = game.world.width;
	bgSprite.height = game.world.height;
	
	var preloadGroup = game.add.group();
	
	var tradeMark = game.add.image(0,0,'loading');
	tradeMark.frameName = 'circle';
	tradeMark.width = game.world.height/4;
	tradeMark.height = tradeMark.width;
	tradeMark.anchor.setTo(0.5);
	preloadGroup.add(tradeMark);
	preloadGroup.x = game.world.width/2;
	preloadGroup.y = game.world.height/2;
	
	var timeEvent = game.time.events.loop(Phaser.Timer.SECOND/2, function() {
		tradeMark.rotation  +=0.5;
	}, this);
	
	var textSprite = new LineTextSprite(tradeMark.width,tradeMark.height/6,tradeMark.height/6,'#ffffff');
	textSprite.create();
//	textSprite.layout({w:tradeMark.height/6,h:tradeMark.height/6,fh:tradeMark.height/6});
	textSprite.group.x = game.world.width/2 - tradeMark.width/2;
	textSprite.group.y = game.world.height/2 + tradeMark.height /2*1.2;
	textSprite.setText('资源加载中...  '+ 0 +'%');
//		preloadGroup.add(textSprite.group);
	textSprite.textSprite.addColor('#ffffff', 0);
	textSprite.textSprite.addColor('#ffff00', 7);
	
	var statePreload = this;
	game.load.onFileComplete.add(function(progress,cacheKey,success,totalLoaded,totalFiles){
		if(statePreload.ok){
			game.time.events.remove(timeEvent);
			return ;
		}
		if(progress >=0 && progress <=9 ){
			textSprite.setText('资源加载中...  '+progress +'%');
		}else if(progress >=10 && progress <=99){
			textSprite.setText('资源加载中... '+progress +'%');
		}else if(progress == 100){
			textSprite.setText('资源加载中... '+100 +'%');
		}
	});
};
StatePreload.prototype.preloadImage = function(){
	
	game.load.atlas('actioninputs', game.resourcePrefix +'resources/images/actioninputs.png',
				game.resourcePrefix +'resources/images/actioninputs.json');
				
	game.load.atlas('actionshows', game.resourcePrefix +'resources/images/actionshows.png',
			game.resourcePrefix +'resources/images/actionshows.json');
			
	game.load.atlas('middle', game.resourcePrefix +'resources/images/middle.png',
			game.resourcePrefix +'resources/images/middle.json');
			
	game.load.atlas('mjcards', game.resourcePrefix +'resources/images/mjcards.png',
			game.resourcePrefix +'resources/images/mjcards.json');
			
	game.load.atlas('userinfo', game.resourcePrefix +'resources/images/userinfo.png',
			game.resourcePrefix +'resources/images/userinfo.json');
			
	game.load.atlas('buttons', game.resourcePrefix +'resources/images/buttonsAll.png?time=123',
			game.resourcePrefix +'resources/images/buttonsAll.json?time=123');
	
	game.load.atlas('result', game.resourcePrefix +'resources/images/result.png',
			game.resourcePrefix +'resources/images/result.json');
			
	game.load.atlas('animationItem', game.resourcePrefix +'resources/images/animationItem.png',
			game.resourcePrefix +'resources/images/animationItem.json');
			
	game.load.atlas('messagebox', game.resourcePrefix +'resources/images/messagebox.png',
			game.resourcePrefix +'resources/images/messagebox.json');	
			
	game.load.atlas('recordBtn', game.resourcePrefix +'resources/images/record.png',
				game.resourcePrefix +'resources/images/record.json');	
			
//	game.load.atlas('other', game.resourcePrefix + 'resources/images/other.png',
//				game.resourcePrefix +'resources/images/other.json');	

	game.load.atlas('other', game.resourcePrefix +'resources/images/other.png',
				game.resourcePrefix +'resources/images/other.json');	
	
//	alert(game.resourcePrefix +'resources/images/other.png',
//				'resources/images/other.json');
	
	game.load.atlas('chat.showvoice', game.resourcePrefix +'resources/images/showvoice.png',
			game.resourcePrefix +'resources/images/showvoice.json');
			
	game.load.atlas('chat.showfaces', game.resourcePrefix +'resources/images/faces.png',
		game.resourcePrefix +'resources/images/faces.json');
		
	game.load.atlas('baidaCard', game.resourcePrefix +'resources/images/baida.png',
			game.resourcePrefix +'resources/images/baida.json');
			
			
	game.load.atlas('tuoguanall', game.resourcePrefix +'resources/images/tuoguanall.png',
			game.resourcePrefix +'resources/images/tuoguanall.json');
		
	game.load.image('background_paizhuo', game.resourcePrefix +'resources/images/bac_paizuo.png');

	

	

	
	
	
			
			

};
StatePreload.prototype.preloadAudio = function(){
	for(var i = 0;i<9;i++){
		game.load.audio('text_'+i, [ game.resourcePrefix +'resources/sound/msg/fix_msg_'+(i+1)+'.mp3' ]);
	}
	for(var i =0;i<3;i++){
		for(var j = 1;j<= 9;j++ ){
			if(i == 0){
				game.load.audio('mj.card.'+j, [ game.resourcePrefix +'resources/sound/card/'+j+'.mp3' ]);
			}else{
				game.load.audio('mj.card.'+i +''+j, [ game.resourcePrefix +'resources/sound/card/'+i +''+j+'.mp3' ]);
			}
			
		}
	}
	for (var i = 3; i <= 9; i++) {
		game.load.audio('mj.card.'+i+ '1', [ game.resourcePrefix +'resources/sound/card/'+i+'1.mp3' ]);
	}
	game.load.audio('mj.chi', [ game.resourcePrefix +'resources/sound/card/chi.mp3' ]);
	game.load.audio('mj.peng', [ game.resourcePrefix +'resources/sound/card/peng.mp3' ]);
	game.load.audio('mj.gang', [ game.resourcePrefix +'resources/sound/card/gang.mp3' ]);
	game.load.audio('mj.hu', [ game.resourcePrefix +'resources/sound/card/hu.mp3' ]);
	game.load.audio('mj.hua', [ game.resourcePrefix +'resources/sound/card/flower.mp3' ]);
	
	game.load.audio('click', [ game.resourcePrefix +'resources/sound/click.mp3' ]);
	game.load.audio('discard', [ game.resourcePrefix +'resources/sound/discardTile.mp3' ]);
	game.load.audio('lose', [ game.resourcePrefix +'resources/sound/lose.mp3' ]);
	game.load.audio('tick', [ game.resourcePrefix +'resources/sound/tick.mp3' ]);
	game.load.audio('uiclick', [ game.resourcePrefix +'resources/sound/ui_click.mp3' ]);
	game.load.audio('win', [ game.resourcePrefix +'resources/sound/win.mp3' ]);
};
StatePreload.prototype.update = function(){
	if(this.ok){
   		game.state.start('game');
//	  	game.state.start('test')
	}
};
