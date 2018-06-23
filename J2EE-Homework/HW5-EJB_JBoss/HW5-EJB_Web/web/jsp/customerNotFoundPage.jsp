<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>客户不存在</title>
</head>
<body>
    <h1>用户名或密码错误！</h1>
    <p>请仔细确认您的用户名及密码</p>

    <%--重新登录按钮--%>
    <form method="GET" action="<%=response.encodeURL(request.getContextPath() + "/login")%>">
        <input type='submit' value="重新登录" />
    </form>

</body>
</html>
