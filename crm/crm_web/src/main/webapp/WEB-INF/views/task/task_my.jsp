<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../base/base-css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

   <jsp:include page="../base/base-side.jsp">
       <jsp:param name="active" value="task_my"/>
   </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">计划任务</h3>

                    <div class="box-tools pull-right">
                        <a href="/task/new" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增任务</a>
                        <c:choose>
                            <c:when test="${not (param.show == 'all')}">
                                <a href="/task/my?show=all" class="btn btn-primary btn-sm"><i class="fa fa-eye"></i> 显示所有任务</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/task/my" class="btn btn-primary btn-sm"><i class="fa fa-eye"></i> 显示未完成任务</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="box-body">

                    <ul class="todo-list">
                        <c:forEach items="${taskList}" var="task">
                            <li class="${task.done ? 'done' : ''}">
                                <input type="checkbox" class="task_checkbox" ${task.done ? 'checked' : ''} value="${task.id}">
                                <span class="text">${task.title}</span>
                                <c:choose>
                                    <c:when test="${not empty task.customer and not empty task.customer.id}">
                                        <a href="/customer/info/${task.customer.id}"><i class="fa fa-user-o"></i> ${task.customer.custName}</a>
                                    </c:when>
                                    <c:when test="${not empty task.salesRecord and not empty task.salesRecord.id}">
                                        <a href="/sales/record/${task.salesRecord.id}"><i class="fa fa-money"></i> ${task.salesRecord.salesName}</a>
                                    </c:when>
                                </c:choose>
                                <small class="label label-danger"><i class="fa fa-clock-o"></i> ${task.alertTime}</small>
                                <div class="tools">
                                    <i class="fa fa-edit update_task" rel="${task.id}"></i>
                                    <i class="fa fa-trash-o del_task" rel="${task.id}"></i>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2010 -2017 <a href="http://almsaeedstudio.com">凯盛软件</a>.</strong> All rights
        reserved.
    </footer>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<%@include file="../base/base-js.jsp"%>
<script>
    $(function () {
        //删除
        $(".del_task").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗",function () {
                window.location.href = "/task/del/"+id;
            });
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

        //修改
        $(".update_task").click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/task/update/"+id;

        });

    });
</script>
</body>
</html>
