<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>수정</title>
<link
	href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css"
	rel="stylesheet" />
<link href="/resources/css/styles.css" rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
	crossorigin="anonymous"></script>

<!-- JQuery 라이브러리 -->
<!-- <script src="//code.jquery.com/jquery-1.11.0.min.js"></script> -->

<!-- include libraries(jQuery, bootstrap) -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<!-- summernote-ko-KR.js -->
<script src="/resources/summernote/summernote-lite.js"></script>
<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>
<link rel="stylesheet" href="/resources/summernote/summernote-lite.css">

</head>

<body class="sb-nav-fixed">
	<!-- header -->
	<div class="header-menu">
		<%@ include file="../include/header.jsp"%>
	</div>
	
	<div id="layoutSidenav">
	
	<!-- side bar -->
	<div class="side-bar">
		<%@ include file="../include/side.jsp"%>
	</div>
		
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					
					<br><h2>수정</h2>
				
					<form role="form" method="post" autocomplete="off">
					
						<div class="card-body">
						
						<table class="table table-bordered">
						
							<tr>
								<th>
									<label>1차 분류</label>
									<select class="category1" required="required">
										<option value="">전체</option>
									</select>
									&nbsp;
									<label>2차 분류</label>
									<select class="category2" name="category_code">
										<option value="">전체</option>
									</select>
									&nbsp;
								</th>
							</tr>
							
							<tr>
								<th>
									<label for="board_title">제목</label>
									<input type="text" style="width: 100%;" id="board_title" name="board_title" value="${detail.board_title}"/>
								</th>
							</tr>
							
							<tr>
								<th>
									<label for="board_content">내용</label>
									<textarea rows="5" cols="50" id="board_content" name="board_content">${detail.board_content}</textarea>
								</th>
							</tr>
							
							<!-- 다중 파일 업로드 -->
							<tr>
								<th>
									<input type="file" id="uploadFile" name="uploadFile" multiple/>
								</th>
							</tr>
						
						</table>
						
						<button type="submit" id="write_button" onclick="moveModify();">확인</button> &nbsp;
						<button type="button" onclick="location.href='/board/detail?board_no=${detail.board_no}'">취소</button>
						
						</div>
					</form>
				</div>
			</main>
		</div>
	</div>
	
	
<script>
/* summernote 에디터 */
$(document).ready(function() {
	$('#board_content').summernote({
		minHeight : 500,
        maxHeight : null,
        lang : 'ko-KR',
        toolbar: [
		    ['fontname', ['fontname']],												// 글꼴 설정
		    ['fontsize', ['fontsize']],												// 글자 크기 설정
		    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],	// 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
		    ['color', ['forecolor','color']],										// 글자색
		    ['table', ['table']],													// 표만들기
		    ['para', ['ul', 'ol', 'paragraph']],									// 글머리 기호, 번호매기기, 문단정렬
		    ['height', ['height']],													// 줄간격
		    ['insert',['picture','link','video']],									// 그림첨부, 링크 만들기, 동영상첨부
		    ['view', ['codeview','help']]											// 코드보기, 도움말
		],
		
		fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],	// 추가한 글꼴
		fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],									// 추가한 폰트 사이즈
		
		//콜백 함수
        callbacks : { 
        	onImageUpload : function(files, editor, welEditable) {
	        // 파일 업로드(다중업로드를 위해 반복문 사용)
	        for (var i = files.length - 1; i >= 0; i--) { 
		        uploadSummernoteImageFile(files[i], this);
        		}
        	}
        }
	});
    
    function uploadSummernoteImageFile(file, el) {
		data = new FormData();
		data.append("file", file);
		
		$.ajax({
			data : data,
			type : "POST",
			url : "/board/uploadSummernoteImageFile",
			contentType : false,
			enctype : 'multipart/form-data',
			processData : false,
			success : function(data) {
				$(el).summernote('editor.insertImage', data.url);
			}
		});
	}
});

/* 확인 버튼 이벤트 */
/* function moveModify() {
	if (confirm("글을 작성하시겠습니까?") == true) {	// 확인 메시지
		document.moveModify.submit();
	} else {
		return false;
	}
} */


/* 카테고리 데이터 삽입 */
// 컨트롤러에서 데이터 받기
var jsonData = JSON.parse('${category}');
console.log(jsonData);

var cate1Arr = new Array();
var cate1Obj = new Object();

// 1차 분류 셀렉트 박스에 삽입할 데이터 준비
for (var i = 0; i < jsonData.length; i++) {
	
	if(jsonData[i].level == "1") {
		cate1Obj = new Object(); //초기화
		cate1Obj.category_code = jsonData[i].category_code
		cate1Obj.category_name = jsonData[i].category_name
		cate1Arr.push(cate1Obj);
	}
}

// 1차 분류 셀렉트 박스에 데이터 삽입
var cate1Select = $("select.category1");

console.log(cate1Select);

for (var i = 0; i < cate1Arr.length; i++) {
	cate1Select.append("<option value='" + cate1Arr[i].category_code + "'>" + cate1Arr[i].category_name + "</option>");
}

// 2차 분류
$(document).on("change", "select.category1", function(){

	var cate2Arr = new Array();
	var cate2Obj = new Object();
	
	// 2차 분류 셀렉트 박스에 삽입할 데이터 준비
	for(var i = 0; i < jsonData.length; i++) {
	 
		if(jsonData[i].level == "2") {
			cate2Obj = new Object();  //초기화
			cate2Obj.category_code = jsonData[i].category_code;
			cate2Obj.category_name = jsonData[i].category_name;
			cate2Obj.category_parent = jsonData[i].category_parent;
	  
			cate2Arr.push(cate2Obj);
	 	}
	}
	
	var cate2Select = $("select.category2");
	
	cate2Select.children().remove();
	
	$("option:selected", this).each(function(){
	 
		var selectVal = $(this).val();  
		cate2Select.append("<option value=''>전체</option>");
		 
		for(var i = 0; i < cate2Arr.length; i++) {
			if(selectVal == cate2Arr[i].category_parent) {
				cate2Select.append("<option value='" + cate2Arr[i].category_code + "'>" + cate2Arr[i].category_name + "</option>");
			}
		}
	});
});

// 수정 페이지 카테고리 표시
var select_cateCode = '${category.cate_code}';
var select_cateCodeRef = '${category.cate_parant}';
var select_cateName = '${category.cate_nama}';

if(select_cateCodeRef != null && select_cateCodeRef != '') {
	$(".category1").val(select_cateCodeRef);
	$(".category2").val(select_cateCode);
	$(".category2").children().remove();
	$(".category2").append("<option value='" + select_cateCode + "'>" + select_cateName + "</option>");
} else {
	$(".category1").val(select_cateCode);
	//$(".category2").val(select_cateCode);
	// select_cateCod가 부여되지 않는 현상이 있어서 아래 코드로 대체
	$(".category2").append("<option value="' + select_cateCode + '" selected='selected'>전체</option>");
}

</script>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
	crossorigin="anonymous"></script>
<script src="/resources/js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest"
	crossorigin="anonymous"></script>
<script src="/resources/js/datatables-simple-demo.js"></script>
</body>
</html>