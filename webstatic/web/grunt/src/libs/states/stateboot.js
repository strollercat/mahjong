function StateBoot() {
	
	var screenWidth = screen.width;
	var screenHeight = screen.height;
	var dpr = window.devicePixelRatio;
	
	this.setPhoneWorld =  function(){
		if (game.scale.isLandscape) {
			game.world.angle = 0;
			game.scale.setGameSize(dpr * screenHeight, dpr * (screenWidth - 64  ));
			game.world.setBounds(0, 0,dpr * screenHeight,dpr * (screenWidth - 64  ));
			if(game.gameSprite){
				game.gameSprite.layout();				
			}
		} else {
			game.scale.setGameSize(dpr * screenWidth,dpr * (screenHeight - 64));
			game.world.setBounds(0, -dpr * (screenHeight - 64), dpr* (screenHeight - 64),dpr * screenWidth);
			game.world.angle = -90;
			if(game.gameSprite){
				game.gameSprite.layout();				
			}
		}
	}
	
	this.setPadWorld =  function(){
		var worldWidth,worldHeight;
		if (game.scale.isLandscape) {
			game.world.angle = 0;
			worldWidth = screenHeight;
			worldHeight = worldWidth/16 *9;
			
			game.scale.setGameSize(dpr * screenHeight, dpr * screenWidth);
			game.world.setBounds(0, -(screenWidth - worldHeight - 64)/2,dpr * worldWidth,dpr * worldHeight);
			if(game.gameSprite){
				game.gameSprite.layout();				
			}
		} else {
			game.scale.setGameSize(dpr * screenWidth,dpr * (screenHeight - 64));
			game.world.setBounds(-dpr*3*(screenWidth - screenHeight/16*9)/2, -dpr * (screenHeight - 64), dpr* (screenHeight - 64),dpr * screenHeight/16*9);
			game.world.angle = -90;
			if(game.gameSprite){
				game.gameSprite.layout();				
			}
		}
	}
	
	this.init = function() {
		if (util.isWeixin()) {
			if(!util.ratio4to3()){
				game.scale.scaleMode = Phaser.ScaleManager.EXACT_FIT;
				game.scale.setUserScale(1 / dpr,1 / dpr);
				this.setPhoneWorld();
				game.scale.onOrientationChange.add(this.setPhoneWorld, this);
			}else{
				game.scale.scaleMode = Phaser.ScaleManager.USER_SCALE; 
				game.scale.setUserScale(1 / dpr,1 / dpr);
				this.setPadWorld();
				game.scale.onOrientationChange.add(this.setPadWorld, this);					
			}
		} else {
			game.world.setBounds(0,0,800,800/16 * 9);
		}
		game.config = {};
		
		
		
		game.resourcePrefix = '';
		
//		
	}


	this.preload = function() {
		game.stage.backgroundColor = '#005c75';
//		game.load.image('trademark','resources/images/trademark.jpg')
//		game.load.image('loadingCircle', 'resources/images/loading_image.png');
//		game.load.image('z_loading', 'resources/images/z_loading.png');
		
		game.load.atlas('loading', game.resourcePrefix +'resources/images/loading.png',
			game.resourcePrefix +'resources/images/loading.json');
	};
	this.create = function() {
		game.state.start('preload');
	};
};
