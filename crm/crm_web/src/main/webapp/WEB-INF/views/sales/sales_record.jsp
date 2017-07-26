<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-我的客户</title>
    <%@ include file="../base/base-css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/select2/select2.min.css">
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
        <!-- 右侧内容部分 -->
        <div class="content-wrapper">

            <!-- Main content -->
            <section class="content">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">销售机会基本资料</h3>
                        <div class="box-tools">
                            <a href="/sales/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                            <button id="delBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                        </div>
                    </div>
                    <div class="box-body no-padding">
                        <table class="table">
                            <tr>
                                <td class="td_title">机会名称</td>
                                <td>${salesRecord.salesName}</td>
                                <td class="td_title">价值</td>
                                <td><fmt:formatNumber value="${salesRecord.salesValue}"/> </td>
                                <td class="td_title">当前进度</td>
                                <td>
                                    ${salesRecord.current}
                                    <button class="btn btn-xs btn-success" id="showChangeProgressModalBtn"><i class="fa fa-pencil"></i></button>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        创建日期：<fmt:formatDate value="${salesRecord.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                    </span>
                    </div>
                </div>

                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">关联客户资料</h3>
                    </div>
                    <div class="box-body no-padding">
                        <table class="table">
                            <tr>
                                <td class="td_title">姓名</td>
                                <td>${salesRecord.customer.custName}</td>
                                <td class="td_title">职位</td>
                                <td>${salesRecord.customer.jobTitle}</td>
                                <td class="td_title">联系电话</td>
                                <td>${salesRecord.customer.mobile}</td>
                            </tr>
                            <tr>
                                <td class="td_title">所属行业</td>
                                <td>${salesRecord.customer.trade}</td>
                                <td class="td_title">客户来源</td>
                                <td>${salesRecord.customer.source}</td>
                                <td class="td_title">级别</td>
                                <td class="star">${salesRecord.customer.level}</td>
                            </tr>
                            <c:if test="${not empty salesRecord.customer.address}">
                                <tr>
                                    <td class="td_title">地址</td>
                                    <td colspan="5">${salesRecord.customer.address}</td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty salesRecord.customer.mark}">
                                <tr>
                                    <td class="td_title">备注</td>
                                    <td colspan="5">${salesRecord.customer.mark}</td>
                                </tr>
                            </c:if>
                        </table>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8">
                        <h4>跟进记录
                            <small><button id="showRecordModalBtn" class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small>
                        </h4>
                        <ul class="timeline">
                            <c:if test="${empty recordSalesRecords}">
                                <li>
                                    <!-- timeline icon -->
                                    <i class="fa fa-circle-o bg-red"></i>
                                    <div class="timeline-item">
                                        <div class="timeline-body">
                                            暂无跟进记录
                                        </div>
                                    </div>
                                </li>
                            </c:if>
                            <c:forEach items="${recordSalesRecords}" var="record">
                                <c:choose>
                                    <c:when test="${record.content == '将当前进度修改为：[ 成交 ]'}">
                                        <li>
                                            <!-- timeline icon -->
                                            <i class="fa fa-check bg-green"></i>
                                            <div class="timeline-item">
                                                <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>
                                                <div class="timeline-body">
                                                        ${record.content}
                                                </div>
                                            </div>
                                        </li>
                                    </c:when>
                                    <c:when test="${record.content == '将当前进度修改为：[ 暂时搁置 ]'}">
                                        <li>
                                            <!-- timeline icon -->
                                            <i class="fa fa-close bg-red"></i>
                                            <div class="timeline-item">
                                                <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>
                                                <div class="timeline-body">
                                                        ${record.content}
                                                </div>
                                            </div>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <!-- timeline icon -->
                                            <i class="fa fa-info bg-blue"></i>
                                            <div class="timeline-item">
                                                <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>
                                                <div class="timeline-body">
                                                        ${record.content}
                                                </div>
                                            </div>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col-md-4">
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
                                            <li class="${task.done ? 'done' : ''}">
                                                <input type="checkbox" class="task_checkbox" ${task.done ? 'checked' : ''} value="${task.id}">
                                                <span class="text">${task.title}</span>
                                                <small class="label label-danger"><i class="fa fa-clock-o"></i> ${task.doneTime}</small>
                                                <div class="tools">
                                                    <i class="fa fa-edit update_task" rel="${task.id}"></i>
                                                    <i class="fa fa-trash-o del_task" rel="${task.id}"></i>
                                                </div>
                                            </li>
                                        </c:if>
                                    </c:forEach>
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

                <div class="modal fade" id="recordModal">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">添加跟进记录</h4>
                            </div>
                            <div class="modal-body">
                                <form action="/sales/new/record" id="recordForm" method="post">
                                    <input type="hidden" name="salersrecordId" value="${salesRecord.id}">
                                    <textarea id="recordContent"  class="form-control" name="content"></textarea>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <button type="button" class="btn btn-primary" id="saveRecordBtn">保存</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->

                <%--更改当前进度Modal--%>
                <div class="modal fade" id="changeProgressModal">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">更新当前进度</h4>
                            </div>
                            <div class="modal-body">
                                <form method="post" action="/sales/curren/update" id="updateProgressForm">
                                    <input type="hidden" name="id" value="${salesRecord.id}">
                                    <select name="current" class="form-control">
                                        <c:forEach items="${currents}" var="pro">
                                            <option value="${pro}">${pro}</option>
                                        </c:forEach>
                                    </select>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <button type="button" class="btn btn-primary" id="saveProgress">更新</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->

            </section>
            <!-- /.content -->
        </div>

        <%--新增待办任务MODEL--%>
        <div class="modal fade" id="taskModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">选择转入员工</h4>
                    </div>
                    <div class="modal-body">
                        <form action="/sales/newtask" id="saveTaskForm" method="post">
                            <input type="hidden" name="customerId" value="${salesRecord.customer.id}">
                            <input type="hidden" name="salesRecordId" value="${salesRecord.id}">
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
                        <button type="button" class="btn btn-primary" id="addSalesTask">确定</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

<%@include file="../base/base-js.jsp"%>
        <script src="/static/plugins/select2/select2.min.js"></script>
        <script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
        <script src="/static/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
        <script src="/static/plugins/moment/moment.js"></script>
        <script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
        <script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
        <script>
            $(function () {

                var salesId = ${salesRecord.id};
                //删除
                $("#delBtn").click(function () {
                    layer.confirm("确定要删除该机会吗?",function () {
                        window.location.href = "/sales/del/" + salesId;
                    });
                });

                //添加跟进记录
                $("#showRecordModalBtn").click(function () {
                    $("#recordModal").modal({
                        show:true,
                        backdrop:'static'
                    });
                });
                $("#saveRecordBtn").click(function () {
                    if(!$("#recordContent").val()) {
                        layer.msg("请输入跟进内容");
                        return ;
                    }
                    $("#recordForm").submit();
                });
                //更改当前进度
                $("#showChangeProgressModalBtn").click(function () {
                    $("#changeProgressModal").modal({
                        show : true,
                        backdrop:'static'
                    });
                });
                $("#saveProgress").click(function () {
                    $("#updateProgressForm").submit();
                });
            })

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

            $("#addSalesTask").click(function () {
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
        </script>
</body>
</html>
