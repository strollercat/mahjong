function BulletAnimateSprite(){
	this.width;
	this.frameArr;
};
BulletAnimateSprite.prototype.layout = function(wh){
	this.width = wh.w;
}
BulletAnimateSprite.prototype.create = function() {
	var array,i,j;
	array = [];
	array[0]=14;
	array[1]=13;
	array[2]=18;
	array[3]=8;
	array[4]=15;
	array[5]=11;
	array[6]=15;
	array[7]=11;
	array[8]=8;
	array[9]=10;
	this.frameArr = [];
	for(i = 0;i<10;i++){
		var arr = [];
		for(j = 0;j<array[i];j++){
			arr.push('item'+(i+1)+'_'+(j+1)+'.png');
		}
		this.frameArr.push(arr);
	}
}

BulletAnimateSprite.prototype.bullet = function(authorPos,whoPos,index){
	
	var bullet = game.add.image(0,0,'animationItem');
	bullet.frameName = 'item'+(index+1)+'_1.png';
	bullet.width = this.width;
	if(index == 6){
		bullet.height = this.width/76 * 24;
	}else{
		bullet.height = this.width;
	}
	bullet.x = authorPos.x;
	bullet.y = authorPos.y;
	
	
	var tween = game.add.tween(bullet);
	tween.to(whoPos, 1200, Phaser.Easing.Linear.None, false,
			0, 0, false);
	tween.onComplete.add(function(tween) {
		bullet.destroy();
		var animationSprite = game.add.image(0,0,'animationItem');
		animationSprite.width = this.width;
		animationSprite.height = this.width;
		animationSprite.x = whoPos.x;
		animationSprite.y = whoPos.y;
		animationSprite.animations.add('animate',this.frameArr[index]);
		animationSprite.animations.play('animate',5,false,true);
	}, this);
	tween.start();
};

BulletAnimateSprite.test = function(){
	var bas = new BulletAnimateSprite();
	bas.create();
	bas.layout({w:game.world.width/17});
	bas.bullet({x:0,y:0},{x:game.world.width/2,y:game.world.height/2},3);	
}