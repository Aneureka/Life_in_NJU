<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cs" uri="/WEB-INF/tlds/checkSession.tld" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored ="false" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>订单列表</title>
</head>
<body>
<%--验证用户状态--%>
<cs:checkSession/>
<h1>客户订单列表</h1>
<%--客户订单列表表格--%>
<table>
    <tr>
        <th>订单号</th>
        <th>客户姓名</th>
        <th>商品名称</th>
        <th>数量</th>
        <th>总价</th>
        <th>创建时间</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.id}"/></td>
            <td><c:out value="${order.customerName}"/></td>
            <td><c:out value="${order.productName}"/></td>
            <td><c:out value="${order.quantity > 0 ? order.quantity : '缺货'}"/></td>
            <td><c:out value="${order.totalPrice}"/></td>
            <td><c:out value="${order.createdAt}"/></td>
        </tr>
    </c:forEach>
</table>

<%--分页--%>
<table>
    <tbody>
    <tr valign='top'>
        <c:if test="${pageNum > 1 && maxPageNum > 1}">
            <td><a href="<%=response.encodeURL(request.getContextPath()+"/orderList?pageNum=")%>${pageNum-1}">上一页</a></td>
        </c:if>
        <c:forEach var="index" begin="${start}" end="${end}">
            <td><a href="<%=response.encodeURL(request.getContextPath()+"/orderList?pageNum=")%>${index}">${index}</a></td>
        </c:forEach>
        <c:if test="${pageNum < maxPageNum && maxPageNum > 0}">
            <td><a href="<%=response.encodeURL(request.getContextPath()+"/orderList?pageNum=")%>${pageNum+1}">下一页</a></td>
        </c:if>
    </tr>
    </tbody>
</table>

<%--登出按钮--%>
<form method="GET" action="<%=response.encodeURL(request.getContextPath() + "/login")%>">
    <input type='submit' name='logout' value='logout' />
</form>

<%--在线人数统计--%>
<p>
    在线总人数：${userCount}
    已登录人数：${loginUserCount}
    游客人数：${userCount-loginUserCount}
</p>

</body>
</html>
