function AudioSprite(width,commandHandler){
	this.width = width;
	this.commandHandler = commandHandler;
	
	this.button;
	this.tint;
	
	this.downStartTime;
	this.outted = false;
	
	
	
	
	
};
AudioSprite.prototype = util.inherit(GroupSprite.prototype);
AudioSprite.prototype.constructor = AudioSprite;

AudioSprite.prototype.layout = function(wh){
	this.width = wh.w;
	
	this.tint.width = this.width;
	this.tint.height = 1.2 *this.width;
	
	this.button.layout({w:this.width,h:this.width});
	this.button.group.y= 1.2 *this.width;
	
}

AudioSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.tint = game.add.image(0,0,'other');
	this.tint.frameName = 'voiceTips';
	this.tint.visible = false;
	this.group.add(this.tint);
	
	this.button = new ButtonSprite(0,0,'btn_yuyin_normal','btn_yuyin_press',function(btn){
	},this);
	this.button.create();
	this.group.add(this.button.group);
	
    var context = this;
    this.button.button.onInputUp.add(function(btn){
    	
    	if(!localStorage.rainAllowRecord || localStorage.rainAllowRecord !== 'true'){ //用户拒绝录音
    		return ;
		}
    	var second = (new Date().getTime() - this.downStartTime) ;
    	this.tint.visible = false;
    	if(second < 500 ){
    		clearTimeout(this.recordTimer);
    		return ;
    	}
    	
    	if(this.outted){
    		wx.stopRecord({
   		 		success: function (res) {
        		
    			}
			});
    		return ;
    	}
//    	alert('auidio go');
    	wx.stopRecord({
   		 	success: function (res) {
        		var localId = res.localId;
        		wx.uploadVoice({
   				 	localId: localId, 
    				isShowProgressTips: 0, 
        			success: function (res) {
        				var serverId = res.serverId; 
						if(context.commandHandler){
							context.commandHandler.listen('chatVoice',{serverId:serverId,localId:localId});
						}
    				}
				});
    		}
		});
    }, this);
    this.button.button.onInputDown.add(function(btn){
    	
    	if(!localStorage.rainAllowRecord || localStorage.rainAllowRecord !== 'true'){ //用户拒绝录音
    		return ;
		}
    	this.downStartTime = new Date().getTime();
    	this.tint.visible = true;
    	
    	var context = this;
    	this.recordTimer = setTimeout(function(){
    		game.world.bringToTop(context.group);
    		context.outted = false;
        	wx.startRecord({
            	success: function(){
                	localStorage.rainAllowRecord = 'true';
            	},
            	cancel: function () {
               		alert('用户拒绝授权录音');
            	}
        	});
    	},500);
    }, this);
    
    this.button.button.onInputOut.add(function(btn){
    	this.outted = true;
    }, this);
	
		
	
	
}
AudioSprite.test = function(){
	var as =new AudioSprite(0,null);
	as.create();
	as.layout({w:100,h:100});
	as.group.x = 100;
	as.group.y = 100;
	
}
	