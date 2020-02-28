/**
 * 展示loading
 * @returns {*}
 */
function showLoading(){
	var idx = layer.msg('处理中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;  
	return idx;
}
//salt
var passsword_salt="1a2b3c4d"
// 获取url参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
};
//设定时间格式化函数，使用new Date().format("yyyyMMddhhmmss");  
Date.prototype.format = function (format) {  
    var args = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
    };  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var i in args) {  
        var n = args[i];  
        if (new RegExp("(" + i + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));  
    }  
    return format;  
};

/**
 * 将时间戳转化为时间戳格式
 * @param timestamp
 * @returns {string}
 */
function timestampToTime(timestamp) {
    var date = new Date(timestamp * 1000); // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = date.getDate() + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes() + ':';
    var s = date.getSeconds();
    return Y+M+D+h+m+s;
}

/**
 * 拦截所有ajax请求的返回值
 */
$.ajaxSetup({
    complete: function(XMLHttpRequest, textStatus) {

    },
    statusCode: {
        401: function() {
            alert('登录失效，请重新登录');
            window.location.href = "/api/login/page";
        },
        504: function() {
            alert('数据获取/输入失败，服务器没有响应。504');
        },
        500: function() {
            alert('服务器有误。500');
        }
    }
});