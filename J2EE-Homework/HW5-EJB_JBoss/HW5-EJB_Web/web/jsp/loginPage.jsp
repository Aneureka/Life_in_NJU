<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page isELIgnored ="false" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>登录页面</title>
</head>
<body>
    <h1>客户登录</h1>
    <form action="<%=response.encodeURL(request.getContextPath()+"/login")%>" method="post">
        账号：<input type="text" name="id" value="<%=request.getAttribute("cookieLogin")%>" /><br/>
        密码：<input type="password" name="password" /><br/>
        <c:if test="${loginError == true}">
            <p style="color: red;">账号或密码错误！</p><br>
        </c:if>
        <input type="submit" value="登录" />
    </form>
    <hr/>

    <%--在线人数统计--%>
    <p>
        在线总人数：${userCount}
        已登录人数：${loginUserCount}
        游客人数：${userCount-loginUserCount}
    </p>

</body>
</html>
