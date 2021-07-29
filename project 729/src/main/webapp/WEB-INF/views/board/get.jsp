<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../includes/header.jsp"%>
<!--  댓글에 대한 작업은 get.jsp에서 모두 이루어진다.
	댓글의추가, 삭제, 수정, 읽기, 목록보기 -> javascript에서 Ajax를 이용해서 서버와 통신을 통해서 이루어진다.
 -->
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">급처 득템</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">판매 물품 페이지</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="form-group">
					<label>Bno</label> <input class="form-control" name='bno'
						value='<c:out value="${board.bno}"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>Title</label> <input class="form-control" name='title'
						value='<c:out value="${board.title }"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>물품 설명</label>
					<textarea class="form-control" rows="3" name='content'
						readonly="readonly"><c:out value="${board.content}" /></textarea>
				</div>
				<div class="form-group">
					<label>작성자</label> <input class="form-control" name='userid'
						value='<c:out value="${board.userid }"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>가격</label> <input class="form-control" name='title'
						value='<c:out value="${board.price }"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>판매 기간</label> <input class="form-control" name='title'
						value='<c:out value="${board.saledate}"/>' readonly="readonly">
				</div>
				<%-- <button data-oper='modify' class="btn btn-default"
					onclick="location.href='/board/modify?bno=<c:out value="${board.bno}" />'">Modify</button>
				<button data-oper='list' class="btn btn-info"
					onclick="location.href='/board/list'">List</button>--%>

				<sec:authentication property="principal" var="pinfo"/>
				<sec:authorize access="isAuthenticated()">
				<c:if test="${pinfo.username eq board.userid}">
					<button data-oper='modify' class="btn btn-default">수정</button>
				</c:if>
				</sec:authorize>
				<%--submit 버튼으로 동작 --%>
				<button data-oper='list' class="btn btn-info">목록</button>
				<button data-oper='chat' class="btn btn-info">메세지 보내기</button>
				<button data-oper='like' class="btn btn-info">관심목록</button>
				<form id='operForm' action="/board/modify" method="get">
					<input type='hidden' id='bno' name='bno'
						value='<c:out value="${board.bno}"/>'>
					<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
 					<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
 					<input type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
					<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'> 
				</form>
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<!-- 첨부파일 -->
<div class='bigPictureWrapper'>
	<div class='bigPicture'>
	</div>
</div>

<style>
.uploadResult {
   width: 100%;
   background-color: gray;
}
.uploadResult ul {
   display: flex;
   flex-flow: row;
   justify-content: center;
   align-items: center;
}
.uploadResult ul li {
   list-style: none;
   padding: 10px;
   align-content: center;
   text-align: center;
}
.uploadResult ul li img {
   width: 100px;
}
.uploadResult ul li span{
  color:white;
}
.bigPictureWrapper {
  position: absolute;
  display: none;
  justify-content: center;
  align-items: center;
  top:0%;
  width:100%;
  height:100%;
  background-color: gray; 
  z-index: 100;
  background:rgba(255,255,255,0.5);
}
.bigPicture {
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}
.bigPicture img {
  width: 600px; 
}
</style>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">첨부 파일</div>
			<div class="panel-body">
				<div class='uploadResult'>
					<ul>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
		
	
	
<!-- 댓글 목록을 출력하는 부분 -->
	<div class='row'>
 		<div class="col-lg-12">
 		<!-- /.panel -->
 			<div class="panel panel-default">
 			<div class="panel-heading">
 		<i class="fa fa-comments fa-fw"></i> Reply
 		<!-- <button id='addReplyBtn' class = 'btn btn-primary btn-xs pull-right'> 댓글 달기</button> -->
 		<sec:authorize access="isAuthenticated()">
 		<button id='addReplyBtn' class = 'btn btn-primary btn-xs pull-right'> 댓글 달기</button>
 		</sec:authorize>
 	</div>
 
 <!-- /.panel-heading -->
 	<div class="panel-body">
 		<ul class="chat">
 		<!-- start reply -->
 			<li class="left clearfix" data-rno='12'><!-- 댓글 -->
 			<div>
 			<div class="header">
 				<strong class="primary-font">user00</strong>
 				<small class="pull-right text-muted">2018-01-01 13:13</small>
 			</div>
 				<p>Good job!</p>
 			</div>
		 </li>
 		<!-- end reply -->
 	</ul>
 <!-- ./ end ul -->
 </div>
 <!-- /.panel .chat-panel -->
 <div class="panel-footer"></div>
</div>
 </div>
 <!-- ./ end row -->
</div>
<!--  모달창을 추가하는 부분 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
  aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
          aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <label>Reply</label> 
          <input class="form-control" name='reply' value='New Reply!!!!'>
        </div>      
        <div class="form-group">
          <label>Replyer</label> 
          <input class="form-control" name='replyer' value='replyer'>
        </div>
        <div class="form-group">
        <label>Reply Date</label> 
          <input class="form-control" name='replyDate' value='2018-01-01 13:13'>
        </div>
      </div>
      <div class="modal-footer">
        <button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
        <button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
        <button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
        <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript" src="/resources/js/reply.js"></script>
