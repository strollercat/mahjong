function RoomInfoSprite(height){
	this.height = height;
}
RoomInfoSprite.mjMap = null;
RoomInfoSprite.prototype = util.inherit(GroupSprite.prototype);
RoomInfoSprite.prototype.constructor = RoomInfoSprite;


RoomInfoSprite.prototype.layout = function(wh) {
	this.height = wh.h;
};




RoomInfoSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	if(!RoomInfoSprite.mjMap){
		RoomInfoSprite.mjMap = {};
		RoomInfoSprite.mjMap['ningboMajiang'] = '宁波麻将';
		RoomInfoSprite.mjMap['fenhuaMajiang'] = '奉化麻将';
	}
	
};

RoomInfoSprite.prototype.getRoomInfoString = function(roomInfo){
	this.mj = roomInfo.name;
	var message = [];
	
	if(this.mj == 'ningboMajiang'){
		this.baida = roomInfo.baida;
		this.tai = roomInfo.startFan;
		this.ju = roomInfo.totalJu;
		this.ren = roomInfo.playerNum;
		this.quan = roomInfo.quan;
		this.hunpengqing = roomInfo.hunpengqing;
		this.jinHua = roomInfo.jinhua;
		this.yeHua = roomInfo.yehua;
		message.push(RoomInfoSprite.mjMap[this.mj] +' '+this.baida+'百搭' );
		message.push(this.tai+'台起胡'+' 共'+this.ju+'局' );
		message.push('金花'+this.jinHua+'野花'+this.yeHua+(this.hunpengqing?' 清混碰':''));
	}else if(this.mj == 'fenhuaMajiang'){
		var ju = roomInfo.totalJu;
		var ren = roomInfo.playerNum;
		var quan = roomInfo.quan;
		var renQuan = ren +'人局 ';
		if(quan == 0){
			renQuan+='东风圈'; 
		}else if(quan ==1){
			renQuan+='南风圈'; 
		}else if(quan ==2){
			renQuan+='西风圈'; 
		}else if(quan == 3){
			renQuan+='北风圈'; 
		}
		message.push('奉化麻将 '+ (roomInfo.pinghu? '平搓':'冲刺'));
		message.push('共'+ju+'局');
	}else if(this.mj == 'tiantaiMajiang'){
		var ju = roomInfo.totalJu;
		var hushu = roomInfo.maxFan;
		var hasBaida = roomInfo.hasBaida;
		
		message.push('天台麻将 '+'共'+ju+'局');
		message.push(hushu+'胡 '+(hasBaida?'4百搭':'无百搭'));
	}else if(this.mj == 'hangzhouMajiang'){
		var ju = roomInfo.totalJu;
		var ren = roomInfo.playerNum;
		var baiBanBaida = roomInfo.baiBanBaida;
		var sanLao  = roomInfo.sanLao; 
		var sanTan  = roomInfo.sanTan; 
		var zimoHu = roomInfo.zimoHu;
		var baidaiKaoxiang = roomInfo.baidaiKaoxiang;
		var pengTan = roomInfo.pengTan; 
		var baidaHaoqi = roomInfo.baidaHaoqi ;
		var laoShu = roomInfo.laoShu;
		
		message.push((sanLao?'三老庄 ':'平庄起 ')+(sanTan?'三滩承包(庄闲)':'不可吃三滩'));
		message.push((zimoHu?'自摸胡 ':'庄闲放冲(三老) ')+(baidaHaoqi?'财神替豪七杠子 ':''));
		message.push((pengTan?'碰算滩 ':'碰不算滩 ')+(baidaiKaoxiang?'有财必拷响':'')+(laoShu?'本局'+laoShu+'老':''));
		
	}else if(this.mj == 'hongzhongMajiang'){
		var ju = roomInfo.totalJu;
		var diScore = roomInfo.diScore;
		
		message.push('红中麻将 '+'共'+ju+'局');
		message.push('底分 '+diScore);
	}else if(this.mj== 'guangdongMajiang'){
		var ju = roomInfo.totalJu;
		var diScore = roomInfo.diScore;
		
		message.push('广东麻将 '+'共'+ju+'局');
		message.push('底分 '+diScore);
	}else if(this.mj == 'xiangshanMajiang'){
		var ju = roomInfo.totalJu;
		var ren = roomInfo.playerNum;
		
		message.push('象山麻将 '+'共'+ju+'局');
		message.push('人数 '+ ren +'人');
	}
	return message;
}


RoomInfoSprite.prototype.setRoomInfo = function(roomInfo){
	this.message = this.getRoomInfoString(roomInfo);
};

RoomInfoSprite.test = function(){
	var ri = new RoomInfoSprite(0);
	ri.create();
//	ri.layout({h:20});
	var roomInfo = {};
	roomInfo.name = 'ningboMajiang';
	roomInfo.baida = 7;
	roomInfo.startFan = 4;
	roomInfo.totalJu = 8;
	roomInfo.playerNum = 4;
	roomInfo.quan = 1;
	ri.setRoomInfo(roomInfo);
	ri.layout({h:20});
}
