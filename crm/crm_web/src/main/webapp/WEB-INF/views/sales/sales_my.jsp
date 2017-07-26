<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../base/base-css.jsp"%>
    <style>
        .table>tbody>tr:hover {
            cursor: pointer;
            background: #cccccc;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="sales_my"></jsp:param>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">搜索</h3>
                    </div>
                    <div class="box-body">
                        <form class="form-inline">
                            <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="名称 或 机会价值">
                            <button class="btn btn-default"><i class="fa fa-search"></i></button>
                        </form>
                    </div>
                </div>

                <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的销售机会</h3>

                    <div class="box-tools pull-right">
                        <a href="/sales/new" class="btn btn-box-tool">
                            <i class="fa fa-plus"></i> 添加机会
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-info">${message}</div>
                    </c:if>
                    <table class="table">
                        <thead>
                        <tr>
                            <td>机会名称</td>
                            <td>关联客户</td>
                            <td>机会价值</td>
                            <td>当前进度</td>
                            <td>最后跟进时间</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="sales">
                            <tr class="record" rel="${sales.id}">
                                <td>${sales.salesName}</td>
                                <td>${sales.customer.custName}</td>
                                <td><fmt:formatNumber value="${sales.salesValue}"/></td>
                                <td>${sales.current}</td>
                                <td><fmt:formatDate value="${sales.followTime}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
                <c:if test="${pageInfo.pages > 1}" >
                    <div class="box-footer">
                        <ul id="pagination-demo" class="pagination-sm pull-right"></ul>
                    </div>
                </c:if>
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

<%@include file="../base/base-js.jsp"%>
<script>
    $(function () {
        <c:if test="${pageInfo.pages > 1}" >
        //分页
        $('#pagination-demo').twbsPagination({
            totalPages: ${pageInfo.pages},
            visiblePages: 7,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?p={{number}}&keyword=${keyword}"
        });
        </c:if>

        $(".record").click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/sales/record/"+id;
        });
    });
</script>
</body>
</html>
