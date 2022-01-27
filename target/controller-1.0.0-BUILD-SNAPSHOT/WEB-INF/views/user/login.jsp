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
<meta name="description" content="" />
<meta name="author" content="" />
<title>로그인</title>
<link href="/resources/css/styles.css" rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
	crossorigin="anonymous"></script>

<!-- JQuery 라이브러리 -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

</head>

<body class="bg-primary">
	<div id="layoutAuthentication">
		<div id="layoutAuthentication_content">
			<main>
				<div class="container">
					<div class="row justify-content-center">
						<div class="col-lg-5">
							<div class="card shadow-lg border-0 rounded-lg mt-5">
								<div class="card-header">
									<h3 class="text-center font-weight-light my-4">로그인</h3>
								</div>

								<div class="card-body">

									<form action="" id="login_form" role="form" method="post">

										<div class="form-floating mb-3">
											<input class="form-control" id="user_id" name="user_id"	type="email" placeholder="아이디" required="required"/>
											<label for="">아이디</label>
										</div>
										<div class="form-floating mb-3">
											<input class="form-control" id="user_pw" name="user_pw"	type="password" placeholder="비밀번호" required="required"/>
											<label for="">비밀번호</label>
										</div>
										
										<!-- 
										<span class="">
											<input class="form-check-input" id="inputRememberId" type="checkbox" value="" />
											<label class="form-check-label" for="inputRememberId">아이디 저장</label>
										</span>
										 -->
										
										<!-- 
										<span class="" style="float: right">
											<input class="form-check-input" id="inputRememberLogin" type="checkbox" value="" />
											<label class="form-check-label" for="inputRememberLogin">로그인 유지</label>
										</span>
										 -->
										
										<c:if test="${result == 0 }">
											<div class="login_warn" style="color: red; font-size: 12px; text-align: center;">아이디 또는 비밀번호가 일치하지 않습니다.</div>
										</c:if>
										
										<div class="d-flex align-items-center justify-content-between mt-4 mb-0">
											<!-- <a class="small" href="password.html">비밀번호 찾기</a> -->
											<a class="btn btn-primary" id="login_button">로그인</a>
										</div>
									</form>

								</div>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
	
<script>
/* 로그인 버튼 테스트 */
$("#login_button").click(function(){
	
	// 로그인 메서드 서버 요청
	$("#login_form").attr("action", "/user/login");
	$("#login_form").submit();
	
});

/* 엔터키로 로그인 동작 */
$("#login_form").keyup(function(e){
	
	if(e.keyCode == 13) {
		$("#login_form").attr("action", "/user/login");
		$("#login_form").submit();
	}
});

</script>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
	crossorigin="anonymous"></script>
<script src="/resources/js/scripts.js"></script>
</body>
</html>
