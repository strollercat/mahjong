function AudioManager(){
	this.audioMap = {};
};


AudioManager.currentLocalId; //当前正在播放的localId

AudioManager.prototype.create = function(){
	var audio;
	
	for(var i = 0;i<9;i++){
		audio = game.add.audio('text_'+i);
		this.audioMap['text_'+i] = audio;
		
	}
	for(var i =0;i<3;i++){
		for(var j = 1;j<= 9;j++ ){
			if(i == 0){
				audio = game.add.audio('mj.card.'+j);
				this.audioMap['mj.card.'+j] = audio;
			}else{
				audio = game.add.audio('mj.card.'+i +''+j);
				this.audioMap['mj.card.'+i +''+j] = audio;
			}
		}
	}
	for (var i = 3; i <= 9; i++) {
		audio = game.add.audio('mj.card.'+i+'1');
		this.audioMap['mj.card.'+i+'1'] = audio;
	}
	audio = game.add.audio('mj.chi');
	this.audioMap['mj.chi'] = audio;
	audio = game.add.audio('mj.peng');
	this.audioMap['mj.peng'] = audio;
	audio = game.add.audio('mj.gang');
	this.audioMap['mj.gang'] = audio;
	audio = game.add.audio('mj.hu');
	this.audioMap['mj.hu'] = audio;
	audio = game.add.audio('mj.hua');
	this.audioMap['mj.hua'] = audio;
	
	audio = game.add.audio('click');
	this.audioMap['click'] = audio;
	audio = game.add.audio('uiclick');
	this.audioMap['uiclick'] = audio;
	audio = game.add.audio('discard');
	this.audioMap['discard'] = audio;
	audio = game.add.audio('lose');
	this.audioMap['lose'] = audio;
	audio = game.add.audio('tick');
	this.audioMap['tick'] = audio;
//	audio = game.add.audio('timeup');
//	this.audioMap['timeup'] = audio;
	audio = game.add.audio('win');
	this.audioMap['win'] = audio;
	
};

AudioManager.prototype.playServerId = function(serverId){

	var am = this;
	
	wx.downloadVoice({
		serverId: serverId, 
		isShowProgressTips: 0, // 默认为1，显示进度提示
		success: function(res){
			am.playLocalId(res.localId);
		}
	});
};
AudioManager.prototype.playLocalId = function(localId){
	
	if(!localStorage.rainAllowPlayVoice || localStorage.rainAllowPlayVoice !== 'true'){ //没有放声音的权限
		return ;
	}
	
	if(AudioManager.currentLocalId){
		wx.stopVoice({
			localId: AudioManager.currentLocalId 
		});
	}
	
	wx.playVoice({
		localId: localId 
	});
	
	AudioManager.currentLocalId = localId;
};


AudioManager.prototype.playByKey = function(key){
//	console.log('key['+key+']');
	if(this.audioMap[key]){
		this.audioMap[key].play();
	};
}

AudioManager.prototype.playByCard = function(number){
	var cn;
	if(number >= 0 && number <36){
		cn = '1'+(parseInt(number/4) + 1); 
	}else if(number >= 36 && number< 72){
		cn = '2'+(parseInt((number - 36 )/4)+1);
	}else if(number>=72 && number <108){
		cn = parseInt((number - 72)/4) + 1;
	}else if(number >= 108 && number < 136 ){
		cn = ((parseInt((number - 108)/4) + 3)+'1');
	}
	cn = 'mj.card.'+cn;
	this.playByKey(cn);
};


