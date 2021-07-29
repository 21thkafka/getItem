<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<link rel="stylesheet" href="../resources/my.css">

<body>
<div class="wrap">
    	<div class="blueContainer">
      		<div>
				<div class="grade">${user.userid}</div>
				<div class="name"><sec:authentication property="principal.member.username"/></div>	
				<div class="address">주소 : ${user.adress}"</div>										
			</div>
		</div>
		<div class="shippingStatusContainer">
      <div class="title">
        거래내역
      </div>
      <div class="status">
        
        <div class="item">
          <div>
          	<sec:authentication property="principal" var="pinfo"/>
          	<sec:authorize access="isAuthenticated()">
            <div class="blue number">30</div>
            <div class="text"><a href="/users/saleInfo?userid=${pinfo.member.userid}">판매내역</a></div>
            </sec:authorize>
          </div>
        </div>     
        <div class="item">
          <div>
            <div class="blue number">77</div>
            <div class="text">구매내역</div>
          </div>
        </div>       
        <div class="item">
          <div>
            <div class="blue number">3</div>
            <div class="text">관심목록</div>
          </div>
        </div>     
        
      </div>
      
    </div>  
    <div class="listContainer">
      <a href="#" class="item">
          <div class="icon">★</div>
          <div class="text">내 동네<span class="circle"></span></div>
          <div class="right"> > </div>
      </a>
      
      <a href="#" class="item">
          <div class="icon">★</div>
          <div class="text">신고하기</div>
          <div class="right"> > </div>
      </a>
      
    <div class="infoContainer">
      <a href="#" class="item">
        <div>공지사항</div>
      </a>    
      <a href="#" class="item">
        <div>이용안내</div>
      </a>    
      <a href="#" class="item">
        <div>고객센터</div>
      </a>
    </div>
  </div>
</body>
</html>