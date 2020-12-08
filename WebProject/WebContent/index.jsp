<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.UserDAO" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>이색 여행지에 대한 모든것!</title>
	 
	<!-- 부트스트랩 CSS 추가하기 -->	
	<link rel="stylesheet" href="./css/bootstrap.min.css">

    <!-- 커스텀 CSS 추가하기 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>
<body>
<%
	String userID = null;
	if(session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	
%>

	<nav class="navbar navbar-expand-lg navbar-light bg-dark">
		<a class="navbar-brand text-white" href="index.jsp">이색 여행지에 대한 모든것!</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link text-white" href="index.jsp">메인</a>
				</li>
				
				<li class="nav-item active">
					<a class="nav-link text-white" href="bbs.jsp">게시판</a>
				</li>
				
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle text-white" id="dropdown" data-toggle="dropdown">
						회원관리
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
					<%
						if(userID == null) {
					%>
						<a class="dropdown-item" href="userLogin.jsp">로그인</a>
						<a class="dropdown-item" href="userJoin.jsp">회원가입</a>
					<%
					} else{
					%>
						<a class="dropdown-item" href="userLogoutAction.jsp">로그아웃</a>
					<%
					}
					%>
					</div>
				</li>
			</ul>
			<form action="./bbsSearch.jsp" method="get" class="form-inline my-2 my-lg-0">
				<input type="text" name="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="search">
				<button class="btn btn-primary text-white my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>
	
	<div class="container">
		<div class="jumbotron">
			<div class="container">
				<h1>웹 사이트 소개</h1>
				<p> 본 웹사이트는 다양한 회원들이 자신이 다녀본 이색적인 여행지를 직접 추천합니다. 
				이 사이트를 통해 친구, 가족 혹은 연인과 함께 할 수 있는 이색적인 여행지에 대해 알아보세요!
				무엇보다도 회원님들의 직접적인 경험 통한 여행지 추천이기 때문에 신뢰할 수 있습니다!
				 회원관리 탭을 통해 간단한 회원가입후 게시판을 통해 다양한 후기들을 지금 만나 보세요.</p>
				<p><a class="btn btn-primary btn-pull" href="./userJoin.jsp" role="button">회원가입 하기</a></p>
			</div>
		</div>
		
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="images/1.jpg" class="d-block w-100" height="500px">
				</div>
				<div class="carousel-item">
					<img src="images/2.jpg" class="d-block w-100" height="500px">
				</div>
				<div class="carousel-item">
					<img src="images/3.jpg" class="d-block w-100" height="500px">
				</div>
			</div>
			<a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	
		<div class="container" style="margin-top: 50px">
			<div class="row">
				<div class="col">
					<h4>1번 그림</h4>
					<p>섬속의 아름다움, 경상남도 거제도의 외딴섬 외도 보타니아!</p>
				</div>
				<div class="col">
					<h4>2번 그림</h4>
					<p>충남의 그리스, 아산시의 지중해마을!</p>
				</div>
				<div class="col">
					<h4>3번 그림</h4>
					<p>한국의 작은 유렵, 전남 담양에 있는 메타프로방스!</p>
				</div>
			</div>
		</div>
	
	
	</div>
	
<!-- 	
	<div class="contianer" style="height: 600px; width: 1100px; float:none; margin:0 auto">
		
	</div>
 -->
	
 	
 	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2020김민규All Right Reserved.
	</footer>
	 
    <!-- 제이쿼리 자바스크립트 추가하기 -->
    <script src="./js/jquery.min.js"></script>
    <!-- Popper 자바스크립트 추가하기 -->
    <script src="./js/popper.min.js"></script>
    <!-- 부트스트랩 자바스크립트 추가하기 -->
    <script src="./js/bootstrap.min.js"></script>
    <!-- int형의 maxLength를 확인할 때 -->

</body>
</html>



