﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>部门管理</title>
    <#include "../common/link.ftl" >
    <script>
        $(function () {
            //ajaxForm 把表单升级为 ajax 异步方式(针对submit 按钮使用)
            $("#editForm").ajaxForm(function (data) {
                if (data.success){
                    window.location.href="/department/list.do";
                }else{
                    $.messager.popup(data.msg)
                }
            })
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl" >
    <!--菜单回显-->
    <c:set var="currentMenu" value="department"/>
    <#include "../common/menu.ftl" >
    <div class="content-wrapper">
        <section class="content-header">
            <h1>部门编辑</h1>
        </section>
        <section class="content">
            <div class="box">
                <form class="form-horizontal" action="/department/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" value="${department.id}" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-1 control-label">名称：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="name" name="name" value="${department.name}"
                                   placeholder="请输入部门名">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-1 control-label">编码：</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sn" name="sn" value="${department.sn}"
                                   placeholder="请输入部门编码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="submitBtn" type="submit" class="btn btn-primary">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
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
