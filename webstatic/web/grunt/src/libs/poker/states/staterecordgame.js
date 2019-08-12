function StateRecordGame() {

//	this.recordReceiver;
	
	this.resultSprite;
	this.recordNextBtn;
	this.recordLastBtn;
	this.recordNextText;
	this.recordLastText;
	
	
	this.gameIndex = 0;
	
	this.showInner = function(){
		this.resultSprite.clear();
		this.resultSprite.group.visible = true;
		
		var gameInfo =  StateRecordGame.msgQueue[this.gameIndex];
		var roomNoticeInfo,resultInfo,roomInfo;
		
		
		
		
		for(var i = 0;i< gameInfo.length;i++){
			if(gameInfo[i].code == 'roomNotice'){
				roomNoticeInfo = gameInfo[i];
			}else if(gameInfo[i].code == 'result'){
				resultInfo = gameInfo[i];
			}
		}
		roomInfo = '闯三关 第 ' + (this.gameIndex+1) +'/'+roomNoticeInfo.roomInfo.totalJu +' 局';
		
		
		var dataArr = [];
		for(var i = 0;i< roomNoticeInfo.users.length;i++){
			var userInfo =  roomNoticeInfo.users[i];
			var data = {};
			data.headUrl = userInfo.url;
			dataArr[userInfo.dir] = data;
		}
		
		var cards = resultInfo.cards;
		for(var i = 0;i< cards.length;i++){
			var dir = cards[i].dir;
			var data = dataArr[dir];
			
			var nameArr = [];
			var cns = [];
			
			var tmpCs = cards[i].cards;
			if(tmpCs[0]){
				nameArr.push(tmpCs[0].name);
				nameArr.push(' ');
				nameArr.push(' ');
				cns = tmpCs[0].cns;
			}else{
				for(var j = 1;j<tmpCs.length;j++){
					nameArr.push(tmpCs[j].name);
					cns = cns.concat(tmpCs[j].cns);
				}
			}
			data.cns = cns;
			data.nameArr = nameArr;
		}
		var msg = resultInfo;
		for(var i = 0;i< msg.scores.length;i++){
			var dir = msg.scores[i].dir;
			var data = dataArr[dir];
			data.score = msg.scores[i].score;
		}
		
		util.printObject(dataArr[0]);
		util.printObject(dataArr[1]);
		
		this.resultSprite.show(dataArr,null,roomInfo,0);
		this.resultSprite.topSuccess.visible = false;
		this.resultSprite.topPing.visible =  false;
		this.resultSprite.topFail.visible = false;
		this.resultSprite.readyBtn.group.visible = false;
		game.world.bringToTop(this.recordNextBtn.group);
		game.world.bringToTop(this.recordLastBtn.group);
		game.world.bringToTop(this.recordNextText.group);
		game.world.bringToTop(this.recordLastText.group);
	}
	
	this.createInner = function(){
		
		this.resultSprite = new ResultSprite();
		this.resultSprite.create();
		this.resultSprite.readyBtn.group.visible = false;
		
		var context = this;
		
		this.recordNextBtn = new ButtonSprite(0,0,'kuaijin.png','kuaijin_h.png',function(btn){
				if(btn.context.gameIndex == StateRecordGame.msgQueue.length - 1){
					GameSprite.prototype.showMessageBox(['这已经是最后一局了'],function(btn){btn.context.group.destroy()});
				}else{
					btn.context.gameIndex ++;
					context.showInner();
				}
			},this);
		this.recordNextBtn.image = 'recordBtn';
		this.recordNextBtn.create();
		
		this.recordLastBtn = new ButtonSprite(0,0,'kuaitui.png','kuaitui_j.png',function(btn){
				if(btn.context.gameIndex == 0){
					GameSprite.prototype.showMessageBox(['这已经是第一局了'],function(btn){btn.context.group.destroy()});
				}else{
					btn.context.gameIndex --;
					context.showInner();
				}
			},this);
		this.recordLastBtn.image = 'recordBtn';
		this.recordLastBtn.create();
		
		this.recordNextText = new LineTextSprite(0,0,0,'#ffffff');
		this.recordNextText.create();
		this.recordLastText = new LineTextSprite(0,0,0,'#ffffff');
		this.recordLastText.create();
		
	}
	this.layoutInner = function(){
		
		this.resultSprite.layout();
		
		var width = game.world.height/9;
		var height = width/60 * 53;
		var wh = {w:width,h:height};
		this.recordNextBtn.layout(wh);
		this.recordLastBtn.layout(wh);
		wh={w:width,h:height/3,fh:height/3};
		this.recordNextText.layout(wh);
		this.recordLastText.layout(wh);
		this.recordNextText.setText('下一局');
		this.recordLastText.setText('上一局');
		
		this.recordNextBtn.group.y = game.world.height/6* 5+ (game.world.height /6 - height)/2;
		this.recordLastBtn.group.y = game.world.height/6 *5+ (game.world.height /6 - height)/2;
		
		this.recordNextBtn.group.x = game.world.width/2 + 0.2*width;
		this.recordLastBtn.group.x = game.world.width/2-1.2 *width;
		
		this.recordNextText.group.y = this.recordNextBtn.group.y +height;
		this.recordLastText.group.y = this.recordLastBtn.group.y +height;
		this.recordNextText.group.x = this.recordNextBtn.group.x;
		this.recordLastText.group.x = this.recordLastBtn.group.x;
		
		
	}

	this.create = function() {
		console.log('stateRecordGame');
		this.createInner();
		this.layoutInner();
		this.showInner();
	};

	this.update = function() {
		
	};

};
