<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>문서 조회</title>
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

<style>
	.listBtn{
		float: right;
	}

</style>

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
					
					<br><h2>상세 페이지</h2>
				
					<form role="form" method="get" autocomplete="off">
						
						<div class="card-body">
						
						<table class="table table-bordered">
							
							<tr>
								<th>
									<label for="">카테고리</label>
									<input type="text" style="width: 100%;" id="category_name" name="category_name" value="${detail.category_name}" disabled="disabled"/>
								</th>
							</tr>
							
							<tr>
								<th>
									<label for="">작성자</label>
									<input type="text" style="width: 100%;" id="user_name" name="user_name" value="${detail.user_name}" disabled="disabled" />
								</th>
							</tr>
							
							<tr>
								<th>
									<label for="">등록일자</label>
									<input type="text" style="width: 100%;" id="reg_date" name="reg_date" value="<fmt:formatDate value="${detail.reg_date}" pattern="yyyy-MM-dd HH:mm:ss"/>" disabled="disabled"/>
								</th>
							</tr>
								
							<tr>
								<th>
									<label for="">수정일자</label>
									<input type="text" style="width: 100%;" id="edit_date" name="edit_date" value="<fmt:formatDate value="${detail.edit_date}" pattern="yyyy-MM-dd HH:mm:ss"/>" disabled="disabled"/>
								</th>
							</tr>
							
							<tr>
								<th>
									<label for="">제목</label>
									<input type="text" style="width: 100%;" id="board_title" name="board_title" value="${detail.board_title}" disabled="disabled"/>
								</th>
							</tr>
							
							<tr>
								<th>
									<label for="">내용</label>
									<br><textarea rows="5" cols="80" id="board_content" name="board_content">${detail.board_content}</textarea>
								</th>
							</tr>
							
							<tr>
								<th>
									<label for="">첨부 파일</label>
									<c:forEach items="${fileList }" var="fileList">
										<a href="/common/download?file_no=${fileList.file_no }"><img src="/resources/img/attach.png" width="20px" height="20px">${fileList.fileName }</a> &nbsp;
									</c:forEach>
								</th>
							</tr>
							
						</table>
							
							<button type="button" onclick="location.href='/board/modify?board_no=${detail.board_no }'">수정</button> &nbsp;
							<button type="button" onclick="location.href='/board/delete?board_no=${detail.board_no }'">삭제</button>
							<button type="button" class="listBtn" onclick="location.href='/board/list'">목록</button>
						
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
	});
	
	// 수정 못하게 막기
	$('#board_content').summernote('disable');
	
});

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