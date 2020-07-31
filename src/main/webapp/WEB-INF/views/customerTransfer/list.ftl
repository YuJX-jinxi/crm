<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>移交历史管理</title>
    <#include "../common/link.ftl">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <#assign currentMenu="customerTransfer"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>移交历史管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/customerTransfer/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">

                <#--添加高级查询功能-->
                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}" placeholder="请输入姓名/电话">
                    </div>
                <#-- 默认的提交方式 就是 submit -->
                    <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 查询</button>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>客户姓名</th>
                            <th>操作日期</th>
                            <th>操作人</th>
                            <th>旧销售人员</th>
                            <th>新销售人人员</th>
                            <th>移交原因</th>
                        </tr>
                        <#-- 不需要写 ${ } 这个符号了   as 后面是遍历时的变量名-->
                        <#list pageInfo.list as customer>
                             <tr>
                                 <#-- 后面加 ! 是为了防止空值错误 -->
                                 <td>${customer_index+1}</td>
                                 <td>${(customer.customer.name)!}</td>
                                 <td>${customer.operateTime!?string('yyyy-MM-dd')}</td>
                                 <td>${(customer.operator.name)!}</td>
                                 <td>${(customer.oldSeller.name)!}</td>
                                 <td>${(customer.newSeller.name)!}</td>
                                 <td>${customer.reason!}</td>
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
</body>
</html>
