<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            $('#submitBtn').click(function () {
                //设置右边的下拉框在提交之前自动帮我们选中
                $('.selfRoles >option').prop('selected',true);
                $('#editForm').submit();//提交表单(使用了验证插件,在提交的时候会先进行验证,验证后不提交)
            })

            //解决修改时需要点击两次保存的问题
            $('html').one('mouseover',function(){
                //每次弹框弹起后都会进行一次校验，而且只校验一次
                $('#editForm').data("bootstrapValidator").validate();
            })

            //表单验证
            $("#editForm").bootstrapValidator({
                feedbackIcons: { //图标
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields:{ //配置要验证的字段
                    name:{
                        validators:{ //验证的规则
                            notEmpty:{ //不能为空
                                message:"用户名必填" //错误时的提示信息
                            },
                            stringLength: { //字符串的长度范围
                                min: 1,
                                max: 5
                            },
                            remote: {//远程验证,就是发一个请求到后台去验证
                                type: 'POST',
                                url: '/employee/checkName.do',
                                message: '用户名已存在',
                                delay: 500,
                                data: function() {  //自定义提交参数，默认只会提交当前用户名input的参数
                                    return {
                                        id: $('[name="id"]').val(),
                                        name: $('[name="name"]').val()
                                    };
                                }
                            }
                        }
                    },
                    password:{
                        validators:{
                            notEmpty:{ //不能为空
                                message:"密码必填" //错误时的提示信息
                            },
                        }
                    },
                    repassword:{
                        validators:{
                            notEmpty:{ //不能为空
                                message:"密码必填" //错误时的提示信息
                            },
                            identical: {//两个字段的值必须相同
                                field: 'password',
                                message: '两次输入的密码必须相同'
                            },
                        }
                    },
                    email: {
                        validators: {
                            emailAddress: {} //邮箱格式
                        }
                    },
                    age:{
                        validators: {
                            between: { //数字的范围
                                min: 18,
                                max: 60
                            }
                        }
                    }
                }
            }).on('success.form.bv', function() { //表单所有数据验证通过后执行里面的代码

               // $('#editForm').submit(); //不能再使用这个方法, submit 方法会触发验证

                //提交异步表单
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success){
                        window.location.href="list.do";
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
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#assign currentMenu="employee"/>
    <#--<c:set var="currentMenu" value="employee"/>-->
    <#include "../common/menu.ftl">

    <div class="content-wrapper">
        <section class="content-header">
            <h1>员工编辑</h1>
        </section>
        <section class="content">
            <div class="box">
                <form class="form-horizontal" action="/employee/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" value="${(employee.id)!}" name="id" >
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-6">
                            <input
                                    <#--<#if employee??>
                                    &lt;#&ndash;readonly  只读 &ndash;&gt;&lt;#&ndash;disabled 禁用 直接去后台更新的 sql 语句,,直接删掉不给它修改名称&ndash;&gt;
                                            disabled
                                    </#if>-->
                                   type="text" class="form-control" id="name" name="name" value="${(employee.name)!}" placeholder="请输入用户名">
                        </div>
                    </div>

                    <!-- 当用户是添加信息的时候,显示密码框让用户输入
                           如果是编辑框,应该不显示,不然别人会随意修改
                           empty : 判断当前对象是否为空的,如果为空的那么就显示数据
                           如果不为 null 那么就不显示
                     -->
                    <#-- employee 为空的时候,就是新增-->
                    <#if !employee??><#--判断对象是否有值   如果为空 返回true  前面的 ! 是取反的意思-->
                    <#--<c:if test="${empty employee}">-->

                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="password" name="password" value="${(employee.password)!}" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="repassword" class="col-sm-2 control-label">验证密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="repassword" name="repassword" value="${(employee.password)!}" placeholder="再输入一遍密码">
                        </div>
                    </div>

                   <#-- </c:if>-->
                    </#if>

                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">电子邮箱：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="email" name="email" value="${(employee.email)!}" placeholder="请输入邮箱">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="age" class="col-sm-2 control-label">年龄：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="age" name="age" value="${(employee.age)!}" placeholder="请输入年龄">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-2 control-label">部门：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="dept" name="dept.id">
                                <!-- 回显部门信息 -->
                                <#list departments as d>
                                <#--<c:forEach items="${departments}" var="d">-->
                                    <option value="${d.id}">${d.name}</option>
                               <#-- </c:forEach>-->
                                </#list>
                            </select>

                            <#-- 把员工本身的 部门信息回显 -->
                            <script>
                                $("#dept").val(${(employee.dept.id)!})
                            </script>
                        </div>
                    </div>

                    <div class="form-group" id="adminDiv">
                        <label for="admin" class="col-sm-2 control-label">超级管理员：</label>

                        <div class="col-sm-6"style="margin-left: 15px;">
                            <input type="checkbox" id="admin" name="admin" class="checkbox">

                            <#-- ${employee.admin} 判断当前用户是否是超级管理员,而用作编辑时候的回显
                                    ! 感叹号 后面跟着的是默认值-->
                            <#if (employee.admin)!false>
                            <#--<c:if test="${employee.admin}">-->
                                <script>
                                    //如果当前用户是超级管理员
                                    //加载时把按钮设置为 true
                                    $("#admin").prop("checked",true);
                                </script>
                           <#-- </c:if>-->
                            </#if>
                        </div>
                    </div>

                    <div class="form-group " id="role">
                        <label for="role" class="col-sm-2 control-label">分配角色：</label><br/>
                        <div class="row" style="margin-top: 10px">

                            <!-- 把所有角色显示出来,给用户选择 -->
                            <div class="col-sm-2 col-sm-offset-2">
                                <select multiple class="form-control allRoles" size="15">
                                    <!-- 获取跳转过来的角色数组 -->
                                    <#list roles as role>
                                    <#--<c:forEach items="${roles}" var="role">-->
                                        <option value="${role.id}">${role.name}</option>
                                    <#--</c:forEach>-->
                                    </#list>
                                </select>
                            </div>

                            <div class="col-sm-1" style="margin-top: 60px;" align="center">
                                <div>

                                    <a type="button" class="btn btn-primary  " style="margin-top: 10px" title="右移动"
                                       onclick="moveSelected('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-menu-right"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="左移动"
                                       onclick="moveSelected('selfRoles', 'allRoles')">
                                        <span class="glyphicon glyphicon-menu-left"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全右移动"
                                       onclick="moveAll('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全左移动"
                                       onclick="moveAll('selfRoles', 'allRoles')" >
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a>
                                </div>
                            </div>

                            <div class="col-sm-2">
                                <select multiple class="form-control selfRoles" size="15" name="ids">
                                    <#list (employee.roles)! as role>
                                    <#--<c:forEach items="${employee.roles}" var="r">-->
                                        <option value="${role.id}">${role.name}</option>
                                    <#--</c:forEach>-->
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>

                    <#--左右移动的按钮-->

                    <script>
                        /*点击事件的代码都不需要填写到加载完的函数框中,不然当按钮的点击事件会被加载了,
                        * 就是被占用了,会报一个当前函数方法找不到的错误信息 */

                        //编写的是全部移动的函数方法
                        function moveAll(src,target) {
                            //事件中获取到左边'selfRoles'的全部的option移动'allRoles'
                            $("."+target).append($("."+src+">option"))
                        }
                        function moveSelected(src,target) {
                            //事件中获取到左边'selfRoles'的全部的option移动'allRoles'
                            $("."+target).append($("."+src+">option:selected"))
                        }

                        //对于超级管理员的选中问题---->点击按钮
                        var roleDiv;
                        //找到按钮,并给按钮添加点击事件
                        $("#admin").click(function () {
                            //获取当前按钮的状态
                            var checked = $(this).prop('checked');
                            //判断是否是勾选状态
                            if(checked){
                                //删除角色的div
                                //为什么不使用remove,因为这个删除js也会被删除
                                //detach这个方法会返回被删除的内容,所以用来接收
                                roleDiv = $("#role").detach();

                                //console.log(roleDiv);
                            }else{
                                //恢复角色div,加到超管的后面
                                $("#adminDiv").after(roleDiv);
                            }
                        })


                        //获取超级管理员的复选框的当前状态
                        var checked = $("#admin").prop('checked');
                        //判断当前状态是否为true,如果是,就干掉 角色框的内容
                        if(checked){
                            //删除角色的div
                            roleDiv = $("#role").detach();
                        }else{
                            //恢复角色div,加到超管的后面
                            $("#adminDiv").after(roleDiv);
                        }

                        //角色去重的效果
                        //解决：页面加载完，拿左边两边的option 对比，遍历左边每个角色，若已经在右边列表内，则需要删除。
                        //1.把已有的角色id放入一个数组中(右边)
                        //each 遍历方法
                        var ids = [];
                        $(".selfRoles > option").each(function (i, ele) {
                            // i 是索引
                            //ele 是元素本身是一个 DOM 对象,那么要使用 jQuery 方法,就需要把他包装成 jQuery 对象
                            // push 添加方法 是 js 方法来的
                            ids.push( $(ele).val());
                        })
                        //2.遍历所有的角色(左边)
                        $(".allRoles > option").each(function (i, ele) {
                            //拿到左边的全部 id 值
                            var id = $(ele).val();
                            //3.判断是否存在ids数组中,如果是就删除掉自己
                            //inArray("你要判断的值",一个数组),如果存在就返回存在的索引
                            if($.inArray(id,ids)!=-1){
                                //返回一个索引,就干掉他,直到等于-1
                                $(ele).remove();
                            }
                        })
                    </script>

                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <!-- 这里的按钮选择为什么选择 button 因为再提交之前,还需要添加
                                事件,因为员工部门有 角色选择,提交之前要把状态转成选中状态
                            -->
                            <button id="submitBtn" type="button" class="btn btn-primary">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
                        </div>
                    </div>

                </form>
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
</body>
</html>
