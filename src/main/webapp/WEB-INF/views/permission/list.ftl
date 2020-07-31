<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>权限管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            //拿到删除的标签,并添加点击事件
            $(".btn_delete").click(function () {
                //点击事件要做什么
                //拿到标签中 的 data-id 属性的 val
                var id=$(this).data('id');
                //确认提示框
                $.messager.confirm("FBI警告!","是否确定删除该权限管理?",function () {
                    //发出 AJAX 请求
                    //那么这里给 员工控制类 带去了 id,那么 类就要接收数据
                    $.get('/permission/delete.do',{id:id},handlerMessage)
                })
            })


        $(".btn_reload").click(function () {
            $.messager.confirm("FBI警告!","确定要重新加载吗?(时间可能过长)",function () {
                //发送 AJAX 请求
                $.get('/permission/reload.do',handlerMessage)
            })
        })
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#assign currentMenu="permission"/>
    <#--<c:set var="currentMenu" value="permission"/>-->
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>权限管理</h1>
        </section>
        <section class="content">
            <div class="box" >
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/permission/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <a href="javascript:;" class="btn btn-success btn_reload" style="margin: 10px;">
                        <span class="glyphicon glyphicon-repeat"></span>  重新加载
                    </a>
                </form>

                <table class="table table-striped table-hover" >
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>权限名称</th>
                        <th>权限表达式</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <#list pageInfo.list as permission>
                   <#-- <c:forEach items="${pageInfo.list}" var="permission" varStatus="vs">-->
                       <tr>
                           <td>${permission_index+1}</td>
                           <td>${permission.name!}</td>
                           <td>${permission.expression!}</td>
                           <td>
                               <a href="#" data-id="${permission.id}" class="btn btn-danger btn-xs btn_delete" >
                                   <span class="glyphicon glyphicon-trash"></span> 删除
                               </a>
                           </td>
                       </tr>
                    <#--</c:forEach>-->
                    </#list>
                    </table>
                <!--分页-->
                <#include "../common/page.ftl">
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
</body>
</html>
