$(function () {

    //只要执行代码,都要执行这段代码
    $.messager.model={
        ok:{text:'确定'},
        cancel:{text:'取消'}
    }
    //禁用提交数组时自动添加 [] 的功能
    jQuery.ajaxSettings.traditional = true;
})
//每个页面的ajax的回调函数
var handlerMessage=function (data) {
    //这里使用 alert或者popup都可以
    if (data.success){
        $.messager.popup('操作成功,2秒后制定刷新')
        setTimeout(function () {
            window.location.reload();//重新加载当前页面(携带原本的参数)
        },2000)
    } else{
        $.messager.popup(data.msg)
    }
}