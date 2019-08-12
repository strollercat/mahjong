$(document).ready(function() {

	weixinShare();

	var height = $('.weui-tab__bd').height() - $('.weui-tabbar').height();
	$('.weui-tab__bd').height(height);
	
	currentMoney = 0 ;
	
	$('#agentId3').blur(function(){		
		var postData = getPostData();
		postData.id = $('#agentId3').val();
		if(postData.id==''){
			return ;
		}
		$.ajax({
		        url: "/web/getGameUserNameById.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		            if(data.errCode == "000000"){
		            	$('#agentName').val(data.body.name);
		            }else{
		                $('#agentName').val('不存在id为'+postData.id+'的用户');
		            }
		        }
		});
	});

	homeView();
	
	$('#sendMoneySubmit').on('click',function(){
		
		var userId = $('#agentId3').val();
		var money = $('#agentMoney3').val();	
		
		if(!userId){
			$.toptip('请输入用户ID', 'warning');
			return ;
		}
		if(!money){
			$.toptip('请输入钻石数', 'warning');
			return ;
		}
		
		var postData = getPostData();
		postData.payAccount = userId;
		postData.money = money;
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/agentPay.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	$.alert('操作成功');
		            	currentMoney -= money;
		            	$('.money').html(
								'<img src="image/charge_diamond.png">'
										+ currentMoney);
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	$('#agentBuySubmit').on('click',function(){
		
		var money = $('#agentBuyMoney').val();	
		
		if(!money){
			$.toptip('请输入钻石数', 'warning');
			return ;
		}
		
		var postData = getPostData();
		postData.diamond = money;
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/agentPrePay.do",
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
	
	$('#startTime').datetimePicker();
	$('#endTime').datetimePicker();
	$('#moneyQuery').on('click',function(){
	
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		if(!startTime){
			$.toptip('请输入开始时间', 'warning');
			return ;
		}
		if(!endTime){
			$.toptip('请输入结束时间', 'warning');
			return ;
		}
		startTime += ':00';
		endTime += ':00';
		
		var postData = getPostData();
		postData.startTime = startTime;
		postData.endTime = endTime;
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/getTotalSellMoneyByAccountTimeRange.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	$('#totalMoney').html(-data.body);
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	
	
	moneySellData = {};
	moneySellData.nowDate = new Date().Format('yyyy-MM-dd hh:mm:ss');
	moneySellData.currentPage = 1;
	moneySellData.rowsPerPage = 5;
	$('#moneySellMore').on('click',function(){
		var postData = getPostData();
		postData.date =  moneySellData.nowDate;
	    postData.currentPage = moneySellData.currentPage;
	    postData.rowsPerPage = moneySellData.rowsPerPage;
	    $.showLoading("正在加载...");
	    $.ajax({
	        url: "/web/getSellMoneyDetailsByAccountPage.do",
	        type:"POST",
	        data:postData,
	        success: function (data) {
	        	$.hideLoading();
	            if(data.errCode == "000000"){
	            	
	            	if(!data.body || data.body.length == 0){
	            		$.alert('亲，没有更多的钻石销售记录了');
	            		return ;
	            	}
	            	
	            	var body = $('#moneySellBody').html();
	            	for(var i = 0;i<data.body.length;i++){
	            		var item = data.body[i];
	            		body += util.getWeUIItem('',item.headimg,'销售给 '+item.nickname+' '+(-item.money)+'钻','时间：'+item.time,'');
	            	}
	            	$('#moneySellBody').html(body);    
	            	moneySellData.currentPage += 1;    	
	            }else{
	            	 $.alert(data.errMsg);
	            }
	        }
	    });
	});

});

function homeView() {
	var postData = getPostData();
	$.showLoading("正在加载...");
	$.ajax({
		url : "/web/checkUser.do",
		type : "POST",
		data : postData,
		success : function(data) {
			$.hideLoading();
			if (data.errCode == "000000") {
				data.userinfo = data.body;
				currentMoney = data.userinfo.money;
				$('.money').html(
						'<img src="image/charge_diamond.png">'
								+ currentMoney);
				$('.nick_name').html(
						data.userinfo.nickname + '(ID:'
								+ data.userinfo.gameUserId + ')');
				$('.head_image').attr("src", data.userinfo.headimg);
				window.localStorage.setItem("headimg", data.userinfo.headimg);
				if (!data.userinfo.specialid) {
					wx.closeWindow();
					return;
				}
			} else if (data.errCode == "000001") {
				wx.closeWindow();
				return;
			}
		}
	});
}

function getPostData() {
	var postData;
	if (util.env == 'dev') {
		postData = {
			code : util.getQueryString('code')
		};
	} else {
		postData = {};
	}
	return postData;
}

function weixinShare() {
	if (typeof wx != 'undefined') {
		wx.ready(function() {
			wx.hideAllNonBaseMenuItem();
		});
	}
}

Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
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
