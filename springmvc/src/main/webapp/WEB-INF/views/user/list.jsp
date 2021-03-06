<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
</head>
<body>
    <h1>hello : ${name} </h1>

    <c:if test="${not empty message}">
        <h3>${message}</h3>
    </c:if>

    <form action="/user/save" method="post">
        <input type="text" name="userName">
        <input type="text" name="address">
        <input type="text" name="score">
        <button>提交</button>
    </form>


    <h3>文件上传</h3>
    <form action="/user/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="docName">
        <button>上传</button>
    </form>

    <img src="/static/img/1.jpg" alt="">
</body>
</html>