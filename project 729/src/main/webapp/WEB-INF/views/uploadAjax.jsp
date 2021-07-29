<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax를 이용한 파일 업로드</title>
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
}
.uploadResult ul li img {
   width: 20px;
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
</head>
<body>
	<h1>upload with Ajax</h1>
	<!-- 원본 이미지를 보여주는 부분 -->
	<div class="bigPictureWrapper">
		<div class='bigPicture'>
		</div>
	</div>
	<div class='uploadDiv'><!--  첨부파일을 추가하는 부분 -->
		<input type='file' name='uploadFile' multiple>
	</div>
	<div class="uploadResult"><!-- 업로드된 첨부파일에 대한 정보를 보여주는 부분-->
		<ul>
			<!-- 첨부 파일의 정보를 list로 보여준다. -->
		</ul>
	</div>
	<button id='uploadBtn'>Upload</button>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous">
</script>
<script>
function showImage(fileCallPath) {
	// alert(fileCallPath);
	$(".bigPictureWrapper").css("display","flex").show();
	  
	$(".bigPicture")
		.html("<img src='/display?fileName="+ encodeURI(fileCallPath)+"'>")
		.animate({width:'100%', height: '100%'}, 1000);
}

$(document).ready(function(){
		var regex = new RegExp("(.*?)\(exe|sh|zip|alz)");
		var maxSize = 5242880;
		
		function checkExtension(filename, filesize){
			if(filesize > maxSize){
				alert("파일 크기 초과");
				return false;
			}
			if(regex.test(filename)){
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			return true;
		}
		
		var cloneObj = $(".uploadDiv").clone();	// 첨부파일을 추가하는 Div 요소를 복사
		$("#uploadBtn").on("click", function(e){
			var formData= new FormData();
			var inputFile= $("input[name='uploadFile']");
			var files = inputFile[0].files;
			console.log(files);
			//add filedate to formdata
			for(var i = 0; i< files.length; i++){
				if(!checkExtension(files[i],name, files[i].size)){
					return false;
				}
				formData.append("uploadFile", files[i]);
			}
			$.ajax({
				url:'/uploadAjaxAction',
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				dataType: 'json',
				success: function(result){		// result : 첨부파일 정보가 실림
					console.log(result)
					showUploadedFile(result);
					$(".uploadDiv").html(cloneObj.html());	// 다시 초기화
					// input type="file"을 다시 초기화 (이전에 첨부한 파일에 대한 정보를 제거)
					// <input type="text" name="name" value="${board.name}">
					// 파일(type="file")인 경우는 value에 다른 값을 넣는 것으로 초기화 할 수 없다.
					// 그래서 요소를 새로 만든다.
					
				}
				//,error : function(error) {alert("Upload"))}
			});//$.ajax
		});
		
		var uploadResult = $(".uploadResult ul");
		function showUploadedFile(uploadResultArr) {
			var str = "";
			$(uploadResultArr).each(function(i, obj){	// 첨부파일(obj)별로
				// str+="<li>" + obj.fileName + "</li>"; //AttachFileDTO fileName
				if(!obj.image){		// 일반 파일은 대표 아이콘을 사용해서 표시
					var fileCallPath = encodeURIComponent(obj.uploadPath +"/" + obj.uuid+"_"+
							obj.fileName);
					str += "<li><div><a href='/download?fileName="+ fileCallPath + "'>"+
					"<img src='/resources/img/attach.png'>" + obj.fileName+"</a>" + 
						"<span data-file=\'" + fileCallPath + "\' data-type='file'> x </span>"+	
						"</div></li>";
				} else {	// 이미지 파일
					// str += "<li>" + obj.fileName + "</li>";
					// 파일 이름에 한글이 있을 경우 인코딩 처리 : encodeURIComponent() 
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_"+
							obj.uuid + "_" + obj.fileName);
					var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
					originPath = originPath.replace(new RegExp(/\\/g), "/");
					str +="<li><a href=\"javascript:showImage(\'" + originPath + 
							"\')\"><img src='/display?fileName=" + fileCallPath +"'></a> " + 
							"<span data-file=\'" + fileCallPath + "\' data-type='image'> x </span>" +		
							"</li>";
				}
			});
			uploadResult.append(str);		
		}
		//첨부 이미지 파일 사라지는 효과 이벤트
		$(".bigPictureWrapper").on("click", function(e){
			$(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
			setTimeout(function() {
				$(".bigPictureWrapper").hide();
			}, 1000);
		});
		// 첨부 파일 삭제 이벤트
		$(".uploadResult").on("click","span", function(e){
		      var targetFile = $(this).data("file");
		      var type = $(this).data("type");
		      console.log(targetFile);
		        
		      $.ajax({
		          url: '/deleteFile',
		          data: {fileName: targetFile, type:type},
		          dataType:'text',
		          type: 'POST',
		            success: function(result){
		               alert(result);
		            }
		      }); //$.ajax  
		   });
		
	});
	</script>
</body>
</html>