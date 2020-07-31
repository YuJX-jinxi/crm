<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>跟进历史管理</title>
    <#include "../common/link.ftl">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#-- 记得要隔开,建立空格 -->
    <#include "../common/navbar.ftl">
    <#--菜单回显  c:set 主要作用是定义一个变量 var:变量名  value : 变量值
        之所以要定义一个变量 : menu.ftl 文件或 获取值,并设置菜单高亮效果,就是选中效果
        在任意一个地方 ${currentMenu} 去取值
    -->
    <#assign currentMenu="customerTraceHistory"/>
    <#--<c:set var="currentMenu" value="customerTraceHistory"/>-->
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>跟进历史管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/customerTraceHistory/list.do" method="post">
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
                            <th>姓名</th>
                            <th>跟进日期</th>
                            <th>跟进内容</th>
                            <th>跟进方式</th>
                            <th>跟进结果</th>
                            <th>录入人</th>
                        </tr>
                        <#-- 不需要写 ${ } 这个符号了   as 后面是遍历时的变量名-->
                        <#list pageInfo.list as customer>
                             <tr>
                                 <#-- 后面加 ! 是为了防止空值错误 -->
                                 <td>${customer_index+1}</td>
                                 <td>${(customer.customer.name)!}</td>
                                 <td>${customer.traceTime!?string('yyyy-MM-dd')}</td>
                                 <td>${customer.traceDetails!}</td>
                                 <td>${(customer.traceType.title)!}</td>
                                 <td>${customer.traceResult!}</td>
                                 <td>${(customer.inputUser.name)!}</td>
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
