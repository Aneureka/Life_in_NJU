<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/dist" var="dist" />
<spring:url value="/resources/fonts/" var="fonts" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <title>Quantour</title>
    <link rel="stylesheet" href="${dist}/index.css" />
</head>
<body>
    <div id="root"></div>

    <script src="${dist}/index.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/12.IndexPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/0.StockInfo.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/1.StrategyDetailPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/2.StrategyPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/4.IndividualPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/3.StockMarket.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/5.NewPost.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/6.PostPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/7.RegisterPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/8.Login.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/9.ForgetPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/13.CommunityPage.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/10.StrategyHistory.js"></script>
    <script type="text/javascript" charset="utf-8" async src="${dist}/11.MyStrategy.js"></script>
</body>
</html>
