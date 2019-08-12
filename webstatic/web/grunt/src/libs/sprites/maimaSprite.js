function MaimaSprite(){

	this.doneCallBack;
	
	this.cardsGroup;
	this.cardsArr = [];
	
	this.blackCardsArr = [];
}

MaimaSprite.prototype = util.inherit(GroupSprite.prototype);
MaimaSprite.prototype.constructor = MaimaSprite;

MaimaSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.bgSprite = game.add.image(0,0,'other');
	this.bgSprite.frameName='yellowBg';
	this.bgSprite.alpha = 0.2;
	this.group.add(this.bgSprite);
	
	this.btnConfirm = new ButtonSprite(0,0,'confirm_normal','confirm_press',function(btn){
		btn.context.group.visible = false;
		btn.context.doneCallBack();
	},this);
	this.btnConfirm.create();
	this.group.add(this.btnConfirm.group);
	
	this.titleSprite = new LineTextSprite(0,0,0,'#ffff00');
	this.titleSprite.create();
	var text = this.titleSprite.textSprite;
//		text.align = 'center';
	text.font = 'Arial Black';
    text.fontWeight = 'bold';
    text.setShadow(5, 5, 'rgba(0,0,0,0.5)', 10);
    text.stroke = '#000000';
    text.strokeThickness = 3;
	this.titleSprite.setText("买    马");
	this.group.add(this.titleSprite.group);
	
	
	this.cardsGroup = game.add.group();
	this.group.add(this.cardsGroup);
	
	
	

	this.blackCardsArr = [];
	for(var i = 0;i< 6;i++){
		this.blackCardsArr[i] = new CardSprite(0, 3, -1, 0);
		this.blackCardsArr[i].create();
		this.group.add(this.blackCardsArr[i].group);
	}
}


MaimaSprite.prototype.layout = function(wh){
	this.width = wh.w;

	var cardWidth = this.width/7;
	var cardHeight = CardSprite.getHeightByWidth(0,cardWidth);
	
	this.cardWidth = cardWidth;
	this.cardHeight = cardHeight;
	
	this.height =3 * cardHeight;
	
	this.bgSprite.width = this.width;
	this.bgSprite.height = this.height;
	
	var btnWidth = this.width/3;
	var btnHeight = btnWidth/275 * 108;
	this.btnConfirm.layout({w:btnWidth,h:btnHeight});
	this.btnConfirm.group.x = (this.width - btnWidth)/2;
	this.btnConfirm.group.y = 2*cardHeight +(cardHeight - btnHeight)/3;
	
	var fh = cardHeight/2;
	this.titleSprite.layout({w:this.width,h:fh,fh:fh});
	this.titleSprite.setText(this.titleSprite.getText());
	this.titleSprite.group.y = cardHeight/4;
	
	if(this.cardsArr && this.cardsArr.length >0){
		for(var i = 0;i<this.cardsArr.length;i++){
			this.cardsArr[i].layout({w:cardWidth});
			this.cardsArr[i].group.x = i* cardWidth;
			this.cardsArr[i].group.y = cardHeight;
		}
		this.cardsGroup.x = this.getCardOffx(this.cardsArr.length);
	}
	
	
	for(var i = 0;i< 6;i++){
		this.blackCardsArr[i].layout({w:cardWidth});
		this.blackCardsArr[i].group.y = cardHeight;
	}
	
}

MaimaSprite.prototype.getCardOffx = function(size){
	var offx;
	if(size == 4){
		offx = 1.5*this.cardWidth;
	}else{
		offx = 0.5 * this.cardWidth;
	}
	return offx;
}
MaimaSprite.prototype.showMaima = function(maCards,doneCallBack){
	this.group.visible = true;
	this.doneCallBack = doneCallBack;
	
	var offx = this.getCardOffx(maCards.length);
	this.cardsGroup.x = offx;
	this.cardsGroup.y = this.cardHeight;
	
//	for(var i = 0;i< maCards.length;i++){
//		var card = maCards[i];
//		this.cardsArr[i] = new CardSprite(0, 1, card.cn, 0);
//		this.cardsArr[i].create();
//		this.cardsGroup.add(this.cardsArr[i].group);
//		if(card.zhong){
//			this.cardsArr[i].mask('0xFFFF00');
//		}
//		this.layout({w:this.width});
//	}
	
	this.blackCardsTween(0,maCards.length,maCards);
	
}
MaimaSprite.prototype.blackCardsTween = function(i,size,maCards){
//	console.log('tween');
	var bc = this.blackCardsArr[i];
	var offx = this.getCardOffx(size);
	
	bc.group.visible = true;
	bc.group.x = 0;
	
	
	var tween = game.add.tween(bc.group);
	var x = offx+i * this.cardWidth;
	tween.to({x:x}, 1500, Phaser.Easing.Linear.None, false,
			0, 0, false);
	var index = i;
	tween.onComplete.add(function(tween) {
		bc.group.visible = false;
		
//		console.log(index);
		var card = maCards[index];
		this.cardsArr[index] = new CardSprite(0, 0, card.cn, 0);
		this.cardsArr[index].create();
		this.cardsArr[index].layout({w:this.cardWidth})
		this.cardsArr[index].group.x = index * this.cardWidth;
//		this.cardsArr[index].group.y = this.cardHeight;
		this.cardsGroup.add(this.cardsArr[index].group);
		if(card.zhong){
			this.cardsArr[i].mask('0xFFFF00');
		}
		if(index == size -1){
			this.btnConfirm.group.visible = true;
			return ;
		}
		this.blackCardsTween(index+1,size,maCards);
	}, this);
	tween.start();
}
MaimaSprite.prototype.clear = function(){
	this.group.visible = false;
	if(this.cardsArr && this.cardsArr.length >0){
		for(var i = 0;i< this.cardsArr.length;i++){
			this.cardsGroup.remove(this.cardsArr[i].group);
			this.cardsArr[i].group.destroy();
		}
	}
	
	this.cardsArr = [];
	for(var i = 0;i< this.blackCardsArr.length;i++){
		this.blackCardsArr[i].group.visible = false;
	}
	this.btnConfirm.group.visible = false;
	
}

MaimaSprite.test = function(){
	var maimaS =new MaimaSprite();
	maimaS.create();
	maimaS.layout({w:game.world.width/2});
	maimaS.clear();
	maimaS.group.y = (game.world.height - maimaS.height)/2;
	maimaS.showMaima([{cn:1,zhong:true},{cn:2,zhong:true},{cn:3,zhong:true},{cn:4,zhong:true}],function(){
		console.log('click');
	})
//	maimaS.clear();
//	maimaS.showMaima([{cn:1,zhong:true},{cn:2,zhong:true},{cn:3,zhong:true},{cn:4,zhong:true},{cn:5,zhong:true},{cn:6,zhong:true}],function(){
//		console.log('click');
//	})
}