<script type="text/javascript">
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

$(document).ready(function() {
	var pageNum = 1;
	var operForm = $("#operForm");
$("button[data-oper='modify']").on("click", function(e) {
	operForm.attr("action", "/board/modify").submit();
});

		$("button[data-oper='list']").on("click", function(e) {
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list")
			operForm.submit();
		});		
		console.log("Reply Module...");
//		console.log(replyService);		// reply.js에 정의된 객체
//		replyService.add(10, 20);		// replyService 객체의 함수 add를 호출
		//var replyService = { };

 	var bnoValue = '<c:out value="${board.bno}" />';
 	var replyUL = $(".chat");	// ul  char 변수
 
 	
 	showList(1);
 	
 	function showList(page) {   // 댓글 페이지를 보여주는 함수
 	   console.log("show list " + page);
 	   replyService.getList({bno:bnoValue, page: page|| 1}, function(replyCnt, list) {
 	      console.log("list: " + list);
 	      console.log("replyCnt: " + replyCnt);
 	      
 	      if(page == -1){		// -1 page -> 마지막 페이지를 계산해서 마지막 페이지를 요청
 	    	  pageNum = Math.ceil(replyCnt / 10.0);		// ceil(올림) : 마지막 페이지
 	    	  showList(pageNum);						// 마지막 페이지를 보여주도록 요청
 	    	  return;
 	      }
 	      
 	      var str = "";
 	      if(list == null || list.length == 0) {
 	         replyUL.html("");
 	         return;
 	      }
 	      for(var i = 0, len = list.length || 0;i < len;i++) {
 	         str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
 	         str +="  <div><div class='header'><strong class='primary-font'>["
 	            + list[i].rno+"] "+list[i].replyer+"</strong>"; 
 	          str +="    <small class='pull-right text-muted'>" 
 	          + replyService.displayTime(list[i].replyDate) + "</small></div>";
 	          str +="    <p>"+list[i].reply+"</p></div></li>";
 	      }
 	      replyUL.html(str);			// 댓글 목록을 보여주기
 	      // 페이징 처리하는 부분을 call
 	      showReplyPage(replyCnt);		// 페이징 처리하는 부분을 출력
 	   });
 	  }
 	// 모달 창 처리
 	var modal = $(".modal");
 	  var modalInputReply = modal.find("input[name='reply']");
 	  var modalInputReplyer = modal.find("input[name='replyer']");
 	  var modalInputReplyDate = modal.find("input[name='replyDate']");
 	  
 	  var modalModBtn = $("#modalModBtn");
 	  var modalRemoveBtn = $("#modalRemoveBtn");
 	  var modalRegisterBtn = $("#modalRegisterBtn");
 	  var replyer = null;
 	  <sec:authorize access="isAuthenticated()">
 	  	replyer ='<sec:authentication property="principal.username"/>';
 	  </sec:authorize>
 	  
 	  var csrfHeaderName = "${_csrf.headerName}";
	  var csrfTokenValue = "${_csrf.token}";
 	  
 	  var modalCloseBtn = $("#modalCloseBtn");
 	 
 	 modalCloseBtn.on("click", function(){
 		// close 버튼이 눌리면 실행된다. -> 모달창을 닫는다.
 		$(".modal").modal("hide");
 		
 	 });
 	

 	  $("#addReplyBtn").on("click", function(e){
 	    modal.find("input").val("");
 	    modal.find("input[name='replyer']").val(replyer);
 	    modalInputReplyDate.closest("div").hide();
 	    modal.find("button[id !='modalCloseBtn']").hide();
 	    modalRegisterBtn.show();
 	    $(".modal").modal("show");
 	  });
 	  
 	  //Ajax spring security header...
 	  $(document).ajaxSend(function(e, xhr, options){
 		  xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
 	  });
 	  
 	 modalRegisterBtn.on("click",function(e){	// 댓글 추가 버튼
 		var reply = {		//  ReplyVO
 		          reply: modalInputReply.val(),
 		          replyer:modalInputReplyer.val(),
 		          bno:bnoValue
 		      };
 		      replyService.add(reply, function(result){
 		          alert(result);
 		          modal.find("input").val("");
 		          modal.modal("hide");
 		          
 		          //showList(1);        // 댓글 목록이 갱신되도록 한다.
 		          showList(-1);        // 댓글 목록이 갱신되도록 한다.
 		      });
 		  });
 	//댓글 조회 클릭 이벤트 처리 

 	 $(".chat").on("click", "li", function(e){
 		 var rno = $(this).data("rno");
 	 console.log(rno);
 	replyService.get(rno, function(reply){
 	      modalInputReply.val(reply.reply);
 	      modalInputReplyer.val(reply.replyer);
 	      modalInputReplyDate.val(replyService.displayTime( reply.replyDate))
 	      .attr("readonly","readonly");
 	      modal.data("rno", reply.rno);
 	      
 	      modal.find("button[id !='modalCloseBtn']").hide();
 	      modalModBtn.show();
 	      modalRemoveBtn.show();
 	      
 	      $(".modal").modal("show");        
 	    });
 	  });
 	//모달창에서 댓글 이벤트 수정
 	modalModBtn.on("click", function(e){
 		var originalReplyer = modalInputReplyer.val();
 		var reply = {rno:modal.data("rno"), reply: modalInputReply.val(),
 				replyer: originalReplyer};
 		if(!replyer){
 			alert("로그인후 수정이 가능합니다.");
 			modal.modal("hide");
 			return;
 		}
 		console.log("Original Replyer: " + originalReplyer);
 		if(replyer != originalReplyer){
 			alert("자신이 작성한 댓글만 수정이 가능합니다.");
 			modal.modal("hide");
 			return;
 		}
 		replyService.update(reply, function(result){
 			alert(result);
 			modal.modal("hide");
 			//showList(1);
 			showList(pageNum);	// 현재 보여주고 있는 페이지로 이동
 		});
 	});
 	//모달 창에서 댓글 삭제 버튼에 이벤트를 등록
 	modalRemoveBtn.on("click", function(e){
 		var rno = modal.data("rno");
		console.log("RNO: " + rno);
		console.log("REPLYER:" + replyer);
		if(!replyer){
			alert("로그인후 삭제가 가능합니다.");
			modal.modal("hide");
			return;
		}
		var originalReplyer= modalInputReplyer.val();
		console.log("Original Replyer: " + originalReplyer);
		if(replyer != originalReplyer){
			alert("자신이 작성한 댓글만 삭제가 가능합니다.");
			modal.modal("hide");
			return;
		}
 		replyService.remove(rno, originalReplyer, function(result){
 			alert(result);
 			modal.modal("hide");
 			//showList(1);
 			showList(pageNum);
 		});
 	});
	
 	
 	var replyPageFooter = $(".panel-footer");
 	
 	function showReplyPage(replyCnt){
 	      var endNum = Math.ceil(pageNum / 10.0) * 10;  
 	      var startNum = endNum - 9;       
 	      var prev = startNum != 1;
 	      var next = false;
 	      
 	      if(endNum * 10 >= replyCnt){
 	        endNum = Math.ceil(replyCnt/10.0);
 	      }
 	      if(endNum * 10 < replyCnt){
 	        next = true;
 	      }
 	      var str = "<ul class='pagination pull-right'>";
 	      if(prev){
 	        str+= "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
 	      }
 	      for(var i = startNum ; i <= endNum; i++){
 	        var active = pageNum == i? "active":"";
 	        str+= "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
 	      }
 	      if(next){
 	        str+= "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
 	      }
 	      str += "</ul></div>";
 	      console.log(str);
 	      replyPageFooter.html(str);
 	  }
 	
 	replyPageFooter.on("click", "li a", function(e){
 		e.preventDefault();
	 	console.log("page click");
	 	var targetPageNum = $(this).attr("href");
	    console.log("targetPageNum: " + targetPageNum);
	    pageNum = targetPageNum;
	    showList(pageNum);		// 해당 페이지로 이동
 });
 	
 	(function(){	// 자동 실행함수 : get, jsp가 로드된 후 자동으로 실행된다.
 		var bno = '<c:out value="${board.bno}"/>';
 		
 		$.getJSON("/board/getAttachList", {bno:bno}, function(arr){
 			console.log(arr);
 			
 			//첨부파일 보여주기 이벤트 			
 		 		var str="";
 		 		$(arr).each(function(i, attach){
 		 			//image type
 		 			if(attach.fileType){
 		 				var fileCallPath = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid+"_"+attach.fileName);
 		 				str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"'data-type='"+attach.fileType+"'><div>";
 		 				str += "<img src='/display?fileName="+ fileCallPath +"'>";
 		 				str += "</div>"
 		 				str + "</li>";
 		 			} else {
 		 				str += "<li data-path='"+attach.uploadPath+"'data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"'data-type='"+attach.fileType+"'><div>";
 						str += "<span>" + attach.fileName+"</span>"; 					
 						str += "<img src='/resources/img/attach.png'></a>";
 						str += "</div>";
 						str + "</li>";
 		 			}
 		 		});
 		 		$(".uploadResult ul").html(str);
 		}); //end getjson
 	})();
 	$(".uploadResult").on("click","li", function(e){
 		console.log("view image");
 		var liObj = $(this);
 		var path = encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid")+"_" + liObj.data("filename"));
 		if(liObj.data("type")){
 		  showImage(path.replace(new RegExp(/\\/g),"/"));
 		}else {
 		  //download 
 		  self.location ="/download?fileName="+path;
 		}
 	  });
 	  
 	  function showImage(fileCallPath){
 	    alert(fileCallPath);
 		$(".bigPictureWrapper").css("display","flex").show();
 		$(".bigPicture")
 		  .html("<img src='/display?fileName="+fileCallPath+"' >")
 		  .animate({width:'100%', height: '100%'}, 1000);
 	  }

}); 

</script>

<%@ include file="../includes/footer.jsp"%>