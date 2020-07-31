<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            //拿到删除的标签,并添加点击事件
            $(".btn_delete").click(function () {
                //点击事件要做什么
                //拿到标签中 的 data-id 属性的 val
                var id=$(this).data('id');
                //确认提示框
                $.messager.confirm("FBI警告!","是否确定删除该员工信息?",function () {
                    //发出 AJAX 请求
                    //那么这里给 员工控制类 带去了 id,那么 类就要接收数据
                    $.get('/employee/delete.do',{id:id},handlerMessage)
                })
            })
            $(".btn_state").click(function () {
                debugger
                var id=$(this).data('id');
                var status=$(this).data('status');
                $.messager.confirm("FBI警告!","是否确定设置该员工状态?",function () {
                    $.get('/employee/status.do',{id:id,status:status},handlerMessage)
                })
            })

            // 批量删除 全选和反选的操作
            $("#allCheck").click(function () {
                //先获取到当前复选框的状态
                var checked=$(this).prop('checked');
                //设置到 list 列表中的复选框
                $(".cb").prop('checked',checked)
            })

            //列表中的复选框事件
            $(".cb").click(function () {
               //检测是否已经全选(判断选中的复选框的个数,是否等于所有列表的复选框的个数
                //下面相当于是一个 Boolean类型
                //$(".cb").length==$(".cb:checked").length
                //可以理解为 : 当前  标签个数 == 当前标签状态是选中的个数  相等那么结果为 true   不等为false
                //结果为 true 那么证明相等,全选框也全选上 为false 就反之
                $("#allCheck").prop('checked',$(".cb").length==$(".cb:checked").length)
            })

            //批量删除的点击事件
            $(".btn-batchDelete").click(function () {
                //获取用户勾选的数据,长度为 0 就是没有选中
                var $cb=$(".cb:checked");
                //判断当前用户在点击批量删除按钮之前有没有勾选上需要删除的对象
                if ($cb.length==0) {
                    //如果没有勾选上,那么就发出一个弹出框警告
                    $.messager.alert("FBI 警告!","请选中需要删除的数据 ! ! !")
                    //有 else 就继续写,没有就需要 return(结束)
                    return;
                }
                //确认框  如果有勾选上数据,就会执行到下面的代码
                $.messager.confirm("FBI警告!","是否确认删除?",function () {
                    var ids=[];//用一个数组来接收
                    //n.fn.init [input.cb, prevObject: n.fn.init(1), context: document, selector: ".cb:checked"]
                    console.log($cb);//从上面的数据可以看出,它是一个数组,存放的是当前的标签,代表可以从里面拿数据出来
                    //那么现在开始遍历数据,拿到当前复选框的id
                    $cb.each(function (i,ele) {
                        //添加到数组中存起来
                        ids.push($(ele).data('id'))
                    })
                    console.log(ids);
                    //发送 AJAX 请求
                    $.post('/employee/batchDelete.do',{ids:ids},handlerMessage)
                })
            })

            //导入按钮
            $(".btn-import").click(function () {
                $("#importModal").modal('show');
            })
            //提交文件上传表单
            $(".btn-submit").click(function () {
                //把表单转换成异步的提交
                $("#importForm").ajaxSubmit(handlerMessage);
            })
            //拿到只看部门的复选框,给他添加点击事件
            $("#zk").click(function () {
                //拿到当前复选框的状态
                var checked=$("#zk").prop("checked");
                console.log(checked);
                console.log($(this).val());
                //判断当前状态
                if (checked){
                    //如果是 true 那么就设置给 部门里面去显示
                    $("#dept").val($(this).val())
                }else{
                    //如果是 false 那么就显示全部
                    $("#dept").val(-1)
                }
            })
            if ($("#dept").val()==$('#zk').val()){
                $("#zk").prop("checked",true)
            }else{
                $("#zk").prop("checked",false)
            }

        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#assign currentMenu="employee"/>
    <#--<c:set var="currentMenu" value="employee"/>-->
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>员工管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <form class="form-inline" id="searchForm" action="/employee/list.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <div class="form-group">
                            <label for="keyword">关键字:</label>
                            <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}" placeholder="请输入姓名/邮箱">
                        </div>
                        <div class="form-group">
                            <label for="dept"> 部门:</label>
                            <select class="form-control" id="dept" name="deptId">
                                <option value="-1">全部</option>
                                <!-- 看看 controller 带过来页面显示的是什么参数-->
                                <#--<c:forEach items="${departments}" var="d">
                                    <option value="${d.id}">${d.name}</option>
                                </c:forEach>-->
                                <#list departments as d>
                                    <option value="${d.id}">${d.name}</option>
                                </#list>
                            </select>
                            <script>
								$("#dept").val(${qo.deptId!})
                            </script>
                        </div>&nbsp;&nbsp;&nbsp;
                        <input type="checkbox" value='<@shiro.principal property="deptId"/>' id="zk">只看本部门&nbsp;&nbsp;&nbsp;
                        <#-- 默认的提交方式 就是 submit -->
                        <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 查询</button>

                        <a href="/employee/input.do" class="btn btn-success btn_redirect">
                            <span class="glyphicon glyphicon-plus"></span> 添加
                        </a>
                        <@shiro.hasRole name="admin">
                        <a href="#" class="btn btn-danger btn-batchDelete">
                            <span class="glyphicon glyphicon-trash"></span> 批量删除
                        </a>
                        </@shiro.hasRole>
                        <a href="/employee/exportXls.do" class="btn btn-warning" >
                            <span class="glyphicon glyphicon-download"></span> 导出
                        </a>
                        <a href="#" class="btn btn-warning btn-import">
                            <span class="glyphicon glyphicon-upload"></span> 导入
                        </a>
                    </form>
                </div>
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="allCheck"></th>
                        <th>编号</th>
                        <th>名称</th>
                        <th>email</th>
                        <th>年龄</th>
                        <th>部门</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <#list  pageInfo.list as employee>
                    <#--<c:forEach items="${pageInfo.list}" var="employee" varStatus="vs">-->
                        <tr>
                            <td><input type="checkbox" class="cb" data-id="${employee.id}"></td>
                            <td>${employee_index+1}</td>
                            <td>${employee.name!}</td>
                            <td>${employee.email!}</td>
                            <td>${employee.age!}</td>
                            <td>${(employee.dept.name)!}</td>
                            <td align="center" >${employee.status?string("<span class='text-success'>正常</span>","<span class='text-danger'>禁用</span>")}</td>
                            <td>

                                <a href="/employee/input.do?id=${employee.id}" class="btn btn-info btn-xs btn_redirect">
                                    <span class="glyphicon glyphicon-pencil"></span> 编辑
                                </a>

                                <a href="#" data-id="${employee.id}" class="btn btn-danger btn-xs btn_delete">
                                    <span class="glyphicon glyphicon-trash"></span> 删除
                                </a>

                                <@shiro.hasRole name="admin">
                                <a href="/resetPwd.do?id=${employee.id}" class="btn btn-warning btn-xs btn_update">
                                    <span class="glyphicon glyphicon-tags"></span> 重置密码
                                </a>

                                <a href="#"data-id="${employee.id}" data-status="${employee.status?string("true","false")}" class="btn ${employee.status?string("btn-success","btn-danger")} btn-xs btn_state">
                                    <span class="${employee.status?string("glyphicon glyphicon-ok","glyphicon glyphicon-remove")}"></span> ${employee.status?string("恢复","禁用")}
                                </a>
                                </@shiro.hasRole>
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
<div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">导入</h4>
            </div>
            <div class="modal-body">
                <#--   enctype="multipart/form-data"  添加表单设置
                        在对应的路径下添加方法
                -->
                <form class="form-horizontal" action="/employee/importXls.do" method="post" id="importForm" enctype="multipart/form-data">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label"></label>
                        <div class="col-sm-6">
                            <input type="file" name="file">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-6">
                            <#--在对应的路径行 添加模板-->
                            <a href="/xlstemplates/employee_import.xls" class="btn btn-success" >
                                <span class="glyphicon glyphicon-download"></span> 下载模板
                            </a>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-submit">保存</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
