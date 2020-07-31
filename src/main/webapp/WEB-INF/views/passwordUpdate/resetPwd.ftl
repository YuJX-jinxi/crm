<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>重置密码</title>
    <#include "../common/link.ftl" >
    <script>
        $(function () {
            $('#submitBtn').click(function () {

                $('#editForm').submit()//提交表单 : 使用了验证插件,在提交的时候会先进行验证,验证后不提交
            })

            $("#editForm").bootstrapValidator({
                feedbackIcons: { //图标
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: { //配置要验证的字段
                    newPassword: {
                        validators: { //验证的规则
                            notEmpty: { //不能为空
                                message: "新密码不能为空" //错误时的提示信息
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
                           window.location.href="/employee/list.do";

                       },500)
                    } else{
                        $.messager.popup(data.msg)
                    }
                })
            });
        })

    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl" >
    <!--菜单回显-->
    <#assign currentMenu="employee"/>
    <#--<c:set var="currentMenu" value="employee"/>-->
    <#include "../common/menu.ftl" >
    <div class="content-wrapper">
        <section class="content-header">
            <h1>重置密码</h1>
        </section>
        <section class="content">
            <div class="box" style="padding: 30px;" >
                <!--高级查询--->
                <form class="form-horizontal" action="/employee/resetPwd.do" method="post" id="editForm" >
                    <input type="hidden"  name="id" value="${employee.id}">
                    <div class="form-group" style="text-align: center;">
                       <h3>${employee.name!}</h3>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="newPassword" class="col-sm-3 control-label">新密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control"  name="newPassword"
                                   placeholder="请输入新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button id="submitBtn" type="button" class="btn btn-primary">确定重置</button>
                        </div>
                    </div>
                </form>

            </div>
        </section>
    </div>
    <#include "../common/footer.ftl" >
</div>
</body>
</html>
