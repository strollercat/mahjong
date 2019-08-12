function ButtonSprite(width,height,image1,image2,callback,context){
	this.width = width;
	this.height = height;
	this.image1 = image1;
	this.image2 = image2;
	this.callback = callback;
	this.context = context;
	
	this.image = null;
	
	this.button;
}
ButtonSprite.prototype = util.inherit(GroupSprite.prototype);
ButtonSprite.prototype.constructor = ButtonSprite;

ButtonSprite.prototype.layout = function(wh){
	this.button.width = wh.w;
	this.button.height = wh.h;
};

ButtonSprite.prototype.create = function() {
	GroupSprite.prototype.create.apply(this, arguments);
	this.button = game.add.button(0, 0, this.image?this.image:'buttons', function(btn){
		btn.context = this.context;
		this.callback(btn);
	}, this,this.image1, this.image1, this.image2);
	this.group.add(this.button);
	this.button.onInputDown.add(function(btn){
    	if(game.audioManager){
			game.audioManager.playByKey('click');
		}
    }, this);
}
ButtonSprite.test = function(){
	var readyBtnSprite = new ButtonSprite(0,0,'ready_normal','ready_press',function(btn){
			console.log('click');
		},this);
	readyBtnSprite.create();
	
	readyBtnSprite.layout({w:100,h:20});
}