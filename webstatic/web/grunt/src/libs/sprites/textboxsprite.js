function TextBoxSprite(textArr){
	this.mbs;
	
	this.ltsArr ;
	this.textArr = textArr;
}
TextBoxSprite.prototype = util.inherit(GroupSprite.prototype);
TextBoxSprite.prototype.constructor = TextBoxSprite;

TextBoxSprite.prototype.layout = function(wh){
	var width = game.world.width/2;
	this.mbs.layout({w:width,h:0});
	var height = this.mbs.height;
	
	var textWidth = width;
	var textHeight = height/6;
	var totalHeight = this.textArr.length * textHeight;
	var offy = height /2 -totalHeight/2 -0.5* textHeight;
//	console.log(offy);
	for(var i = 0;i<this.textArr.length;i++){
		this.ltsArr[i].layout({w:textWidth,h:textHeight,fh:textHeight});
		this.ltsArr[i].setText(this.textArr[i]);
		this.ltsArr[i].group.y = offy + i*textHeight;	}	
	this.group.x = game.world.width/2 -width/2;
	this.group.y = game.world.height/2 - height/2;
	
}

TextBoxSprite.prototype.create = function(){
	GroupSprite.prototype.create.apply(this, arguments);
	
	this.mbs = new MessageBoxSprite(0,0,function(btn){
		btn.context.group.visible = false;
	},this);
	this.mbs.create();
	this.mbs.setTop('hint');
	this.group.add(this.mbs.group);
	
	this.ltsArr = [];
	for(var i = 0 ;i<this.textArr.length;i++){
		this.ltsArr[i] = new LineTextSprite(0,0,0,'#ffffff');
		this.ltsArr[i].create();
		this.group.add(this.ltsArr[i].group);
	}
}
TextBoxSprite.test = function(){
	var ibs = new TextBoxSprite(['尼玛']);
	ibs.create();
	ibs.layout(null);
	
	setTimeout(function(){ibs.layout()},2000);
}