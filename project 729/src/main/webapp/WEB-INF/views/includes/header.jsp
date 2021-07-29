<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>    
    
<head>
     <meta charset="UTF-8"/>
     <meta name="viewport" content="width=device-width, initial-scale=1.0" />
     <meta http-equiv="X-UA-Compatible" content="ie=edge" />
     <link rel="stylesheet" href="../resources/mainnav.css" />
     <title>급처득템 메뉴</title>
     
        <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
     
</head>

<body>
  <div class="Title">
    <h1>급처득템</h1>
  </div>
  <div class="Menu">
    <a href="/board/register">
        <img class="img-icon" id ="regBtn" src="../resources/icon/pencil.png"/>
    </a>
    <a href="/board/list">
        <img class="img-icon" src="../resources/icon/home.png"/>
    </a>
    <a href="#">
    <img class="img-icon" src="../resources/icon/talk.png"/>
    </a>
	<sec:authentication property="principal" var="pinfo"/>
		<sec:authorize access="isAuthenticated()">
			<a href="/users/userInfo?userid=${pinfo.member.userid}">
			<img class="img-icon" src="../resources/icon/user.png"/>
			</a>		
			<a href="/customLogout">
			<img class="img-icon" src="../resources/icon/logout.png"/>
			</a>			
		</sec:authorize>
		
		<sec:authorize access="isAnonymous()">
			<a href="/customLogin">
			<img class="img-icon" src="../resources/icon/login.png"/>
			</a>
			<a href="/customLogin">
			<img class="img-icon" src="../resources/icon/join.png"/>
			</a>
		</sec:authorize>
  </div>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

 