util = {};
util.isWeixin = function() {
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger') {
		return true;
	} else {
		return false;
	}
}
util.getQueryString = function(key) {
    var url = window.location.search;
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = url.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
};
util.inherit = function(p) {
	if (p == null)
		throw TypeError();
	if (Object.create) {
		return Object.create(p);
	}
	var t = typeof p;
	if (t !== "object" && t !== "function")
		throw TypeError();
	function f() {
	}
	;
	f.prototype = p;
	return new f();
}
util.getRemoveItemArr = function(arr, value) {
	var arr1 = [];
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] != value) {
			arr1.push(arr[i]);
		}
	}
	return arr1;
}
util.getRightMoveArr = function(arr, index) {
	var arr1 = [], i, len = arr.length;
	for (i = 0; i < len; i++) {
		arr1[(i + index) % len] = arr[i]
	}
	return arr1;
}
util.log = function(str){
	if(util.env != 'dev'){
		return ;
	}
	console.log(str);
};
util.ltrim = function (s){
    return s.replace(/(^\s*)/g, "");
};
util.rtrim = function (s){
    return s.replace(/(\s*$)/g, "");
};
util.trim = function (s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
};
util.ratio4to3 = function(){
	return screen.width /screen.height > 0.7;
}
util.hasNumber = function(arr,number){
	for(var i = 0;i<arr.length;i++){
		if(number ==  arr[i]){
			return true;
		}
	}
	return false;
}
util.printObject = function(obj){
	var prop;
	var value = '';
	for (prop in obj) { 
		value +=( 'key['+prop+']');
		value +=('value['+obj[prop]+']');
	}
	console.log(value);
};

util.getWeUIItem = function(link,image,title,desc,foot){
	return '<a href="'+(link == null?'javascript:void(0);':link)+'" class="weui-media-box weui-media-box_appmsg"><div class="weui-media-box__hd"><img  class="weui-media-box__thumb" src="'+image+'"></div><div class="weui-media-box__bd"><h4  class="weui-media-box__title">'+title+'</h4><p  class="weui-media-box__desc">'+desc+'</p></div><div class="weui-media-box__ft">'+foot+'</div></a>';
}
util.env ='dev';
//util.env ='test';
//util.env = 'prod';
