$(document).ready(function () {
	
	
//	alert('start');
	
	game = new Phaser.Game(screen.width,screen.height, Phaser.CANVAS);
	
	webSocketClient = new WebSocketClient();
	webSocketClient.connect();
	
	
	weixinShareReady();
	
	game.state.add('boot', StateBoot);
	game.state.add('preload', StatePreload);
	game.state.add('game', StateGame);
	if(util.env == 'dev'){
		game.state.add('test', StateTest);
	}
	
	game.state.start('boot');
});




//document.addEventListener("visibilitychange", function(){
//    $.post("/web/heartBreak.do",{url:window.top.location.href},function(result){
//    });
//});
//window.addEventListener("popstate", function(e) { 
//    $.post("/web/heartBreak.do",{url:window.top.location.href},function(result){
//    	});
//}, false); 

function weixinShareReady(){
	
	$.post("/web/getJsSdkSign.do",{url:window.top.location.href},function(result){
		wx.config({
			'debug': false,
			'appId': result.body.appId,
			'timestamp': result.body.timestamp,
			'nonceStr': result.body.nonceStr,
			'signature': result.body.signature,
			'jsApiList': [
				'checkJsApi',
				'onMenuShareTimeline',
				'onMenuShareAppMessage',
				'onMenuShareQQ',
				'onMenuShareWeibo',
				'onMenuShareQZone',
				'hideMenuItems',
				'showMenuItems',
				'hideAllNonBaseMenuItem',
				'showAllNonBaseMenuItem',
				'translateVoice',
				'startRecord',
				'stopRecord',
				'onVoiceRecordEnd',
				'playVoice',
				'onVoicePlayEnd',
				'pauseVoice',
				'stopVoice',
				'uploadVoice',
				'downloadVoice',
				'chooseImage',
				'previewImage',
				'uploadImage',
				'downloadImage',
				'getNetworkType',
				'openLocation',
				'getLocation',
				'hideOptionMenu',
				'showOptionMenu',
				'closeWindow',
				'scanQRCode',
				'chooseWXPay',
				'openProductSpecificView',
				'addCard',
				'chooseCard',
				'openCard'
			]
		});
	});

	game.shareData = {
	    title: '',
	    desc: '',
	    link: '',
	    imgUrl: '',
	    originDesc:''
	}; 
	game.updateShareDataOfDesc = function(playerNum){
		if(playerNum <=1 || playerNum >=4){
			game.shareData.desc = game.shareData.originDesc;
		}else if(playerNum == 2){
			game.shareData.desc = '二缺二中,'+game.shareData.originDesc;
		}else if(playerNum == 3){
			game.shareData.desc = '三缺一中,'+game.shareData.originDesc;
		}
	};
	game.updateShareData = function(msg){
		var host ='';
		var ishttps = 'https:' == document.location.protocol ? true : false;
		if(ishttps){
			host = 'https://'+ window.location.host;
		}else{
			host ='http://'+ window.location.host;
		}
		host += ('/web/enterRoom.do?recommend='+util.getQueryString('recommend'));
		if(util.env == 'dev'){
			host+= ('&code='+util.getQueryString('code'));
		}
		host+=('&roomId='+msg.roomId);
		game.shareData.link = host;
//		alert('host:'+host);
		
		var userName;
		
		for(var i = 0;i< msg.users.length;i++){
			if(msg.users[i].dir == msg.author){
				game.shareData.imgUrl = msg.users[i].url;
				game.shareData.desc = msg.users[i].name+'邀请您来一局,赶紧进来较量下';
				game.shareData.originDesc = game.shareData.desc;
				userName = msg.users[i].name;
				break;
			}
		}
		
		if(msg.roomInfo.name == 'threeWater'){
			var title ='十三水';
			document.title=title;
			title += (msg.roomInfo.totalJu+'局-'+msg.roomInfo.playerNum+'人-房间号'+msg.roomId);
			game.shareData.title = title;
			
			var desc='';
			desc+=(userName+'邀请您来一局,赶紧进来较量下');
//			if(msg.roomInfo.playerNum == 2){
//				desc+=",缺一色";
//			}
//			desc+=(msg.roomInfo.hunpengqing?',清混碰':'');
//			desc+=(',金花'+msg.roomInfo.jinhua+'野花'+msg.roomInfo.yehua+"\r\n");
//			desc+=msg.roomInfo.startFan +'台起胡';
			
			game.shareData.desc = desc;
			game.shareData.originDesc = desc;
		}
	}
	
	
	
	
	
	var msgQueue = webSocketClient.msgQueue;
	if(msgQueue && msgQueue.length > 0){
		for(var i = 0;i< msgQueue.length;i++){
			var message =  msgQueue[i];
			if(message.code == 'roomNotice' || message.code == 'gameNotice'){ 
				game.updateShareData(message);
				break;
			}
		}
	}
	
	
	if(typeof wx != 'undefined'){
		wx.ready(function(){
//			alert('wx ready!!!!!');
			wx.hideAllNonBaseMenuItem();
			var menuList;
		    if(util.env == 'prod'){
		    	menuList = ["menuItem:share:appMessage",
								"menuItem:share:timeline"];
		    }else{
		    	menuList = ["menuItem:share:appMessage",
								"menuItem:share:timeline",
								"menuItem:copyUrl"];
		    }
		    menuList = ["menuItem:share:appMessage",
								"menuItem:share:timeline",
								"menuItem:copyUrl"];
			wx.showMenuItems({
				menuList: menuList
			});
		 	wx.onMenuShareAppMessage(game.shareData);
			wx.onMenuShareTimeline(game.shareData);
			
			
			wx.checkJsApi({
				jsApiList: ['startRecord','playVoice'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
				success: function(res) {
				// 以键值对的形式返回，可用的api值true，不可用为false
				// 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
//   					alert(res.checkResult.startRecord+' '+res.checkResult.playVoice);
					if(res.checkResult.startRecord === true){
						if(!localStorage.rainAllowRecord || localStorage.rainAllowRecord !== 'true'){
    						wx.startRecord({
   								success: function(){
            						localStorage.rainAllowRecord = 'true';
            						wx.stopRecord();
//	            						alert('用户接受录音');
        						},
        						cancel: function () {
//	            						alert('用户拒绝授权录音');
        						}
    						});
						}
					}
					
					if(res.checkResult.playVoice === true){
//						alert('has playVoice right');
						localStorage.rainAllowPlayVoice = 'true';
						wx.onVoicePlayEnd({
							success: function (res) {
								var localId = res.localId; // 返回音频的本地ID
								AudioManager.currentLocalId = null;
							}
						});
					}
				}
			});
			
			
			
			
//			if(!localStorage.rainAllowRecord || localStorage.rainAllowRecord !== 'true'){
//	    		wx.startRecord({
//	   				success: function(){
//	            		localStorage.rainAllowRecord = 'true';
//	            		wx.stopRecord();
////	            		alert('用户接受录音');
//	        		},
//	        		cancel: function () {
////	            		alert('用户拒绝授权录音');
//	        		}
//	    		});
//			}
			
			
			
//			wx.getLocation({
//				type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
//				success: function (res) {
//					var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
//					var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
//					var speed = res.speed; // 速度，以米/每秒计
//					var accuracy = res.accuracy; // 位置精度
//					$.post("/web/getLocation.do",{latitude:latitude,longitude:longitude,speed:speed,accuracy:accuracy},function(result){
//    				
//    				});
//				}
//			});
		});
		
		wx.error(function(res){
    		alert("登陆失败,请重新登陆");
		});
		
		
	}
};
