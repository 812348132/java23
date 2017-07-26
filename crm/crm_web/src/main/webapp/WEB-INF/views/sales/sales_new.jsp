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

    <%@include file="../base/base-side.jsp"%>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">新增销售机会</h3>
                    <div class="box-tools pull-right">
                        <a href="/sales/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="newSalesForm" method="post">
                        <div class="form-group">
                            <label>机会名称</label>
                            <input type="text" class="form-control" name="salesName">
                        </div>
                        <div class="form-group">
                            <label>关联客户</label>
                            <select name="customerId" id="" class="form-control">
                                <c:choose>
                                    <c:when test="${customer != null}">
                                        <option value="${customer.id}">${customer.custName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value=""></option>
                                        <c:forEach items="${customerList}" var="customer">
                                            <option value="${customer.id}">${customer.custName}</option>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>机会价值</label>
                            <input type="text" class="form-control" name="salesValue">
                        </div>
                        <div class="form-group">
                            <label>当前进度</label>
                            <select name="current" class="form-control">
                                <option value=""></option>
                                <c:forEach items="${currents}" var="current">
                                    <option value="${current}">${current}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>详细内容</label>
                            <textarea name="content" class="form-control"></textarea>
                        </div>
                    </form>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button id="newSalesBtn" class="btn btn-primary">保存</button>
                </div>
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

        $("#newSalesBtn").click(function () {
            $("#newSalesForm").submit();
        });

        $("#newSalesForm").validate({
            errorClass : "text-danger",
            errorElement:"span",
            rules:{
                salesName:{
                    required:true
                },
                customer:{
                    required:true
                },
                salesValue:{
                    required:true
                },
                current:{
                    required:true
                }
            },
            messages:{
                salesName:{
                    required:"不能为空"
                },
                customer:{
                    required:"不能为空"
                },
                salesValue:{
                    required:"不能为空"
                },
                current:{
                    required:"不能为空"
                }
            }
        })



    });
</script>
</body>
</html>
