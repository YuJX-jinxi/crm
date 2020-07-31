<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>客户管理</title>

    <#-- freemarker 没有 select 上下文的概念 , 要使用相对路径去找其他模板文件
            include :引入另一个文件
    -->
    <#include "../common/link.ftl">
    <script>
        $(function () {
            $(".btn-delete").click(function () {
                //获取当前点击的客户 id
                var id=$(this).data('id');
                //提示确认框
                $.messager.confirm("FBI警告!","是否确认删除?",function () {
                    //发送 AJAX 请求
                    $.get('/customer/delete.do',{id:id},handlerMessage)
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
                $("#editForm input[name=name]").val(json.name);
                $("#editForm input[name=age]").val(json.age)
                $("#editForm select[name=gender]").val(json.gender)
                $("#editForm input[name=tel]").val(json.tel)
                $("#editForm input[name=qq]").val(json.qq)
                    //左边看模态框,右边看 domain 的 getjson
                $("#editForm select[name='job.id']").val(json.jobId)
                $("#editForm select[name='source.id']").val(json.sourceId)
            }
                //打开模态框
                $("#editModal").modal('show');
            });

            //ajaxForm 把表单升级为 ajax 异步方式(针对submit 按钮使用)
            //但是现在的按钮 是 button类型的,所以使用 ajaxSubmit 提交
            $(".btn-submit").click(function () {
                $("#editForm").ajaxSubmit(handlerMessage)
            })

            //---------------------   客户跟进 模态框 --------------------------------

            //添加和编辑,添加点击事件,关联 class 属性
            $(".btn-trace").click(function () {
                //每次点击之前,清空模块框的内容,因为点击编辑后,再点击添加,那么编辑的数据,就会残留在添加界面中
                $("#traceForm input,#traceForm select").val("");
                //获取事件源,就是当前元素的id
                var json = $(this).data('json');
                //回显数据的操作
                //判断是否用户当前用户
                if (json) {
                    //json 是客户的信息
                    $("#traceForm input[name='customer.name']").val(json.name);
                    $("#traceForm input[name='customer.id']").val(json.id);
                }
                //打开模态框
                $("#traceModal").modal('show');
            });
            //提交方式
            $(".trace-submit").click(function () {
                $("#traceForm").ajaxSubmit(handlerMessage)
            })

            //---------------------   客户移交 模态框 --------------------------------

            //添加和编辑,添加点击事件,关联 class 属性
            $(".btn-transfer").click(function () {
                //每次点击之前,清空模块框的内容,因为点击编辑后,再点击添加,那么编辑的数据,就会残留在添加界面中
                $("#transferModal input,#transferModal select").val("");
                //获取事件源,就是当前元素的id
                var json = $(this).data('json');
                //回显数据的操作
                //判断是否用户当前用户
                if (json) {
                    //json 是客户的信息
                    $("#transferModal input[name='customer.name']").val(json.name);
                    $("#transferModal input[name='customer.id']").val(json.id);
                    //旧销售人员
                    $("#transferModal input[name='oldSeller.name']").val(json.sellerName);
                    $("#transferModal input[name='oldSeller.id']").val(json.sellerId);
                }
                //打开模态框
                $("#transferModal").modal('show');
            });
            //提交方式,对应的名称
            $(".transfer-submit").click(function () {
                $("#transferForm").ajaxSubmit(handlerMessage)
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
    <#assign currentMenu="customer_potential"/>
    <#--<c:set var="currentMenu" value="customer"/>-->
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>客户管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/customer/potentialList.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">

                    <#--添加高级查询功能-->
                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${qo.keyword!}" placeholder="请输入姓名/电话">
                    </div>

                    <@shiro.hasRole name="admin">

                    <div class="form-group">
                        <label for="dept"> 销售人员:</label>
                        <select class="form-control" id="seller" name="sellerId">
                            <option value="-1">全部</option>
                            <!-- 看看 controller 带过来页面显示的是什么参数-->
                               <#list sellers as s>
                                    <option value="${s.id}">${s.name}</option>
                                </#list>
                        </select>
                        <script>
                           $("#seller").val(${qo.deptId!})
                         </script>
                     </div>
                     </@shiro.hasRole>
                 <#-- 默认的提交方式 就是 submit -->
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
                            <th>姓名</th>
                            <th>电话</th>
                            <th>QQ</th>
                            <th>职业</th>
                            <th>来源</th>
                            <th>销售员</th>
                            <th>状态</th>
                            <th>录入时间</th>
                            <th>操作</th>
                        </tr>
                        <#-- 不需要写 ${ } 这个符号了   as 后面是遍历时的变量名-->
                        <#list pageInfo.list as customer>
                             <tr>
                                 <#-- 后面加 ! 是为了防止空值错误 -->
                                 <td>${customer_index+1}</td>
                                 <td>${customer.name!}</td>
                                 <td>${customer.tel!}</td>
                                 <td>${customer.qq!}</td>
                                 <td>${(customer.job.title)!}</td>
                                 <td>${(customer.source.title)!}</td>
                                 <td>${(customer.seller.name)!}</td>
                                 <td>${customer.statusName!}</td>
                                     <#-- freemarker 不能直接渲染日期对象,需要转成字符串,给他一个格式-->
                                 <td>${customer.inputTime!?string('yyyy-MM-dd HH:mm:ss')}</td>
                                 <td>
                                     <#--
                                            使用 data-* 绑定的自定义数据
                                            data-json 不能使用双引号 ,因为 json数据中本身带有双引号-->
                                     <a href="#" data-json='${customer.json!}' class="btn btn-info btn-xs btn-input">
                                         <span class="glyphicon glyphicon-pencil"></span> 编辑
                                     </a>

                                     <#--<a href="#" data-id="${customer.id}" class="btn btn-danger btn-xs btn-delete">
                                         <span class="glyphicon glyphicon-trash"></span> 删除
                                     </a>-->
                                         <#-- 把编辑的里面的 json 数据 拿到跟进中拿出来-->
                                     <a href="#" class="btn btn-danger btn-xs btn-trace" data-json='${customer.json!}'>
                                         <span class="glyphicon glyphicon-phone"></span> 跟进
                                     </a>

                                         <!--管理员和经理才能看到该下拉框-->
                                     <@shiro.hasAnyRoles name="admin,Market_Manager">
                                     <#-- 由于是需要回显的,那么同样把 json 里面拿数据出来-->
                                         <a href="#"
                                            class="btn btn-danger btn-xs btn-transfer" data-json='${customer.json!}'>
                                             <span class="glyphicon glyphicon-phone"></span> 移交
                                         </a>
                                     </@shiro.hasAnyRoles>
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
<#-- 新增/编辑客户 模态框-->
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title inputTitle">客户编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customer/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="name"
                                   placeholder="请输入客户姓名"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户年龄：</label>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" name="age"
                                   placeholder="请输入客户年龄"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户性别：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="gender">
                                <option value="1">男</option>
                                <option value="0">女</option>
                                <option value="">请选择性别</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户电话：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="tel"
                                   placeholder="请输入客户电话"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户QQ：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="qq"
                                   placeholder="请输入客户QQ"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户工作：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="job.id">
                                <option value="">请选择客户工作</option>
                                <#list jobs as j>
                                    <option value="${j.id}">${j.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户来源：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="source.id">
                                <option value="">请选择客户来源</option>
                                <#list sources as s>
                                    <option value="${s.id}">${s.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-submit" >保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
            </div>
        </div>
    </div>
</div>

<#--客户跟进的模态框-->
<div class="modal fade" id="traceModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">跟进</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTraceHistory/saveOrUpdate.do" method="post" id="traceForm">
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">客户姓名：</label>
                        <div class="col-lg-6">
                            <#-- 起名要见名知义,防止弄错-->
                            <input type="text" readonly name="customer.name" class="form-control">
                            <#-- 起名要见名知义,防止弄错-->
                            <input type="hidden" name="customer.id">
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">跟进时间：</label>
                        <div class="col-lg-6 ">
                            <input type="text" class="form-control"  name="traceTime"  placeholder="请输入跟进时间">
                        </div>

                        <script>
                            //跟进时间使用日期插件
                            $("input[name=traceTime]").datepicker({
                                language: "zh-CN", //指定语言
                                autoclose: true, //选择日期后自动关闭
                                todayHighlight: true, //高亮今日日期
                                //还可以设置 最小时间,自己百度
                                endDate:new Date() //最大的时间,超过时间范围就不能选择
                            });
                        </script>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-4 control-label">交流方式：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceType.id">
                                <option value="">请选择交流方式</option>
                                <#-- 查询指定字典目录,查询出字典明细出来遍历-->
                                <#list ccts as c>
                                    <option value="${c.id}">${c.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">跟进结果：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceResult">
                                <#--用作添加时给客户提醒-->
                                <option value="">请选择跟进结果</option>
                                <option value="3">优</option>
                                <option value="2">中</option>
                                <option value="1">差</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">跟进记录：</label>
                        <div class="col-lg-6">
                            <textarea type="text" class="form-control" name="traceDetails"
                                      placeholder="请输入跟进记录" ></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary trace-submit">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<#-- 客户移交 模态框 -->
<div id="transferModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">移交</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTransfer/saveOrUpdate.do" method="post" id="transferForm" style="margin: -3px 118px">
                    <div class="form-group" >
                        <label for="name" class="col-sm-4 control-label">客户姓名：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control"  name="customer.name"   readonly >
                            <input type="hidden" class="form-control"  name="customer.id"  >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">旧销售人员：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control"  name="oldSeller.name" readonly >
                            <input type="hidden" class="form-control"  name="oldSeller.id"  >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">新销售人员：</label>
                        <div class="col-sm-8">
                            <select name="newSeller.id" class="form-control">
                                <option value="">请选择新销售人员</option>
                                <!-- 看看 controller 带过来页面显示的是什么参数-->
                               <#list sellers as s>
                                    <option value="${s.id}">${s.name}</option>
                               </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sn" class="col-sm-4 control-label">移交原因：</label>
                        <div class="col-sm-8">
                            <textarea type="text" class="form-control" id="reason" name="reason" cols="10" ></textarea>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary transfer-submit" >保存</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
