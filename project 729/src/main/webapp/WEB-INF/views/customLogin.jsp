<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>급처득템</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../resources/style.css">
</head>
<body>
	<!-- <form role="form" method="post" action="/login"> -->
       <div class="wrap">
            <div class="form-wrap">
                <div class="title">
                    <h1>급처득템</h1>
                </div>
                <div class="button-wrap">
                    <div id="btn"></div>
                    <button type="button" class="togglebtn" onclick="login()">LOG IN</button>
                    <button type="button" class="togglebtn" onclick="register()">REGISTER</button>
                </div>
                <div class="social-icons">
                    <img src="../resources/img/fb.png" alt="facebook">
                    <img src="../resources/img/tw.png" alt="twitter">
                    <img src="../resources/img/gl.png" alt="google">
                </div>
                <form id="login" action="login" class="input-group" method="post">
                    <input type="text" name="username" class="input-field" value="${result}" placeholder="아이디를 입력해주세요." value = "${result}" required>
                    <input type="password" name="password" class="input-field" placeholder="비밀번호를 입력해주세요." value="" required>
                    <input type="checkbox" class="checkbox"><span>Remember Password</span>
                    <button class="lsubmit">Login</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
                <form id="register" action="/join" name="frm" class="input-group" method="post">
                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	                <input type="text" name="username" id="username" class="input-field" placeholder="이름(한글 또는 영문으로 입력해주세요.)" required>
	                <input type="text" name="userid" id="userid" class="input-field2" placeholder="아이디를 입력해주세요." required>
	                <button type="button" class="abtn" id="idCheck">중복확인</button>
	                <input type="password" name="password" id="password" class="input-field" placeholder="비밀번호를 입력해주세요." required>
	                <span class="final_pw_ck">비밀번호를 입력해주세요.</span>
                   <div class="adressall">
	                    <input type="text" name="adress" id="adress1" class="input-field2" placeholder="주소를 입력해주세요." readonly>
	                    <button type="button" class="abtn" id="btn_adr" onclick="execution_daum_address()">주소찾기</button>
	                    <input type="text" name="adress" id="adress2" class="input-field" readonly>
	                    <input type="text" name="adress" id="adress3" class="input-field" readonly>                   
                   </div>
                        <div class="links">
                            <a href="id">아이디 찾기</a> | <a href="pw">비밀번호 찾기</a>
                        </div>
                    <input type="checkbox" name="checkbox" class="checkbox"><span>약관 동의하기</span>
                    <a href="/clause" class="conditions">약관 보기</a>         
                    <button type="submit" class="jsubmit">REGISTER</button>
                </form>
            </div>
        </div>
        <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script>
            var x = document.getElementById("login");
            var y = document.getElementById("register");
            var z = document.getElementById("btn");            
            
            function login(){
                x.style.left = "50px";
                y.style.left = "450px";
                z.style.left = "0";
            }

            function register(){
                x.style.left = "-400px";
                y.style.left = "50px";
                z.style.left = "110px";
            }

        	   var flag = false;	// 아이디 중복체크 변수
        	   var addr = ''; // 주소 변수
               
               var formObj = $("form[role='form']");
               var csrfHeaderName="${_csrf.headerName}";
               var csrfTokenValue="${_csrf.token}";
           
            
           // Ajax spring security header
           $(document).ajaxSend(function(e, xhr, options){
        	   xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
           });
            
    
          //아이디 중복체크
           $("#idCheck").on("click", function(){
            	//e.preventDeault();
            	console.log("idCheck clicked");
            	var userid = $("#userid").val();
            	var data = {userid : userid};
           	$.ajax({
           		url : "/idCheck",
           		type : "post",
           		data : data,
           		success : function(result, status, xhr){
           			console.log(result);
           		if(result == "using"){
           			alert("중복 아이디 입니다");
           			flag = false;
           		}else {
           			alert("사용가능한 아이디입니다.");
           			flag = true;
           		}
           		},
           		error: function(xhr,status,er){
           			console.log(er);
           		}
           	});
           }); 
           
           /*다음 주소 연결*/
           function execution_daum_address(){
           	
           	new daum.Postcode({
           		oncomplete: function(data){
           		// 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
					
                    var extraAddr = ''; // 참고항목 변수
     
                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }
     
                    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                    if(data.userSelectedType === 'R'){
                        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                            extraAddr += data.bname;
                        }
                        // 건물명이 있고, 공동주택일 경우 추가한다.
                        if(data.buildingName !== '' && data.apartment === 'Y'){
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                        if(extraAddr !== ''){
                            extraAddr = ' (' + extraAddr + ')';
                        }
                        // 조합된 참고항목을 해당 필드에 넣는다.
                        addr += extraAddr;
                    
                    } else {
                        addr += '';
                    }
     
                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    $("#adress1").val(data.zonecode);
            		//$("[name=memberAddr1]").val(data.zonecode);    // 대체가능
           			 $("#adress2").val(addr);
            		//$("[name=memberAddr2]").val(addr);            // 대체가능
                    // 커서를 상세주소 필드로 이동한다.
           			$("#adress3").attr("readonly",false);
                    $("#adress3").focus();
     

           		}
           	}).open();
           	
           }
            
         
            //회원가입 이벤트+유효성검사
            $(document).ready(function(){
            	$("button[type='submit']").on("click", function(){
             	   //e.preventDefault();
            	   var name = $("#username").val();
            	   var id = $("#userid").val();
            	   var pw = $("#password").val();
            	   var adr = $("#adress1").val();
            	   var chk=document.frm.checkbox.checked;
            	   
            	   if(name == ''){
            		   alert("이름을 입력 해주세요") 
            	   } else if(id == ''){
            		   alert("아이디 입력 해주세요")
            	   } else if (flag == false) {
            		   alert("아이디 중복검사 해주세요")       
            	   } else if (pw == ''){
            		   alert("패스워드를 입력 해주세요")  
            	   } else if (addr == ''){
            		   alert("주소를 입력해 주세요")
            	   } else if (!chk){
            		   alert("약관에 동의해 주세요")
            		   return false;
            	   } else {
            		
            		$("#register").attr("action", "/join");
    	    		$("#register").submit();
            	   }
            	   return false;
            	});
            });
          
            
            //로그인 이벤트
            $(".lsubmit").on("click", function(e){
            	e.preventDefault();
            	$("#login").submit();
            });
           
        </script>
</body>
</html>