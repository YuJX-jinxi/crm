<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>课程订单管理</title>

    <#-- freemarker 没有 select 上下文的概念 , 要使用相对路径去找其他模板文件
            include :引入另一个文件
    -->
    <#include "../common/link.ftl">
    <script>
        $(function () {
            $(".btn-delete").click(function () {
                //获取当前点击的部门 id
                var id=$(this).data('id');
                //提示确认框
                $.messager.confirm("FBI警告!","是否确认删除?",function () {
                    //发送 AJAX 请求
                    $.get('/courseorder/delete.do',{id:id},handlerMessage)
                })
            });

            //添加和编辑,添加点击事件,关联 class 属性
            $(".btn-input").click(function () {
                //每次点击之前,清空模块框的内容,因为点击编辑后,再点击添加,那么编辑的数据,就会残留在添加界面中
                $("#editForm input,#editForm select").val("");
                //获取事件源,就是当前元素的id
                var json = $(this).data('json');
                //回显数据的操作
                //判断是否用户当前用户
                if (json) {
                $("#editForm input[name=id]").val(json.id);
                $("#editForm input[name='customer.id']").val(json.customerId);
                $("#editForm input[name='customer.name']").val(json.customerName);
                $("#editForm select[name='classinfo.id']").val(json.classinfoId);
                $("#editForm input[name=money]").val(json.money)
            }
                //打开模态框
                $("#inputModal").modal('show');
            });

            //ajaxForm 把表单升级为 ajax 异步方式(针对submit 按钮使用)
            //但是现在的按钮 是 button类型的,所以使用 ajaxSubmit 提交
            $(".btn-submit").click(function () {
                $("#editForm").ajaxSubmit(handlerMessage)
            })
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#-- 记得要隔开,建立空格 -->
    <#include "../common/navbar.ftl">
    <#--菜单回显  c:set 主要作用是定义一个变量 var:变量名  value : 变量值
        之所以要定义一个变量 : menu.ftl 文件或 获取值,并设置菜单高亮效果,就是选中效果
        在任意一个地方 ${currentMenu} 去取值
    -->
    <#assign currentMenu="courseorder"/>
    <#--<c:set var="currentMenu" value="courseorder"/>-->
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>课程订单管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/courseorder/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">

                <#--添加高级查询功能-->
                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}" placeholder="请输入客户姓名">
                    </div>
                    <div class="form-group">
                        <label for="dept"> 班级课程:</label>
                        <select class="form-control" id="classId" name="classId">
                            <option value="-1">全部</option>
                            <!-- 看看 controller 带过来页面显示的是什么参数-->
                               <#list classInfos as s>
                                    <option value="${s.id}">${s.name}</option>
                               </#list>
                        </select>
                        <script>
                            $("#seller").val(${qo.classId!})
                        </script>

                    </div>
                    <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 查询</button>
                    <a href="#" class="btn btn-success btn-input" style="margin: 10px">
                        <span class="glyphicon glyphicon-plus"></span> 添加
                    </a>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>客户名称</th>
                            <th>班级课程</th>
                            <th>销售时间</th>
                            <th>销售金额</th>
                            <th>操作</th>
                        </tr>
                        <#-- 不需要写 ${ } 这个符号了   as 后面是遍历时的变量名-->
                        <#list pageInfo.list as courseorder>
                             <tr>
                                 <#-- 后面加 ! 是为了防止空值错误 -->
                                 <td>${courseorder_index+1}</td>
                                 <td>${(courseorder.customer.name)!}</td>
                                 <td>${(courseorder.classinfo.name)!}</td>
                                 <td>${courseorder.inputTime!?string('yyyy-MM-dd')}</td>
                                 <td>${courseorder.money!}</td>
                                 <td>
                                     <a href="#" data-json='${courseorder.json!}' class="btn btn-info btn-xs btn-input">
                                         <span class="glyphicon glyphicon-pencil"></span> 编辑
                                     </a>
                                 </td>
                             </tr>
                        </#list>
                    </table>
                    <!--分页-->
                    <#include "../common/page.ftl">
                </div>
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>


<div class="modal fade" id="inputModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增/删除课程订单</h4>
            </div>

            <#-- 中间需要显示的代码/表单样式-->
            <div class="modal-body">
                <form class="form-horizontal" action="/courseorder/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">选择客户：</label>
                        <div class="col-sm-6">

                            <#if !courseorder??>

                            <select class="form-control" name="customer.id" id="customer.id">
                                <option value="">请选择客户</option>
                                <#list customers as customer>
                                    <option value="${customer.id}">${customer.name}</option>
                                </#list>
                            </select>
                        <#else>
                        <#-- 起名要见名知义,防止弄错-->
                            <input type="text" readonly name="customer.name" class="form-control">
                        <#-- 起名要见名知义,防止弄错-->
                            <input type="hidden" name="customer.id">
                            </#if>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">班级课程：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="classinfo.id">
                                <option value="">请选择班级课程</option>
                                <#list classInfos as class>
                                    <option value="${class.id}">${class.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">销售金额：</label>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" id="money" name="money"
                                   placeholder="请输入销售金额">
                        </div>
                    </div>
                </form>
            </div>


            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn  btn-submit">提交</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
