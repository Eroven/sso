<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="basePath" value="${pageContext.request.contextPath }"/>
<html>
<body>
<h2>Hello Miku!</h2>
<meta charset="UTF-8">
<form action="http://localhost:8090/route-user-c-ac/authorize/doLogin">
	<input type="text" name="account">
	<input type="text" name="password">
	<input type="submit" value="登录">
</form>
<hr>
<a href="${basePath }/index">前往登录后才能访问的<strong>Miku</strong>页面</a>
<a href="http://localhost:8090/route-user-tianyi/index">tianyi after页面</a>
<hr>
</body>
</html>
