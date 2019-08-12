$(document).ready(function() {
	
	weixinShare();
	
	var height = $('.weui-tab__bd').height() - $('.weui-tabbar').height();
	$('.weui-tab__bd').height(height);
	
	$('#queryDate1').calendar();
	$('#queryDate2').calendar();
	
	$('#managerQuery').on('click',function(){
	
		var date = $('#queryDate2').val();
		if(!date){
			$.toptip('请输入查询时间', 'warning');
			return ;
		}
		date = date.replace('/','-');
		date = date.replace('/','-');
		var postData = getPostData();
		postData.date = date;
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/getTotalRoomsAndMoneys.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	$('#totalRoom2').html(data.body.totalRoom);
		            	$('#totalMoney2').html(data.body.totalMoney);
		            	$('#totalPay2').html(data.body.totalPay);
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	
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
		postData.userId = userId;
		postData.money = money;
		console.log(userId +" "+money);
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/sendMoney.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	$.alert('操作成功');
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
		        url: "/web/getBill.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	$('#totalMoney').html(data.body.money);
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	
	homeView();
});




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
           
              	$('.money').html('<img src="image/charge_diamond.png">'+data.userinfo.money);
              	$('.nick_name').html(data.userinfo.nickname+'(ID:'+data.userinfo.gameUserId+')');
              	$('.head_image').attr("src",data.userinfo.headimg);
              
              if(!data.userinfo.isAdmin){
              	 wx.closeWindow();
              	 return ;
              }
              
              $('[name="queryDate1"]').empty();
              $('[name="queryDate2"]').empty();
	
		
            }else  if(data.errCode == "000001"){
                wx.closeWindow();
                return ;
            }
        }
    });
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





function weixinShare(){
    if(typeof wx != 'undefined'){
		wx.ready(function(){
			wx.hideAllNonBaseMenuItem();
		});
	}
}


