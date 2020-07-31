<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>部门管理</title>

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
                    $.get('/department/delete.do',{id:id},handlerMessage)
                })
            });

            //添加和编辑,添加点击事件,关联 class 属性
            $(".btn-input").click(function () {
                //每次点击之前,清空模块框的内容,因为点击编辑后,再点击添加,那么编辑的数据,就会残留在添加界面中
                $("#editForm input").val("");
                //获取事件源,就是当前元素的id
                var json = $(this).data('json');
                //回显数据的操作
                //判断是否用户当前用户
                if (json) {
                $("#editForm input[name=id]").val(json.id);
                $("#editForm input[name=sn]").val(json.sn);
                $("#editForm input[name=name]").val(json.name)
            }
                //打开模态框
                $("#inputModal").modal('show');
            });

            //ajaxForm 把表单升级为 ajax 异步方式(针对submit 按钮使用)
            //但是现在的按钮 是 button类型的,所以使用 ajaxSubmit 提交
            $(".btn-submit").click(function () {
                $("#editForm").ajaxSubmit(handlerMessage)
            })

           /* $("#editForm").bootstrapValidator({
                feedbackIcons: { //图标
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: { //配置要验证的字段
                    name: {
                        validators: { //验证的规则
                            notEmpty: { //不能为空
                                message: "部门名称不能为空" //错误时的提示信息
                            },
                        }
                    }
                    sn: {
                        validators: { //验证的规则
                            notEmpty: { //不能为空
                                message: "请输入部门编码" //错误时的提示信息
                            },
                        }
                    }
                }
            }).on('success.form.bv', function() { //表单所有数据验证通过后执行里面的代码

                // $('#editForm').submit(); //不能再使用这个方法, submit 方法会触发验证

                //提交异步表单
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success){
                        $.messager.popup("重置成功!")
                        setTimeout(function () {
                            window.location.href="/department/list.do";

                        },500)
                    } else{
                        $.messager.popup(data.msg)
                    }
                })
            });*/


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
    <#assign currentMenu="department"/>
    <#--<c:set var="currentMenu" value="department"/>-->
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>部门管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/department/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <a href="#" class="btn btn-success btn-input" style="margin: 10px">
                        <span class="glyphicon glyphicon-plus"></span> 添加
                    </a>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>部门名称</th>
                            <th>部门编号</th>
                            <th>操作</th>
                        </tr>
                        <#-- 不需要写 ${ } 这个符号了   as 后面是遍历时的变量名-->
                        <#list pageInfo.list as department>
                             <tr>
                                 <#-- 后面加 ! 是为了防止空值错误 -->
                                 <td>${department_index+1}</td>
                                 <td>${department.name!}</td>
                                 <td>${department.sn!}</td>
                                 <td>
                                     <#--
                                            使用 data-* 绑定的自定义数据
                                            data-json 不能使用双引号 ,因为 json数据中本身带有双引号-->
                                     <a href="#" data-json='${department.json}' class="btn btn-info btn-xs btn-input">
                                         <span class="glyphicon glyphicon-pencil"></span> 编辑
                                     </a>
                                     <a href="#" data-id="${department.id}" class="btn btn-danger btn-xs btn-delete">
                                         <span class="glyphicon glyphicon-trash"></span> 删除
                                     </a>
                                 </td>
                             </tr>
                        </#list>
                        <#--<c:forEach items="${pageInfo.list}" var="department" varStatus="vs">
                            <tr>
                                <td>${vs.count}</td>
                                <td>${department.name}</td>
                                <td>${department.sn}</td>
                                <td>
                                    <a href="/department/input.do?id=${department.id}" class="btn btn-info btn-xs btn-input">
                                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                                    </a>
                                    <a href="#" data-id="${department.id}" class="btn btn-danger btn-xs btn-delete">
                                        <span class="glyphicon glyphicon-trash"></span> 删除
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>-->
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
                <h4 class="modal-title" id="myModalLabel">新增/编辑部门</h4>
            </div>

            <#-- 中间需要显示的代码/表单样式-->
            <div class="modal-body">
                <form class="form-horizontal" action="/department/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="请输入部门名">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">编码：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="sn"
                                   placeholder="请输入部门编码">
                        </div>
                    </div>
                </form>
            </div>


            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn  btn-submit">保存</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
