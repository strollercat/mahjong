$(document).ready(function() {
	
	weixinShare();
	
	var height = $('.weui-tab__bd').height() - $('.weui-tabbar').height();
	$('.weui-tab__bd').height(height);
	
	
	$('#agentSet').on('click',function(){
		
	
		var agentId = $('#agentId').val()
		if(!agentId){
			$.toptip('请输入用户ID', 'warning');
			return ;
		}
		var isAgent = $('#isAgent').val()
		if(!isAgent){
			$.toptip('请输入代理商标志', 'warning');
			return ;
		}
		
		var postData = getPostData();
		postData.agentId = agentId;
		postData.isAgent = isAgent;
		console.log(postData.agentId +' '+postData.isAgent);
	
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/agentSet.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	$.alert('操作成功！');
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
		});
	});
	
	$('#winloseSubmit').on('click',function(){

		var postData = getPostData();
		
		postData.gameUserId = $('#winLoseUserId').val();
		postData.ratio = $('#winLoseRatio').val();
		if(postData.ratio == ''){
			postData.ratio = 0;
		}
		postData.winlose = $('[name="winLose"] option:selected').text();
		if(postData.winlose == '赢'){
			postData.winlose = 1;
		}else if(postData.winlose == '输'){
			postData.winlose = 0;
		}else{
			postData.winlose = 2;
		}
		
		
		$.showLoading("正在加载...");
		$.ajax({
	        url: "/web/operatorWinLose.do",
	        type:"POST",
	        data: postData,
	        success: function (data) {
	        	$.hideLoading();
	            if(data.errCode == "000000"){
	            	$.alert('操作成功！');
	            }else{
	                $.alert(data.errMsg);
	            }
	        }
		});
		
	});
	
	
	
	
	
	
	var nowDate = new Date().Format('yyyy-MM-dd hh:mm:ss');
	var htmlBody ='';
	var page = 1;
	$('#loadMore').on('click',function(){
		var postData = getPostData();
		postData.date = nowDate;
		postData.currentPage = page;
		postData.rowsPerPage = 5;
		console.log(nowDate + " "+page);
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/getListRooms.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	if(!data.body || data.body.length == 0){
		            		 $.alert('无更多开房纪录');
		            		 return ;
		            	}
		            	for(var i = 0;i< data.body.length;i++){
		            		var item = data.body[i];
		            		var url = 'recordGame.html?recommend='+util.getQueryString('recommend')+'&roomId='+item.roomId;
		            		if(util.env == 'dev'){
		            			url += '&code='+util.getQueryString('code');
		            		}
		            		var title = item.title ;
		            		htmlBody += util.getWeUIItem(url,item.image,title,'('+item.recommend+')'+item.desc+'('+item.createTime+')' ,'');
		            	}
		            	$('#listBody').html(htmlBody);
		            	page += 1;
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
			});
	});
	
	
	

	var recommendHtmlBody = '';
	var recommendPage = 1;
	$('#loadMoreRecommend').on('click',function(){
		var postData = getPostData();
		postData.currentPage = recommendPage;
		postData.rowsPerPage = 5;
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/getListRecommendGameUsers.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	if(!data.body || data.body.length == 0){
		            		 $.alert('无更多代理商纪录');
		            		 return ;
		            	}
		            	for(var i = 0;i< data.body.length;i++){
		            		var item = data.body[i];
		            		var title = 'ID:'+item.id;
		            		var desc = '昵称：'+item.name+' 钻石:'+item.money+' 积分:'+item.score;
		            		recommendHtmlBody += util.getWeUIItem('',item.image,title,desc,'');
		            	}
		            	$('#listRecommend').html(recommendHtmlBody);
		            	recommendPage += 1;
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
			});
	});
	
	
	
	var winloseHtmlBody = '';
	var winlosePage = 1;
	$('#loadMoreWinLose').on('click',function(){
		var postData = getPostData();
		postData.currentPage = winlosePage;
		postData.rowsPerPage = 5;
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/getListWinLoses.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	if(!data.body || data.body.length == 0){
		            		 $.alert('无更多输赢明细纪录');
		            		 return ;
		            	}
		            	for(var i = 0;i< data.body.length;i++){
		            		var item = data.body[i];
		            		var title = item.nickname +'(ID:'+item.gameUserId+')';
		            		var desc = '输赢：'+item.winlose+' 概率:'+item.ratio+' 积分:'+item.score;
		            		winloseHtmlBody += util.getWeUIItem('',item.headimgurl,title,desc,'');
		            	}
		            	$('#listBodyWinLose').html(winloseHtmlBody);
		            	winlosePage += 1;
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
			});
	});
	
	
	var userHtmlBody = '';
	var userPage = 1;
	$('#loadMoreUser').on('click',function(){
		var postData = getPostData();
		postData.currentPage = userPage;
		postData.rowsPerPage = 5;
		postData.scoreDir = $('[name="scoreDir"] option:selected').text();
		if(postData.scoreDir == '积分从高到低'){
			postData.scoreDir = 1;
		}else{
			postData.scoreDir = 0;
		}
		console.log(postData.scoreDir);
		$.showLoading("正在加载...");
		$.ajax({
		        url: "/web/getListUsers.do",
		        type:"POST",
		        data: postData,
		        success: function (data) {
		        	$.hideLoading();
		            if(data.errCode == "000000"){
		            	if(!data.body || data.body.length == 0){
		            		 $.alert('无更多输赢明细纪录');
		            		 return ;
		            	}
		            	for(var i = 0;i< data.body.length;i++){
		            		var item = data.body[i];
		            		var title = '(ID:'+item.gameUserId+')'+item.name;
		            		var desc = ' 积分:'+item.score;
		            		userHtmlBody += util.getWeUIItem('',item.image,title,desc,'');
		            	}
		            	$('#listBodyUser').html(userHtmlBody);
		            	userPage += 1;
		            }else{
		                $.alert(data.errMsg);
		            }
		        }
			});
	});
	
	
	$('.weui-tabbar__item').on('click',function(){
		var id = $(this).attr('href');
		if(id == '#tab1'){
			recommendHtmlBody = '';
			recommendPage = 1;
			$('#listRecommend').html(recommendHtmlBody);
		}
		if(id == '#tab5'){
			winloseHtmlBody = '';
			winlosePage = 1;
			$('#listBodyWinLose').html('');
		}
		if(id == '#tab4'){
			userHtmlBody = '';
			userPage = 1;
			$('#listBodyUser').html('');
		}
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
              
              if(!data.userinfo.superAdmin){
              	 wx.closeWindow();
              	 return ;
              }
              
              $('[name="startTime"]').empty();
              $('[name="endTime"]').empty();
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


