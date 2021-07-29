<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../resources/salelist.css">


<body>
<div class="board_list_wrap">
    <table class="board_list">
      <caption>핀매 목록</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>가격</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody>
		<form>
			<c:forEach items="${list}" var="board">						
				<tr>													
					<td><a href="/board/get?bno=${board.bno}"><c:out value="${board.bno}" /></a></td>
					<td><c:out value="${board.title}" /></td>
					<td><c:out value="${board.price}" /></td>
					<td><fmt:formatDate value="${board.regDate}"
									pattern="yyyy-MM-dd" /></td>
					
					<td><button id="soldout" data-bno="${board.bno}" class="btn btn-default">거래 완료</button><td>
					<!-- <td><button id="sell_done" class="btn btn-danger" disable>거래 완료</button></td> -->							
					</tr>
			</c:forEach>
			</form>	
		</tbody>
		</table>
		</div>

	<script>
   var x = document.getElementById("saling");
   var y = document.getElementById("buy");
   var z = document.getElementById("btn");
   
   
   function saling(){
       x.style.left = "50px";
       y.style.left = "450px";
       z.style.left = "0";
   }

   function buy(){
       x.style.left = "-400px";
       y.style.left = "50px";
       z.style.left = "110px";
   }
   
   var csrfHeaderName="${_csrf.headerName}";
   var csrfTokenValue="${_csrf.token}";
   
// Ajax spring security header
   $(document).ajaxSend(function(e, xhr, options){
	   xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
   });
   
   $("#soldout").on("click", function(e){
		e.preventDefault();
		var data = 
		$.ajax({
       		url : "/users/soldout",
       		type : "post",
       		data : data,
       		success : function(result, status, xhr){
       			console.log(result);
       			if(result == 1){
       				alert("판매완료하셨습니다.");
       				$("#soldout").prop("disabled",true);
       			} else {
       				
       			}
       			
       		}
       	});
   });
   </script>
</body>
</html>