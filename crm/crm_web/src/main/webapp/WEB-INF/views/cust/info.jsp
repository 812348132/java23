<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-我的客户</title>
    <%@ include file="../base/base-css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="customer_my"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/customer/edit/${customer.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                        <button id="transferCustomerToAccount" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                        <button id="transferpubuicBtn" rel="${customer.id}" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>
                        <button id="delBtn" rel="${customer.id}" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.custName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.mobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td class="star">${customer.level}</td>
                        </tr>
                        <c:if test="${not empty customer.address}">
                            <tr>
                                <td class="td_title">地址</td>
                                <td colspan="5">${customer.address}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty customer.mark}">
                            <tr>
                                <td class="td_title">备注</td>
                                <td colspan="5">${customer.mark}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <div class="box-footer">
                    <c:if test="${not empty customer.reminder}">
                        <span style="color: #ccc;"><i class="fa fa-exchange"></i> ${customer.reminder}</span>
                    </c:if>
                    <span style="color: #ccc" class="pull-right">
                        创建日期：<fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd HH:mm"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${not empty customer.updateTime}">
                            最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                        </c:if>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">销售机会
                                <small><button id="addSales" rel="${customer.id}" class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small>
                            </h3>
                        </div>
                        <div class="box-body">
                            <c:if test="${empty salesRecordList}">
                                <li class="list-group-item">暂无销售机会</li>
                            </c:if>
                            <ul class="list-group">
                                <c:forEach items="${salesRecordList}" var="chance">
                                    <li class="list-group-item"><a href="/sales/record/${chance.id}" target="_blank">${chance.salesName}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box-body" style="text-align: center;">
                        <img src="/customer/qrcode/${customer.id}">
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">待办事项</h3>
                            <div class="box-tools">
                                <button class="btn btn-sm btn-success" id="showAddTaskModal"><i class="fa fa-plus"></i></button>
                            </div>
                        </div>
                        <div class="box-body">
                            <ul class="todo-list">
                                <c:forEach items="${taskList}" var="task">
                                    <c:if test="${task.done != true}">
                                        <li class="${task.done ? 'done' : ''}" >
                                            <input type="checkbox"  class="task_checkbox" ${task.done ? 'checked' : ''} value="${task.id}">
                                            <span class="text">${task.title}</span>
                                            <small class="label label-danger"><i class="fa fa-clock-o"></i> ${task.doneTime}</small>
                                            <div class="tools">
                                                <i class="fa fa-edit update_task" rel="${task.id}"></i>
                                                <i class="fa fa-trash-o del_task" rel="${task.id}"></i>
                                            </div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>


        </section>
        <!-- /.content -->
    </div>


    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
        Launch demo modal
    </button>

    <div class="modal fade" id="accountModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加待办任务</h4>
                </div>
                <div class="modal-body">
                    <select id="accountId" class="form-control" style="width: 100%">
                        <option value=""></option>
                        <c:forEach items="${accountList}" var="account">
                            <option value="${account.id}">${account.userName} ( ${account.mobile} )</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="">确定</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <%--新增待办任务MODEL--%>
    <div class="modal fade" id="taskModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">选择转入员工</h4>
                    </div>
                    <div class="modal-body">
                        <form action="/customer/newtask" id="saveTaskForm" method="post">
                            <input type="hidden" name="customerId" value="${customer.id}">
                            <div class="form-group">
                                <label>任务名称</label>
                                <input type="text" class="form-control" name="title">
                            </div>
                            <div class="form-group">
                                <label>完成日期</label>
                                <input type="text" class="form-control" id="datepicker" name="doneTime">
                            </div>
                            <div class="form-group">
                                <label>提醒时间</label>
                                <input type="text" class="form-control" id="datepicker2" name="alertTime">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="addCustTask">确定</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/select2/select2.min.js"></script>
<script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script>
    $(function () {
        $("#accountId").select2();

        /*获取客户ID*/
        var custId = ${customer.id};

        $("#delBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("删除客户会自动删除相关数据，确定吗?",function(){
                window.location.href = "/customer/del/"+id;
            });
        });

        $("#transferpubuicBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要放入公海吗?",function () {
                window.location.href="/customer/transferpubuic/"+id;
            });
        });
        $("#transferCustomerToAccount").click(function () {
            $("#accountModal").modal({
                show:true,
                backdrop:"static"
            })
        });
        /*转交给其他人*/
        $("#tranBtnOk").click(function () {
            var accountId = $("#accountId").val();
            if(!accountId) {
                layer.msg("请选择转入账号");
                return;
            }
            window.location.href = "/customer/"+custId+"/tran/"+accountId;
        });

        $("#addSales").click(function () {
            var id = $(this).attr("rel");
            window.location.href="/sales/new/"+id;
        });

        var picker = $('#datepicker').datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true,
            startDate:moment().format("yyyy-MM-dd")
        });
        picker.on("changeDate",function (e) {
            var today = moment().format("YYYY-MM-DD");
            $('#datepicker2').datetimepicker('setStartDate',today);
            $('#datepicker2').datetimepicker('setEndDate', e.format('yyyy-mm-dd'));
        });


        var timepicker = $('#datepicker2').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true
        });

        $("#showAddTaskModal").click(function () {
            $("#taskModal").modal({
                show : true,
                backdrop:'static'
            })
        });

        $("#addCustTask").click(function () {
            $("#saveTaskForm").submit();
        });
        $("#saveTaskForm").validate({
            errorClass:"text-danger",
            errorElement:'span',
            rules:{
                title:{
                    required : true
                },
                doneTime:{
                    required : true
                }
            },
            messages:{
                title:{
                    required : "请输入机会名称"
                },
                doneTime:{
                    required : "请选择完成时间"
                }
            }
        });

        //修改状态
        $(".task_checkbox").click(function () {
            var id = $(this).val();
            var checked = $(this)[0].checked;
            if(checked) {
                window.location.href = "/task/"+id+"/state/done";
            } else {
                window.location.href = "/task/"+id+"/state/undone"
            }
        });

        //删除待办任务
        $(".del_task").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗",function () {
                window.location.href = "/task/del/"+id;
            });
        });

        //修改待办任务
        $(".update_task").click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/task/update/"+id;

        });


    })
</script>
</body>
</html>
