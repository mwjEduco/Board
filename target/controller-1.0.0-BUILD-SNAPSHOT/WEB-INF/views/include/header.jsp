<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
	<!-- Navbar Search-->
	<form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
		<div class="input-group" style="color: white;">
			<!-- 로그인 상태 -->
			<c:if test="${sessionScope.user != null }">
			<!-- sessionScope는 브라우저가 최초 요청을 발생시키고 브라우저를 닫을 때 까지의 영역.
			sessionScope는 session 영역에 저장되어있는 데이터나 객체를 자유롭게 사용할 수 있음. -->
					<span>${user.user_name }님</span> &nbsp;&nbsp;
					<span><a style="color: white;" href="/user/logout">로그아웃</a></span>
			</c:if>
			<!-- 로그아웃 상태 -->
			<c:if test="${user == null }">
			</c:if>
		</div>
	</form>
</nav>