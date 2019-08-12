$(document).ready(function() {
	
//	weixinShare();
	
	var height = $('.weui-tab__bd').height() - $('.weui-tabbar').height();
	$('.weui-tab__bd').height(height);
	$('#adviceAgentPay').hide();
	
	$('#roomLink').on('click',function(){
		$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
		$('#tab3').addClass('weui-tab__bd-item--active');
		$('.weui-bar__item--on').removeClass('weui-bar__item--on');
	});
	$('#roomLink1').on('click',function(){
		$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
		$('#tab3').addClass('weui-tab__bd-item--active');
		$('.weui-bar__item--on').removeClass('weui-bar__item--on');
	});
	
	initChoose();
	
//	gong gao lan
	var padLeft = 0;
	$('#notification').attr('style','padding-left:0px');
	setInterval(function(){
		$('#notification').attr('style','padding-left:'+(padLeft++)+'px');
		if(padLeft >= document.body.offsetWidth -30){
			padLeft= 0 ;
		}
	},40);
	
	$('#divChooseJu').change(function(){
		showTitle();
	});
	$('#divChooseFhType').change(function(){
		var type = $('[name="selectChooseFhType"] option:selected').text();
		if(type == '平搓'){
			$('#divChooseJu').show();
		}else if(type == '冲刺'){
			$('#divChooseJu').hide();
		}
	});

	$('.weui-tabbar__item').on('click',function(){
		var id = $(this).attr('href');
		if(id == '#tab2'){
			zhanjiView();
		}else if(id == '#tab4'){
			faxianView();
		}else if(id == '#tab5'){
			$('#adviceName').val('');
			$('#advicePhone').val('');
			$('#adviceContent').val('');
		}else if(id == '#tab3'){
			homeView();
		}
	});
	
	$('.diamond').on('click',function(){
		var totalFee = $(this).attr("data-totalfee");
		totalFee = totalFee * 100;
		var postData = getPostData();
		postData.totalFee = totalFee;
    	$.showLoading("正在加载...");
    	$.ajax({
		        url: "/web/prePay.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
						if (typeof WeixinJSBridge == "undefined") {
							if (document.addEventListener) {
								document.addEventListener(
										'WeixinJSBridgeReady',
										onBridgeReady, false);
							} else if (document.attachEvent) {
								document.attachEvent('WeixinJSBridgeReady',
										onBridgeReady);
								document.attachEvent(
										'onWeixinJSBridgeReady',
										onBridgeReady);
							}
						} else {
							onBridgeReady(data.body);
						}
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	$('#threeWaterChoose').on('click',function(){
		threeWaterView();
	});
	$('#nbChoose').on('click',function(){
		ningboMajiangView();
	});
	$('#fhChoose').on('click',function(){
		fenhuaMajiangView();
	});
	$('#ttChoose').on('click',function(){
		tiantaiMajiangView();
	});
	$('#hzChoose').on('click',function(){
		hangzhouMajiangView();
	});
	$('#hongzhongChoose').on('click',function(){
		hongzhongMajiangView();
	});
	$('#guangdongChoose').on('click',function(){
		guangdongMajiangView();
	});
	
	$('#xiangshanChoose').on('click',function(){
		xiangshanMajiangView();
	});
	
	
	
	
	
	
	
	
	
	
	
	$('#adviceFeed').on('click',function(){
		$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
		$('#tab_advice').addClass('weui-tab__bd-item--active');
	});
	
	$('#adviceRelogin').on('click',function(){
//		var reUrl = getRedirectUrl('home.html');
//		window.location.href = reUrl;
		$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
		$('#tab3').addClass('weui-tab__bd-item--active');
		$('.weui-bar__item--on').removeClass('weui-bar__item--on');
	});
	
	$('#adviceAgentPay').on('click',function(){
		var reUrl = getRedirectUrl('agent.html');
		window.location.href = reUrl;
	});
	
	
		
	$('#adviceSubmit').on('click',function(){
		var postData = getPostData();
		postData.type = $('[name="selectAdviceType"] option:selected').text();
		postData.gameType = $('[name="selectAdviceGameType"] option:selected').text();
		postData.name = $('#adviceName').val();
		postData.phone = $('#advicePhone').val();
		postData.advice = $('#adviceContent').val();
		if(!postData.name){
			$.toptip('请输入您的称呼', 'warning');
			return ;
		}
    	if(!postData.advice){
    		$.toptip('请输入您的问题', 'warning');
			return ;
    	} 
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/giveAdvice.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
						$.alert('您的反馈已提交');
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	$('#chooseSubmit').on('click',function(){
		var title = $('#chooseTitle').html();
		
		var postData = getPostData();
		postData.recommend = util.getQueryString('recommend');
		if(localStorage.getItem('specialid')){
			postData.recommend = localStorage.getItem('specialid');
		}
		
		var ju = $('[name="selectChooseJu"] option:selected').text();
		if(ju.substring(0,1) == '1'){
			ju = ju.substring(0,2);
		}else{
			ju = ju.substring(0,1);
		}
		window.localStorage.setItem("selectChooseJu",$('[name="selectChooseJu"] option:selected').val());
		
		var ren = $('[name="selectChooseRen"] option:selected').text().substring(0,1);
		window.localStorage.setItem("selectChooseRen",$('[name="selectChooseRen"] option:selected').val());
		
		htmlName = 'game.html';
		
		if(title.substring(0,4) == '宁波麻将'){
			var baida = $('[name="selectChooseBaida"] option:selected').text().substring(0,1);
			var tai = $('[name="selectChooseTai"] option:selected').text().substring(0,1);
			var hua = $('[name="selectChooseHua"] option:selected').text();
			var jinhua = hua.substring(2,3);
			var yehua = hua.substring(5,6);
			postData.mjType = 'ningboMajiang';
			postData.baida = baida;
			postData.ju = ju;
			postData.ren = ren;
			postData.tai = tai;
			postData.jinhua=jinhua;
			postData.yehua = yehua;
			
			window.localStorage.setItem("selectChooseBaida",$('[name="selectChooseBaida"] option:selected').val());
			window.localStorage.setItem("selectChooseTai",$('[name="selectChooseTai"] option:selected').val());
			window.localStorage.setItem("selectChooseHua",$('[name="selectChooseHua"] option:selected').val());
			
		}else if(title.substring(0,3) == '闯三关' ){
			postData.mjType = 'threeWater';
			postData.ju = ju;
			postData.ren = ren;
			htmlName = 'pokergame.html';
		}else if(title.substring(0,4) == '奉化麻将'){
			postData.mjType = 'fenhuaMajiang';
			var type = $('[name="selectChooseFhType"] option:selected').text();
			postData.type = type;
			postData.ren = ren;
			postData.ju = ju;
			console.log(type+' '+ren+" "+ju);
			if(ren == 2){
				$.alert('人数不能为2人');
				return ;
			}
		}else if(title.substring(0,4) == '天台麻将'){
			postData.mjType = 'tiantaiMajiang';
			postData.ju = ju;
			var hushu = $('[name="selectChooseHushu"] option:selected').text().substring(0,3);
			postData.hushu = hushu;
			var hasBaida = $('#selectChooseHasBaida').prop('checked');
			postData.hasBaida = hasBaida;
		}else if(title.substring(0,4) == '杭州麻将'){
			postData.mjType = 'hangzhouMajiang';
			
			var caishen = $('[name="selectChooseCaishen"] option:selected').val();
			var laoshu = $('[name="selectChooseLaoshu"] option:selected').val();
			var santan = $('[name="selectChooseSantan"] option:selected').val();
			var hufa=$('[name="selectChooseHufa"] option:selected').val();
			var haoqi = $('#selectChooseHaoqi').prop('checked');
			var kaoxiang = $('#selectChooseKaoxiang').prop('checked');
			var pengtan = $('#selectChoosePengTan').prop('checked');
			
			
			window.localStorage.setItem("selectChooseCaishen",$('[name="selectChooseCaishen"] option:selected').val());
			window.localStorage.setItem("selectChooseLaoshu",$('[name="selectChooseLaoshu"] option:selected').val());
			window.localStorage.setItem("selectChooseSantan",$('[name="selectChooseSantan"] option:selected').val());
			window.localStorage.setItem("selectChooseHufa",$('[name="selectChooseHufa"] option:selected').val());
			window.localStorage.setItem("selectChooseHaoqi",haoqi);
			window.localStorage.setItem("selectChooseKaoxiang",kaoxiang);
			window.localStorage.setItem("selectChoosePengTan",pengtan);
			
			postData.ju = ju;
			postData.ren = ren;
			postData.caishen = caishen;
			postData.laoshu =laoshu;
			postData.santan = santan;
			postData.hufa = hufa;
			postData.haoqi = haoqi;
			postData.kaoxiang = kaoxiang;
			postData.pengtan = pengtan;
//			util.printObject(postData);
//			return ;
		}else if(title.substring(0,4) == '红中麻将'){
			postData.mjType = 'hongzhongMajiang';
			postData.ju = ju;
			postData.diScore = $('[name="selectChooseDifen"] option:selected').text().substring(0,1);
			
			window.localStorage.setItem("selectChooseDifen",$('[name="selectChooseDifen"] option:selected').val());
			
		}else if(title.substring(0,4) == '广东麻将'){
			postData.mjType = 'guangdongMajiang';
			postData.ju = ju;
			postData.diScore = $('[name="selectChooseDifen"] option:selected').text().substring(0,1);
			window.localStorage.setItem("selectChooseDifen",$('[name="selectChooseDifen"] option:selected').val());
		}else if(title.substring(0,4) == '象山麻将' ){
			postData.mjType = 'xiangshanMajiang';
			postData.ju = ju;
			postData.ren = ren;
		}else{
			return ;
		}
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/createRoom.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	var reUrl;
		            	if(localStorage.getItem('specialid')){
							reUrl = htmlName + '?recommend='+localStorage.getItem('specialid');
							if(util.env == 'dev'){
								reUrl += '&code='+util.getQueryString('code');
							}
						}else{
							reUrl = getRedirectUrl(htmlName);
						}
                		reUrl +='&roomId='+data.body;
                		window.location.href = reUrl;
		            }else if(data.errCode == '000009'){
		            	$.alert(data.errMsg);
		            	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
						$('#tab4').addClass('weui-tab__bd-item--active');
						$('.weui-bar__item--on').removeClass('weui-bar__item--on');
						faxianView();
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	var msg = util.getQueryString("msg");
	if(msg){
		$.alert(msg);
	}
	homeView();
	
	recordData = {};
	recordData.nowDate = new Date().Format('yyyy-MM-dd hh:mm:ss');
	recordData.currentPage = 1;
	recordData.rowsPerPage = 5;
	$('#recordMore').on('click',function(){
		var postData = getPostData();
		postData.date =  recordData.nowDate;
	    postData.currentPage = recordData.currentPage;
	    postData.rowsPerPage = recordData.rowsPerPage;
	    $.showLoading("正在加载...");
	    $.ajax({
	        url: "/web/checkRoomResultsByAccountAndPage.do",
	        type:"POST",
	        data:postData,
	        success: function (data) {
	        	$.hideLoading();
	            if(data.errCode == "000000"){
	            	
	            	if(!data.body || data.body.length == 0){
	            		$.alert('亲，没有更多的房间记录了');
	            		return ;
	            	}
	            	
	            	var body = $('#recordBody').html();
	            	for(var i = 0;i<data.body.length;i++){
	            		var url = '?recommend='+util.getQueryString('recommend')+'&roomId='+data.body[i].roomId;
	            		if(data.body[i].title.substring(0,3) == '闯三关'){
	            			url = 'pokerrecordgame.html' + url;
	            		}else{
	            			url = 'recordGame.html'+url;
	            		}
	            		if(util.env == 'dev'){
	            			url += '&code='+util.getQueryString('code');
	            		}
	            		body += util.getWeUIItem(url,data.body[i].image,data.body[i].title,data.body[i].desc+'('+data.body[i].createTime+') 点击看录像','');
	            	}
	            	$('#recordBody').html(body);    
	            	recordData.currentPage += 1;    	
	            }else{
	            	 $.alert(data.errMsg);
	            }
	        }
	    });
	});
	
	createRoomData = {};
	createRoomData.nowDate = new Date().Format('yyyy-MM-dd hh:mm:ss');
	createRoomData.currentPage = 1;
	createRoomData.rowsPerPage = 5;
	$('#createRoomMore').on('click',function(){
		var postData = getPostData();
		postData.date =  createRoomData.nowDate;
	    postData.currentPage = createRoomData.currentPage;
	    postData.rowsPerPage = createRoomData.rowsPerPage;
	    $.showLoading("正在加载...");
	    $.ajax({
	        url: "/web/getListRoomsByCreateAccount.do",
	        type:"POST",
	        data:postData,
	        success: function (data) {
	        	$.hideLoading();
	            if(data.errCode == "000000"){
	            	if(!data.body || data.body.length == 0){
	            		$.alert('亲，没有更多的开房记录了');
	            		return ;
	            	}
	            	var body = $('#createRoomBody').html();
	            	for(var i = 0;i<data.body.length;i++){
	            		var url = 'game.html?recommend='+util.getQueryString('recommend')+'&roomId='+data.body[i].roomShowId;
		        		if(util.env == 'dev'){
		        			url += '&code='+util.getQueryString('code');
		        		}
	            		body += util.getWeUIItem('',data.body[i].image,data.body[i].title,data.body[i].desc+'('+data.body[i].createTime+')','');
	            	}
	            	$('#createRoomBody').html(body);    
	            	createRoomData.currentPage += 1;    	
	            }else{
	            	 $.alert(data.errMsg);
	            }
	        }
	    });
	});
	
	
	
	var view = util.getQueryString("view");
	var roomId = util.getQueryString("roomId");
	if(roomId){
		roomView();
		return ;
	}
	if(view == 'ningboMajiang'){
		ningboMajiangView();
	}else if(view == 'hongzhongMajiangView'){
		hongzhongMajiangView();
	}else if(view == 'fenhuaMajiang'){
		fenhuaMajiangView();
	}else if(view == 'tiantaiMajiang'){
		tiantaiMajiangView();
	}else if(view =='hangzhouMajiang'){
		hangzhouMajiangView();
	}else if(view == 'faxian'){
		$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
		$('#tab4').addClass('weui-tab__bd-item--active');
		$('.weui-bar__item--on').removeClass('weui-bar__item--on');
		faxianView();
	}else if(view == 'chongzhi'){
		$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
		$('#tab1').addClass('weui-tab__bd-item--active');
		$('.weui-bar__item--on').removeClass('weui-bar__item--on');
	}
});


function initChoose(){
	
	
	$('#divChooseJu').hide();
	$('#divChooseRen').hide();
	$('#divChooseFhType').hide();
	$('#divChooseBaida').hide();
	$('#divChooseTai').hide();
	$('#divChooseHua').hide();
	$('#divChooseHasBaida').hide();
	$('#divChooseHushu').hide();
	$('#divChooseCaishen').hide();
	$('#divChooseLaoshu').hide();
	$('#divChooseSantan').hide();
	$('#divChooseHufa').hide();
	$('#divChooseHaoqi').hide();
	$('#divChooseKaoxiang').hide();
	$('#divChoosePengTan').hide();
	$("#divChooseDifen").hide();
	
	
	var selectChooseJu = window.localStorage.getItem("selectChooseJu");
	if(selectChooseJu){
		$('[name="selectChooseJu"]').val(selectChooseJu);
	}
	
	var selectChooseRen = window.localStorage.getItem("selectChooseRen");
	if(selectChooseRen){
		$('[name="selectChooseRen"]').val(selectChooseRen);
	}
	
	var selectChooseCaishen = window.localStorage.getItem("selectChooseCaishen");
	if(selectChooseCaishen){
		$('[name="selectChooseCaishen"]').val(selectChooseCaishen);
	}
	
	var selectChooseLaoshu = window.localStorage.getItem("selectChooseLaoshu");
	if(selectChooseLaoshu){
		$('[name="selectChooseLaoshu"]').val(selectChooseLaoshu);
	}
	
	var selectChooseSantan = window.localStorage.getItem("selectChooseSantan");
	if(selectChooseSantan){
		$('[name="selectChooseSantan"]').val(selectChooseSantan);
	}
	
	var selectChooseHufa = window.localStorage.getItem("selectChooseHufa");
	if(selectChooseHufa){
		$('[name="selectChooseHufa"]').val(selectChooseHufa);
	}
	
	var selectChooseHaoqi = window.localStorage.getItem("selectChooseHaoqi");
	if(selectChooseHaoqi){
		if(selectChooseHaoqi == 'false'){
			$('#selectChooseHaoqi').removeAttr('checked');
		}
	}
	var selectChooseKaoxiang = window.localStorage.getItem("selectChooseKaoxiang");
	if(selectChooseKaoxiang){
		if(selectChooseKaoxiang == 'false'){
			$('#selectChooseKaoxiang').removeAttr('checked');
		}
	}
	
	var selectChoosePengTan = window.localStorage.getItem("selectChoosePengTan");
	if(selectChoosePengTan){
		if(selectChoosePengTan == 'false'){
			$('#selectChoosePengTan').removeAttr('checked');
		}
	}
	
	
	var selectChooseDifen = window.localStorage.getItem("selectChooseDifen");
	console.log(selectChooseDifen);
	if(selectChooseDifen){
		$('[name="selectChooseDifen"]').val(selectChooseDifen);
	}

}

function xiangshanMajiangView(){
	initChoose();
	
	
	$('#chooseTitle').html('象山麻将');
	$('#divChooseJu').show();
	$('#divChooseRen').show();
//	$('#divChooseBaida').show();
//	$('#divChooseTai').show();
//	$('#divChooseHua').show();
	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
	
}



function ningboMajiangView(){
	initChoose();
	
	$('#chooseTitle').html('宁波麻将');
	$('#divChooseJu').show();
	$('#divChooseRen').show();
	$('#divChooseBaida').show();
	$('#divChooseTai').show();
	$('#divChooseHua').show();
	
	
//	var ju = $('[name="selectChooseJu"] option:selected').text();
//	if(ju.substring(0,1) == '1'){
//		ju = ju.substring(0,2);
//	}else{
//		ju = ju.substring(0,1);
//	}
//	$('#chooseTitle').html('宁波麻将(需要'+(ju/4 + 1)+'个钻石) 房主扣钻');
	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
}


function threeWaterView(){
	initChoose();
	
	$('#chooseTitle').html('闯三关');
	$('#divChooseJu').show();
	$('#divChooseRen').show();
	
	
	
//	var ju = $('[name="selectChooseJu"] option:selected').text();
//	if(ju.substring(0,1) == '1'){
//		ju = ju.substring(0,2);
//	}else{
//		ju = ju.substring(0,1);
//	}
//	$('#chooseTitle').html('闯三关(需要'+(ju/4 + 1)+'个钻石) 房主扣钻');
	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
}


function guangdongMajiangView(){
	initChoose();
	
	$('#chooseTitle').html('广东麻将');
	$('#divChooseJu').show();
	$("#divChooseDifen").show();

	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
}

function hongzhongMajiangView(){
	initChoose();
	
	$('#chooseTitle').html('红中麻将');
	$('#divChooseJu').show();
	$("#divChooseDifen").show();
		
//	$('[name="selectChooseJu"]').empty();
//	$('[name="selectChooseJu"]').append('<option value="1">4局</option>');
//	$('[name="selectChooseJu"]').append('<option value="1">8局</option>');
	
//	var ju = $('[name="selectChooseJu"] option:selected').text();
//	if(ju.substring(0,1) == '1'){
//		ju = ju.substring(0,2);
//	}else{
//		ju = ju.substring(0,1);
//	}
//	$('#chooseTitle').html('红中麻将(需要'+(ju/4 + 1)+'个钻石) 房主扣钻');
	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
}

function fenhuaMajiangView(){
	initChoose();
	
	$('#chooseTitle').html('奉化麻将');
	$('#divChooseFhType').show();
	$('#divChooseJu').show();
	$('#divChooseRen').show();
	
	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
}
function tiantaiMajiangView(){
	initChoose();
	
	$('#chooseTitle').html('天台麻将');
	
	$('#divChooseJu').show();
	$('#divChooseHasBaida').show();
	$('#divChooseHushu').show();

	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
}
function hangzhouMajiangView(){
	
	initChoose();
	
	$('#chooseTitle').html('杭州麻将');
	$('#divChooseJu').show();
//	$('#divChooseRen').show();
	$('#divChooseCaishen').show();
	$('#divChooseLaoshu').show();
	$('#divChooseSantan').show();
	$('#divChooseHufa').show();
	$('#divChooseHaoqi').show();
	$('#divChooseKaoxiang').show();
	$('#divChoosePengTan').show();
	
	
	$('.weui-tab__bd-item--active').removeClass('weui-tab__bd-item--active');
	$('#tab_choose').addClass('weui-tab__bd-item--active');
	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
	
}


function roomView(){
	var roomId = util.getQueryString("roomId");
//	$('#roomLink').hide();
    if(roomId){
        $.showLoading("正在加载...");
		$.ajax({
		    url: "/web/checkRoomDetail.do",
		    type:"POST",
		    data: {
		        roomId : roomId
		    },
		    success: function (data) {
		    	$.hideLoading();
		    	$('#tab3').removeClass('weui-tab__bd-item--active');
		    	$('#tab_room').addClass('weui-tab__bd-item--active');
		    	$('.weui-bar__item--on').removeClass('weui-bar__item--on');
		        if(data.errCode == '000000'){
		        	var roomInfo = '房号：'+data.body.roomId;
		        	var name = data.body.roomInfo.name;
		        	if(name == 'ningboMajiang'){
		        		roomInfo += ('-宁波麻将-' + data.body.roomInfo.baida+'百搭 ');
		        	}else if(name == 'fenhuaMajiang'){
		        		roomInfo += ('-奉化麻将-'+(data.body.roomInfo.pinghu?"平搓":"冲刺"));
		        	}else if(name == 'tiantaiMajiang'){
		        		roomInfo += '-天台麻将';
		        	}else if(name == 'hangzhouMajiang'){
		        		roomInfo += '-杭州麻将';
		        	}else if(name == 'hongzhongMajiang'){
		        		roomInfo += '-红中麻将';
		        	}else if(name == 'guangdongMajiang'){
		        		roomInfo += '-广东麻将';
		        	}else if(name == 'threeWater'){
		        		roomInfo += '-闯三关';
		        	}else if(name == 'xiangshanMajiang'){
		        		roomInfo += '-象山麻将';
		        	}
		        	
		            $('#roomInfo').html(roomInfo);
		           	if(data.body.status == '2'){
		           		$('#roomOver').html('进行中');
		           		$('#roomJu').html(data.body.order + '/'+data.body.totalJu);
		           		$('#roomJuKey').html('当前局数');
		           	}else if(data.body.status == '0'){
		           		$('#roomOver').html('已解散');
		           		$('#roomJu').html(data.body.endJu);
		           		$('#roomJuKey').html('牌局局数');
		           	}else if(data.body.status == '1'){
		           		$('#roomOver').html('已结束');
		           		$('#roomJu').html(data.body.endJu);
		           		$('#roomJuKey').html('牌局局数');
		           	}
		           	
		           	$('#roomCreateUrl').attr("src",data.body.createHeadImg);
		           	$('#roomCreatePlayer').html('房主:'+data.body.createNickName);
		           	$('#roomCreateTime').html(data.body.createTime);
		           	var users = data.body.users;
		           	var i = 0;
		           	if(users && users.length >0){
			           	for(i = 0;i<users.length;i++){
			           		$('#roomP'+(i+1)+'Name').html(users[i].name);
			           		$('#roomP'+(i+1)+'Score').html(users[i].score>0?'+'+users[i].score:users[i].score);
			           		$('#roomP'+(i+1)+'Url').attr("src",users[i].headimg);
			           	}
		           	}
		           	for(;i < 4;i++){
		           		$('#roomP'+(i+1)+'A').hide();
		           	}
		        }else{
		        	$('#roomInfo').html('房间不存在');
		        	for(var i = 0;i < 4;i++){
		           		$('#roomP'+(i+1)+'A').hide();
		           	}
		        }
		    }
		});
    }
    
    
}

function faxianView(){

    if(createRoomData.currentPage != 1){  //说明之前已经加载过了
    	return ;
    }
    
    var postData = getPostData();
    postData.date =  createRoomData.nowDate;
    postData.currentPage = createRoomData.currentPage;
    postData.rowsPerPage = createRoomData.rowsPerPage;
    $('#createRoomBody').html('');
    $.showLoading("正在加载...");
    $.ajax({
        url: "/web/getListRoomsByCreateAccount.do",
        type:"POST",
        data:postData,
        success: function (data) {
        	$.hideLoading();
            if(data.errCode == "000000"){
            	if(!data.body || data.body.length == 0){
            		$('#createRoomBody').html('');
            		return ;
            	}
            	
            	var body = '';
            	for(var i = 0;i<data.body.length;i++){
            		var url = 'game.html?recommend='+util.getQueryString('recommend')+'&roomId='+data.body[i].roomShowId;
            		if(util.env == 'dev'){
            			url += '&code='+util.getQueryString('code');
            		}
            		body += util.getWeUIItem(url,data.body[i].image,data.body[i].title,data.body[i].desc+'('+data.body[i].createTime+')','');
            	}
            	$('#createRoomBody').html(body);    
            	createRoomData.currentPage += 1;    	
            }else{
            	 $.alert(data.errMsg);
            }
        }
    });
}

function zhanjiView(){
	var postData = getPostData();
	$.showLoading("正在加载...");
    $.ajax({
        url: "/web/checkBattle.do",
        type:"POST",
        data:postData,
        success: function (data) {
        	$.hideLoading();
            if(data.errCode == "000000"){
            	var times =  data.body.win+data.body.lose+data.body.ping;
            	$("#zj_times").html(times);
//            	$("#zj_score").html(data.body.score);
            	$("#zj_win").html(data.body.win);
            	$("#zj_lose").html(data.body.lose);
            	$("#zj_ping").html(data.body.ping);
            }else{
            	$.alert(data.errMsg);
            }
        }
    });
    
    if(recordData.currentPage != 1){  //说明之前已经加载过了
    	return ;
    }
    
   
    postData.date =  recordData.nowDate;
    postData.currentPage = recordData.currentPage;
    postData.rowsPerPage = recordData.rowsPerPage;
    $('#recordBody').html('');
    $.showLoading("正在加载...");
    $.ajax({
        url: "/web/checkRoomResultsByAccountAndPage.do",
        type:"POST",
        data:postData,
        success: function (data) {
        	$.hideLoading();
            if(data.errCode == "000000"){
            	
            	if(!data.body || data.body.length == 0){
            		$('#recordBody').html('');
            		return ;
            	}
            	
            	var body = '';
            	for(var i = 0;i<data.body.length;i++){
            		var url = '?recommend='+util.getQueryString('recommend')+'&roomId='+data.body[i].roomId;
            		if(data.body[i].title.substring(0,3) == '闯三关'){
            			url = 'pokerrecordgame.html' + url;
            		}else{
            			url = 'recordGame.html'+url;
            		}
            		if(util.env == 'dev'){
            			url += '&code='+util.getQueryString('code');
            		}
            		body += util.getWeUIItem(url,data.body[i].image,data.body[i].title,data.body[i].desc+'('+data.body[i].createTime+') 点击看录像','');
            	}
            	$('#recordBody').html(body);    
            	recordData.currentPage += 1;    	
            }else{
//            	var body = util.getWeUIItem('',window.localStorage.getItem("headimg"),'您还未玩过游戏','赶紧和小伙伴一起游戏吧','');
//            	$('#recordBody').html(body);
            	
            	 $.alert(data.errMsg);
            }
        }
    });
}

function homeView(){
	

	
	
	var postData = getPostData();
	$.showLoading("正在加载...");
    $.ajax({
        url: "/web/checkUser.do",
        type:"POST",
        data: postData,
        success: function (data) {
        	$.hideLoading();
            if(data.errCode == "000000"){
            	data.userinfo = data.body;
	            window.localStorage.setItem("nickname",data.userinfo.nickname);
	            window.localStorage.setItem("headimg",data.userinfo.headimg);
	            window.localStorage.setItem("money",data.userinfo.money);
         		window.localStorage.setItem("gameUserId",data.userinfo.gameUserId);
				window.localStorage.setItem("specialid",data.userinfo.specialid);
           
              $('.money').html('<img src="image/charge_diamond.png">'+data.userinfo.money);
              $('.nick_name').html(data.userinfo.nickname+'(ID:'+data.userinfo.gameUserId+')');
              $('.head_image').attr("src",data.userinfo.headimg);
              $('#notification').html(data.body.message);
              $('#notification1').html(data.body.message);
              
              if(data.userinfo.specialid){
              	   $('#adviceAgentPay').show();
//              	   $('#currentSpecialIdItem').show();
//              	   var recommend = util.getQueryString('recommend');
//              	   if(recommend){
//              	   	 	$('#currentSpecialId').html(recommend);
//              	   }else{
//              	   		$('#currentSpecialId').html('无');
//              	   }
              }else{
              	   $('#adviceAgentPay').hide();
//              	   $('#currentSpecialIdItem').hide();
              }
              
//              if(data.userinfo.isAdmin){
//              	   $('#currentSpecialIdItem').show();
//              	   var recommend = util.getQueryString('recommend');
//              	   if(recommend){
//              	   	 	$('#currentSpecialId').html(recommend);
//              	   }else{
//              	   		$('#currentSpecialId').html('无');
//              	   }
//              }
            }else  if(data.errCode == "000001"){
                window.localStorage.clear();
                window.location.href = getRedirectUrl('/web/home.do');
            }
        }
    });
}


function showTitle(){
	var ju = $('[name="selectChooseJu"] option:selected').text();
	var ju = $('[name="selectChooseJu"] option:selected').text();
	if(ju.substring(0,1) == '1'){
		ju = ju.substring(0,2);
	}else{
		ju = ju.substring(0,1);
	}
	var title =  $('#chooseTitle').html();
	console.log(ju+' '+title);
	title =  title.split(' ');
	title = title[0]+ ' '+'(需要'+(1 + ju/4)+'个钻石) 房主扣钻';
	$('#chooseTitle').html(title);
}

function getPostData(){
	var postData;
    if(util.env == 'dev'){
    	postData ={code:util.getQueryString('code')};
    }else{
    	postData = {};
    }
    return postData;
}

function getRedirectUrl(path){
	path += ('?recommend='+util.getQueryString('recommend'));
	if(util.env == 'dev'){
		path+=('&code='+util.getQueryString('code'));
	}
	return path;
}

function getShareUrl(){
	var host ='';
	var ishttps = 'https:' == document.location.protocol ? true : false;
	if(ishttps){
		host = 'https://'+ window.location.host;
	}else{
		host ='http://'+ window.location.host;
	}
	host += ('/web/home.do?recommend='+util.getQueryString('recommend'));
	if(util.env == 'dev'){
		host+= ('&code='+util.getQueryString('code'));
	}
	return host;
}

function weixinShare(){
			 //分享link
    var link = getShareUrl();
    console.log(link);
    //分享到朋友数据
    var sharedata = {
        title: '麻将馆',
        desc: '发现一个麻将打牌神器,点击即可进入',
        link: link,
        imgUrl: ''
    };
    //分享到朋友圈数据 默认和分享朋友一样
    var timelinedata = {
        title: sharedata.title,
        link: sharedata.link,
        imgUrl: sharedata.imgUrl
    };
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
    if(typeof wx != 'undefined'){
		wx.ready(function(){
			wx.hideAllNonBaseMenuItem();
			wx.showMenuItems({
   				menuList: menuList
			});
		 	wx.onMenuShareAppMessage(sharedata);
    		wx.onMenuShareTimeline(timelinedata);
		});
	}
}

function onBridgeReady(result) {
	WeixinJSBridge.invoke('getBrandWCPayRequest', result,
		function(res) {
			if (res.err_msg == "get_brand_wcpay_request：ok") {
				alert(res.err_msg);
			} // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 // ok，但并不保证它绝对可靠。
			homeView();
		});
}

Date.prototype.Format = function (fmt) { 
	  var o = {
	    "M+": this.getMonth() + 1, //月份
	    "d+": this.getDate(), //日
	    "h+": this.getHours(), //小时
	    "m+": this.getMinutes(), //分
	    "s+": this.getSeconds(), //秒
	    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
	    "S": this.getMilliseconds() //毫秒
	  };
	  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	  for (var k in o)
	  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	  return fmt;
}

