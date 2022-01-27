<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>게시판</title>
<link
	href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css"
	rel="stylesheet" />
<link href="/resources/css/styles.css" rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
	crossorigin="anonymous"></script>

<!-- JQuery 라이브러리 -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

<style>
	.pageInfo {
		list-style: none;
		display: inline-block;
		margin: 0 0 0 20%;
		position: fixed;
	}
	
	.pageInfo li {
		float: left;
		font-size: 20px;
		margin-left: 15px;
		padding: 3px;
		font-weight: 500;
	}
	
	a:link {color: black; text-decoration: none;}
	a:visited {color: black; text-decoration: none;}
	a:hover {color: black; text-decoration: underline;}
	
	.active{
		text-decoration: underline;
		/* background-color: #cdd5ec; */
	}
	
	.search_area{
		display: inline-block;
		margin-top: 0px;
		margin-left: 260px;
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

					<br><h2>목록 조회</h2>
					
					<form role="form" action="/board/write" method="get" autocomplete="off">
											
						<div class="card-body">

							<table class="table table-hover">
								<thead>
									<tr>
										<th style="text-align: center;">번호</th>
										<th style="text-align: center;">카테고리</th>
										<th style="text-align: center;">제목</th>
										<th style="text-align: center;">최근 작성자</th>
										<th style="text-align: center;">최근 수정일</th>
									</tr>
								</thead>
								
								<tbody>
									<c:forEach items="${list }" var="list">
										<!-- board_del 칼럼이 null일 때(삭제 상태가 아닐 때) -->
										<tr>
											<td style="text-align: center;">${list.board_no}</td>
											<td style="text-align: center;">${list.category_name}</td>
											<td style="text-align: center; width: 35%; text-align: left;"  ><a href="/board/detail?board_no=${list.board_no }">${list.board_title}</a></td>
											<td style="text-align: center;">${list.user_name}</td>
											<td style="text-align: center;"><fmt:formatDate value="${list.edit_date}" pattern="yyyy-MM-dd"/></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<button type="submit" id="write_button">등록</button>
							
						</div>
					</form>
					
					<div class="pageInfo_wrap" style="padding-left: 10%">
						<div class="pageInfo_area">
							<ul id="pageInfo" class="pageInfo">
								
								<!-- 처음 페이지 버튼 -->
								<c:if test="${page.pageNum != 1}">
									<li class=""><a href="/board/list?pageNum=1">[처음]</a></li>
								</c:if>
								
								<!-- 이전 페이지 버튼 -->
								<c:if test="${page.prev }">
									<li class="pagePrev_btn"><a href="/board/list?pageNum=${page.startPage-1 }">[이전]</a></li>
								</c:if>
								
								<!-- 각 페이지 번호 버튼 -->
								<c:forEach begin="${page.startPage }" end="${page.endPage }" var="num">
									<li class="pageInfo_btn ${page.pvo.pageNum == num ? "active":"" }"><a href="/board/list?pageNum=${num }&amount=10">${num }</a></li>
								</c:forEach>
								
								<!-- 다음 페이지 버튼 -->
								<c:if test="${page.next }">
									<li class="pageNext_btn"><a href="/board/list?pageNum=${page.endPage+1 }">[다음]</a></li>
								</c:if>
								
								<!-- 마지막 페이지 버튼 -->
								<%-- <c:if test="${page.pageNum != 1}">
									<li class=""><a href="/board/list?pageNum=${page.totalCnt }">[마지막]</a></li>
								</c:if> --%>
								
							</ul>
						</div>
					</div>
					
					<div class="search_wrap">
						<div class="search_area">
							<form id="searchForm" action="/board/list" method="get">
								<div class="search_input">
									<input type="text" name="keyword">
									<button class="search_btn">검색</button>
								</div>
							</form>
						</div>
					</div>
					
					<form id="moveForm" method="get">
						<input type="hidden" name="pageNum" value="${page.pvo.pageNum }">
						<input type="hidden" name="amount" value="${page.pvo.amount }">
						<input type="hidden" name="keyword" value="${page.pvo.keyword }">
					</form>
										
				</div>
			</main>
		</div>
	</div>
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="/resources/js/scripts.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest"
		crossorigin="anonymous"></script>
	<script src="/resources/js/datatables-simple-demo.js"></script>
</body>
</html>