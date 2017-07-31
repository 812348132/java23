<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-首页</title>
    <%@ include file="../base/base-css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="charts_static"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">柱状图</h3>
                </div>
                <div class="box-body">
                    <div id="bar" style="height: 300px;width: 100%"></div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">饼图</h3>
                        </div>
                        <div class="box-body">
                            <div id="pie" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">折线图</h3>
                        </div>
                        <div class="box-body">
                            <div id="line" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">多数据柱状图</h3>
                        </div>
                        <div class="box-body">
                            <div id="bar2" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">折线图 + 柱状图</h3>
                        </div>
                        <div class="box-body">
                            <div id="lineBar" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->


</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/echarts/echarts.common.min.js"></script>
<script>
   $(function () {

       var bar = echarts.init($("#bar")[0]);
       var pie = echarts.init($("#pie")[0]);
       var line = echarts.init($("#line")[0]);
       var bar2 = echarts.init($("#bar2")[0]);
       var lineBar = echarts.init($("#lineBar")[0]);

       bar.setOption({
           title : {
               text:"2017年手机销量图",
               left : "center"
           },
           tooltip:{},
           legend:{
               data:["销量"],
               left:'right'
           },
           xAxis : {
               data: ['OPPO', "VIVO", "三星", "小米", "华为", "苹果"]
           },
           yAxis:{},
           series:{
               data:[1000,300,699,1231,412,655],
               type:"bar",
               name:"销量"
           }
       });

       pie.setOption({
         title:{
             text:"网站访问来源分布"
         },
           series:{
             data:[
                 {value:335, name:'直接访问'},
                 {value:310, name:'邮件营销'},
                 {value:274, name:'联盟广告'},
                 {value:235, name:'视频广告'},
                 {value:800, name:'搜索引擎'}
             ],
             type:"pie"
           }
       });

       line.setOption({
           title : {
               text:"2017年手机销量图",
               left : "center"
           },
           tooltip:{},
           legend:{
               data:["销量"],
               left:'right'
           },
           xAxis : {
               data: ['OPPO', "VIVO", "三星", "小米", "华为", "苹果"]
           },
           yAxis:{},
           series:{
               data:[1000,300,699,1231,412,655],
               type:"line",
               name:"销量"
           }
       });

       bar2.setOption({
           title:{
               text: "2017上半年手机销售量统计图",
           },
           tooltip:{},
           legend:{
               data:["三星","苹果","Vivo"],
               left:"right"
           },
           xAxis:{
               data:['1月', "2月", "3月", "4月", "5月", "6月"]
           },
           yAxis:{},
           series:[
               {
                   name:"苹果",
                   type:"bar",
                   data: [645, 950, 734, 800, 253, 713]
               },
               {
                   name:"三星",
                   type:"bar",
                   data: [907, 574, 456,987, 765, 312]
               },
               {
                   name:"Vivo",
                   type:"bar",
                   data: [300, 650, 100, 600, 750, 900]
               }
           ]
       });

       lineBar.setOption({
           title:{
               text: "2017上半年手机销售量统计图",
           },
           tooltip:{},
           legend:{
               data:["三星","苹果","Vivo"],
               left:"right"
           },
           xAxis:{
               data:['1月', "2月", "3月", "4月", "5月", "6月"]
           },
           yAxis:{},
           series:[
               {
                   name:"苹果",
                   type:"bar",
                   data: [645, 950, 734, 800, 253, 713]
               },
               {
                   name:"三星",
                   type:"line",
                   data: [907, 574, 456,987, 765, 312]
               },
               {
                   name:"Vivo",
                   type:"bar",
                   data: [300, 650, 100, 600, 750, 900]
               }
           ]
       });
   });
</script>
</body>
</html>
