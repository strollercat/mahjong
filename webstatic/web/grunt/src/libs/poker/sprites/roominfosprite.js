function RoomInfoSprite(height){
	this.height = height;
	
	this.textOne;
	this.textTwo;
	this.textThree;
	this.textFour;
	this.textFive;
	
}
RoomInfoSprite.mjMap = null;
RoomInfoSprite.prototype = util.inherit(GroupSprite.prototype);
RoomInfoSprite.prototype.constructor = RoomInfoSprite;


RoomInfoSprite.prototype.layout = function(wh) {
	this.height = wh.h;
	
	this.textOne.layout({w:0,h:this.height,fh:this.height});
	this.textOne.setTextToLeft(this.textOne.getText());
	
	this.textTwo.layout({w:0,h:this.height,fh:this.height});
	this.textTwo.group.y = this.height;
	this.textTwo.setTextToLeft(this.textTwo.getText());
	
	this.textThree.layout({w:0,h:this.height,fh:this.height});
	this.textThree.group.y = 2 * this.height;
	this.textThree.setTextToLeft(this.textThree.getText());
	
	this.textFour.layout({w:0,h:this.height,fh:this.height});
	this.textFour.group.y = 3 * this.height;
	this.textFour.setTextToLeft(this.textFour.getText());
	
	this.textFive.layout({w:0,h:this.height,fh:this.height});
	this.textFive.group.y = 4 * this.height;
	this.textFive.setTextToLeft(this.textFive.getText());
};




RoomInfoSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	if(!RoomInfoSprite.mjMap){
		RoomInfoSprite.mjMap = {};
		RoomInfoSprite.mjMap['ningboMajiang'] = '宁波麻将';
		RoomInfoSprite.mjMap['fenhuaMajiang'] = '奉化麻将';
	}
	
	this.textOne = new LineTextSprite(0,0,0,'#FFA500');
	this.textOne.create();
	this.group.add(this.textOne.group);
	
	this.textTwo = new LineTextSprite(0,0,0,'#FFA500');
	this.textTwo.create();
	this.group.add(this.textTwo.group);
	
	this.textThree = new LineTextSprite(0,0,0,'#FFA500');
	this.textThree.create();
	this.group.add(this.textThree.group);
	
	this.textFour = new LineTextSprite(0,0,0,'#FFA500');
	this.textFour.create();
	this.group.add(this.textFour.group);
	
	this.textFive = new LineTextSprite(0,0,0,'#FFA500');
	this.textFive.create();
	this.group.add(this.textFive.group);
	
};

RoomInfoSprite.prototype.getRoomInfoString = function(roomInfo){
	this.mj = roomInfo.name;
	var message = [];
	if(this.mj == 'threeWater'){
		this.ju = roomInfo.totalJu;
		this.ren = roomInfo.playerNum;
		
		message.push('十三水:'+'共'+this.ju+'局');
		message.push('人數:'+this.ren);
	}else if(this.mj == 'ningboMajiang'){
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
		
//		this.textOne.setTextToLeft('杭州麻将 '+'共'+ju+'局');
//		this.textTwo.setTextToLeft(ren+'人局 '+(baiBanBaida?'白板财神':'翻财神'));
//		this.textThree.setTextToLeft((sanLao?'三老庄 ':'平庄起 ')+(sanTan?'三滩承包(庄闲)':'不可吃三滩'));
//		this.textFour.setTextToLeft((zimoHu?'自摸胡 ':'庄闲放冲(三老) ')+(baidaHaoqi?'财神替豪七杠子 ':''));
//		this.textFive.setTextToLeft((pengTan?'碰算滩 ':'碰不算滩 ')+(baidaiKaoxiang?'有财必拷响':'')+(laoShu?'本局'+laoShu+'老':''));
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
	}
	return message;
}


RoomInfoSprite.prototype.setRoomInfo = function(roomInfo){
	this.mj = roomInfo.name;
	if(this.mj == 'threeWater'){
		this.ju = roomInfo.totalJu;
		this.ren = roomInfo.playerNum;
		this.textOne.setTextToLeft('十三水:共'+this.ju+"局");
		this.textTwo.setTextToLeft('人数:'+this.ren); 
	}else if(this.mj == 'ningboMajiang'){
		this.baida = roomInfo.baida;
		this.tai = roomInfo.startFan;
		this.ju = roomInfo.totalJu;
		this.ren = roomInfo.playerNum;
		this.quan = roomInfo.quan;
		this.hunpengqing = roomInfo.hunpengqing;
		this.jinHua = roomInfo.jinhua;
		this.yeHua = roomInfo.yehua;
		
		
		this.textOne.setTextToLeft(RoomInfoSprite.mjMap[this.mj] +' '+this.baida+'百搭');
		this.textTwo.setTextToLeft(this.tai+'台起胡'+' 共'+this.ju+'局'); 
		this.textThree.setTextToLeft('金花'+this.jinHua+'野花'+this.yeHua+(this.hunpengqing?'清混碰':''));
		var renQuan = this.ren+ '人局  ';
		if(this.quan == 0){
			renQuan+='东风圈'; 
		}else if(this.quan ==1){
			renQuan+='南风圈'; 
		}else if(this.quan ==2){
			renQuan+='西风圈'; 
		}else if(this.quan == 3){
			renQuan+='北风圈'; 
		}
		this.textFour.setTextToLeft(renQuan);
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
		this.textOne.setTextToLeft('奉化麻将 '+ (roomInfo.pinghu? '平搓':'冲刺'));
		if(roomInfo.pinghu){
			this.textTwo.setTextToLeft('共'+ju+'局');
			this.textThree.setTextToLeft(renQuan);
		}else{
			this.textTwo.setTextToLeft(renQuan);
		}
	}else if(this.mj == 'tiantaiMajiang'){
		var ju = roomInfo.totalJu;
		var hushu = roomInfo.maxFan;
		var hasBaida = roomInfo.hasBaida;
		this.textOne.setTextToLeft('天台麻将 '+'共'+ju+'局');
		this.textTwo.setTextToLeft(hushu+'胡 '+(hasBaida?'4百搭':'无百搭'));
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
		
		this.textOne.setTextToLeft('杭州麻将 '+'共'+ju+'局');
		this.textTwo.setTextToLeft(ren+'人局 '+(baiBanBaida?'白板财神':'翻财神'));
		this.textThree.setTextToLeft((sanLao?'三老庄 ':'平庄起 ')+(sanTan?'三滩承包(庄闲)':'不可吃三滩'));
		this.textFour.setTextToLeft((zimoHu?'自摸胡 ':'庄闲放冲(三老) ')+(baidaHaoqi?'财神替豪七杠子 ':''));
		this.textFive.setTextToLeft((pengTan?'碰算滩 ':'碰不算滩 ')+(baidaiKaoxiang?'有财必拷响':'')+(laoShu?'本局'+laoShu+'老':''));
	}
	
	this.textOne.group.visible = false;
	this.textTwo.group.visible = false;
	this.textThree.group.visible = false;
	this.textFour.group.visible = false;
	this.textFive.group.visible = false;
	
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
