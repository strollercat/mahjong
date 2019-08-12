function StateBoot() {

	var screenWidth = screen.width;
	var screenHeight = screen.height;
	var dpr = window.devicePixelRatio;

	this.setPhoneWorld = function() {
		if (game.scale.isLandscape) { // 水平状态
			game.world.angle = 0;
			game.scale
					.setGameSize(dpr * screenHeight, dpr * (screenWidth - 64));
			game.world.setBounds(0, 0, dpr * screenHeight, dpr
					* (screenWidth - 64));
			if (game.gameSprite) {
				game.gameSprite.layout();
			}
		} else { // 垂直状态
			game.scale
					.setGameSize(dpr * screenWidth, dpr * (screenHeight - 64));
			game.world.setBounds(0, 0, dpr * screenWidth, dpr
					* (screenHeight - 64));
			if (game.gameSprite) {
				game.gameSprite.layout();
			}
		}
	}

	this.setPadWorld = function() {
		var worldWidth, worldHeight;
		if (game.scale.isLandscape) {
			game.scale
					.setGameSize(dpr * screenWidth, dpr * (screenHeight - 64));
			game.world.setBounds(-dpr * 3
					* (screenWidth - screenHeight / 16 * 9) / 2, -dpr
					* (screenHeight - 64), dpr * (screenHeight - 64), dpr
					* screenHeight / 16 * 9);
			game.world.angle = -90;
			if (game.gameSprite) {
				game.gameSprite.layout();
			}
		} else {
			game.world.angle = 0;
			var worldHeight = screenHeight;
			var worldWidth = worldHeight / 9 * 16;

			game.scale.setGameSize(dpr * screenWidth, dpr * screenHeight);
			game.world.setBounds(-(screenWidth - worldWidth) / 2, 0, dpr
					* worldWidth, dpr * (worldHeight - 64));
			if (game.gameSprite) {
				game.gameSprite.layout();
			}
		}
	}

	this.init = function() {
		if (util.isWeixin()) {
			if (!util.ratio4to3()) {
				game.scale.scaleMode = Phaser.ScaleManager.EXACT_FIT;
				game.scale.setUserScale(1 / dpr, 1 / dpr);
				this.setPhoneWorld();
				game.scale.onOrientationChange.add(this.setPhoneWorld, this);
			} else {
				game.scale.scaleMode = Phaser.ScaleManager.USER_SCALE;
				game.scale.setUserScale(1 / dpr, 1 / dpr);
				this.setPadWorld();
				game.scale.onOrientationChange.add(this.setPadWorld, this);
			}
		} else {
			game.world.setBounds(0, 0, 400 / 16 * 9, 400);
		}

	}

	this.preload = function() {
		game.stage.backgroundColor = '#005c75';
		game.load.atlas('loading', 'resources/images/loading.png',
				'resources/images/loading.json');
	};
	this.create = function() {
		game.state.start('preload');
	};
};
