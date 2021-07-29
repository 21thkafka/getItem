<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예외 발생</title>
</head>
<body>
<!-- 예외 출력을 위한 내용을 작성한다. : 친절하게, 회사의 이미지가 실추되지 않게 -->
	<h4>><c:out value="${exception.getMessage()}"></c:out></h4>
	<ul>
		<c:forEach items="${exception.getStackTrace()}" var="stack">
			<li><c:out value="${stack}"></c:out></li>
		</c:forEach>
	</ul>
</body>
</html>