<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>修改密码</title>
    <#include "../common/link.ftl" >
    <script>
        $(function () {
            $('#submitBtn').click(function () {
                $('#editForm').submit()
            })
            //表单验证
            $("#editForm").bootstrapValidator({
                feedbackIcons: { //图标
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields:{ //配置要验证的字段
                    oldPassword:{
                        validators:{
                            notEmpty:{ //不能为空
                                message:"原密码不能为空!" //错误时的提示信息
                            },
                        }
                    },
                    newPassword:{
                        validators:{
                            notEmpty:{ //不能为空
                                message:"新密码必须填!" //错误时的提示信息
                            },
                        }
                    }
                }
            }).on('success.form.bv', function() { //表单所有数据验证通过后执行里面的代码

                // $('#editForm').submit(); //不能再使用这个方法, submit 方法会触发验证

                //提交异步表单
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success){
                        window.location.href="/logout.do";
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
            <h1>修改密码</h1>
        </section>
        <section class="content">
            <div class="box" style="padding: 30px;" >
                <!--高级查询--->
                <form class="form-horizontal" action="/employee/updatePwd.do" method="post" id="editForm" >
                    <input type="hidden"  name="id" value='<@shiro.principal property="name" />'>
                    <div class="form-group" style="margin-top: 10px;">
                        <label  class="col-sm-3 control-label">原密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control"  name="oldPassword"
                                   placeholder="请输入原密码">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label class="col-sm-3 control-label">新密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control"  name="newPassword"
                                   placeholder="请输入新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button id="submitBtn" type="button" class="btn btn-primary">确定修改</button>
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
