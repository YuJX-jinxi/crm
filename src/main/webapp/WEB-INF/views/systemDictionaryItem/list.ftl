<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>字典明细管理</title>

    <#-- freemarker 没有 select 上下文的概念 , 要使用相对路径去找其他模板文件
            include :引入另一个文件
    -->
    <#include "../common/link.ftl">
    <script>
        $(function () {
            $(".btn-delete").click(function () {
                //获取当前点击的字典明细 id
                var id=$(this).data('id');
                //提示确认框
                $.messager.confirm("FBI警告!","是否确认删除?",function () {
                    //发送 AJAX 请求
                    $.get('/systemDictionaryItem/delete.do',{id:id},handlerMessage)
                })
            });

            //添加和编辑,添加点击事件,关联 class 属性
            $(".btn-input").click(function () {
                //每次点击之前,清空模块框的内容,因为点击编辑后,再点击添加,那么编辑的数据,就会残留在添加界面中
                $("#editForm input").val("");

                //回显字典目录的数据
                //把目录的名称设置到模态框的目录名称 input 中
                $("#editForm input[name=parentTitle]").val($('#dic .active').html());
             <#-- $("#editForm input[name=parentTitle]").val($('a[data-id=${qo.parentId!}]').html());-->
                $("#editForm input[name=parentId]").val(${qo.parentId!});

                //获取事件源,就是当前元素的id
                var json = $(this).data('json');
                //回显数据的操作
                //判断是否用户当前用户
                if (json) {
                $("#editForm input[name=id]").val(json.id);
                $("#editForm input[name=title]").val(json.title);
                $("#editForm input[name=sequence]").val(json.sequence);
                    //编辑回显,拿到 a 标签中的 自定义 id 属性,给它设值为 json.parentId 的 html 值
                    //即是当 a 自定义 id 标签的 文本信息
                $("#editForm input[name=parentTitle]").val($("a[data-id="+json.parentId+"]").html());
                $("#editForm input[name=parentId]").val(${qo.parentId!});

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
    <#assign currentMenu="systemDictionaryItem"/>
    <#--<c:set var="currentMenu" value="systemDictionaryItem"/>-->
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>字典明细管理</h1>
        </section>
        <section class="content">
            <div class="box">

                <div class="row" style="margin:20px">
                    <div class="col-xs-3">
                        <div class="panel panel-default">
                            <div class="panel-heading">字典目录</div>
                            <div class="panel-body">
                                <div class="list-group" id="dic">
                                    <#list dics as dic>
                                        <#-- 添加一个查询的条件,parentId=${dic.id},用于查询当前字典目录的明细-->
                                    <a data-id="${dic.id}" href="/systemDictionaryItem/list.do?parentId=${dic.id}" class="list-group-item" value="${dic.id}">${dic.title}</a>
                                    </#list>
                                </div>
                                <script>
                                    //回显目录(高亮效果   active 高亮)
                                    //使用 js 找到当前查询条件中的目录 a 链接 并添加高亮
                                    <#if qo.parentId??>
                                    $("a[data-id=${qo.parentId!}]").addClass('active')
                                    </#if>
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-9">
                        <table>
                            <!--高级查询--->
                            <form class="form-inline" id="searchForm" action="/systemDictionaryItem/list.do" method="post">
                                <input type="hidden" name="currentPage" id="currentPage" value="1">
                                <#-- 解决分页丢失目录的问题 -->
                                <input type="hidden" name="parentId" id="parentId" value="${qo.parentId!}">
                                <a href="#" class="btn btn-success btn-input" style="margin: 10px">
                                    <span class="glyphicon glyphicon-plus"></span> 添加
                                </a>
                            </form>
                            <!--编写内容-->
                            <div class="box-body table-responsive no-padding ">
                                <table class="table table-hover table-bordered">
                                    <tr>
                                        <th>编号</th>
                                        <th>标题</th>
                                        <th>序列号</th>
                                        <th>操作</th>
                                    </tr>
                                <#-- 不需要写 ${ } 这个符号了   as 后面是遍历时的变量名-->
                        <#list pageInfo.list as systemDictionaryItem>
                             <tr>
                             <#-- 后面加 ! 是为了防止空值错误 -->
                                 <td>${systemDictionaryItem_index+1}</td>
                                 <td>${systemDictionaryItem.title!}</td>
                                 <td>${systemDictionaryItem.sequence!}</td>
                                 <td>
                                 <#--
                                        使用 data-* 绑定的自定义数据
                                        data-json 不能使用双引号 ,因为 json数据中本身带有双引号-->
                                     <a href="#" data-json='${systemDictionaryItem.json!}' class="btn btn-info btn-xs btn-input">
                                         <span class="glyphicon glyphicon-pencil"></span> 编辑
                                     </a>
                                     <a href="#" data-id="${systemDictionaryItem.id}" class="btn btn-danger btn-xs btn-delete">
                                         <span class="glyphicon glyphicon-trash"></span> 删除
                                     </a>
                                 </td>
                             </tr>
                        </#list>
                                </table>
                                <!--分页-->
                    <#include "../common/page.ftl">
                            </div>
                        </table>
                    </div>
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
                <h4 class="modal-title" id="myModalLabel">新增/编辑字典明细</h4>
            </div>
            <#-- 中间需要显示的代码/表单样式-->
            <div class="modal-body">
                <form class="form-horizontal" action="/systemDictionaryItem/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">明细目录：</label>
                        <div class="col-sm-6">
                            <#--目录的标题,只是只读的,跟后台没关系,只是给用户看的
                                readonly  只读 -->
                            <input type="text" class="form-control" id="parentTitle" name="parentTitle" readonly >
                                <#--目录id , 提交到后台.关联用的,用于查询当前明细是哪个目录的-->
                            <input type="hidden" id="parentId" name="parentId" >7
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">明细标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title"
                                   placeholder="请输入明细标题">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">明细序号：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sequence" name="sequence"
                                   placeholder="请输入明细序号">
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
