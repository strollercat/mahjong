$(document).ready(function () {
	
	game = new Phaser.Game(screen.width,screen.height, Phaser.CANVAS);
	
	wx.ready(function(){
		wx.hideAllNonBaseMenuItem();
	});
	
	game.state.add('boot', StateBoot);
	game.state.add('preload', StateRecordPreload);
	game.state.add('game', StateRecordGame);

	game.state.start('boot');
	
	var postData = {};
	postData.roomId = util.getQueryString('roomId');
	if(util.env == 'dev'){
		postData.code =  util.getQueryString('code');
	}
	$.ajax({
        url: "/web/checkRecord.do",
        type:"POST",
        data: postData,
        success: function (data) {
            if(data.errCode == "000000"){
            	RecordReceiver.msgQueue = data.body;
				StateRecordPreload.dataLoaded = true;
            }else  if(data.errCode == "000001"){
               	StateRecordPreload.dataResult = false;
            }
        }
    });
});


