<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>/security/all page</h1>
<!-- 로그인 여부에 따라 메뉴를 다르게 보여준다.
로그인 되어 있으면 로그아웃 메뉴를 보여주고 
로그인 되어 있지 않으면 로그인 메뉴를 보여준다.-->

<sec:authorize access="isAnonymous()"><!-- 로그인하지 않은 경우 -->
<a href="/customLogin">로그인</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()"><!-- 인증(로그인)되었다면 -->
<a href="/customLogout">로그아웃</a>
</sec:authorize>

</body>
</html>