<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="basePath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>miku after</title>
</head>
<body>
<h2>Hello&nbsp;${user }</h2>&nbsp;<a href="${basePath }/logout">退出</a><br>
<a href="http://localhost:8090/route-user-miku/toLogin">miku登录页面</a>
<a href="http://localhost:8090/route-user-miku/index">miku after页面</a>
</body>
</html>
