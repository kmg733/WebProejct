<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="file.*" %>
<%@ page import="java.util.ArrayList" %>
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
	int pageNumber = 1;
	if(request.getParameter("pageNumber") != null) {
		try {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));			
		} catch(Exception e) {
			System.out.println("검색 페이지 번호 오류");
		}
	}

	String userID = null;
	if(session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}
	if(userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요.');");
		script.println("location.href = 'userLogin.jsp';");
		script.println("</script>");
		script.close();
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
				
				<li class="nav-item active">
					<a class="nav-link text-white" href="album.jsp">사진첩</a>
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
	
	<div class="album py-5 bg-light">
		<div class="container">
			<form action="albumAction.jsp" method="post" enctype="multipart/form-data">

				<div class="row col-13">
					<div class="align-self-end ml-auto mb-3">
						<a href="upload.jsp" class="btn btn-primary mr-sm-2">사진 업로드</a>
					</div>
				</div>
				
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

					<%
						FileDAO fileDAO = new FileDAO();
						ArrayList<FileDTO> list = fileDAO.getList(pageNumber);
						for(int i = 0; i < list.size(); i++) {
					%>
				  
					<div class="col mb-3">
						<div class="card shadow-sm">
							<img src="./upload/<%= list.get(i).getFileRealName()%>" alt="..." style="height: 280px; width: 100%">
							<div class="card-body">
								<p class="card-text"><%= list.get(i).getFileContent() %></p>
								<div class="d-flex justify-content-between align-items-center">
								</div>
							</div>
						</div>
					</div>
					
					<%
						}
					%>
					
				</div>
			</form>
			<%
				if(pageNumber != 1) {
			%>
				<a href="album.jsp?pageNumber=<%=pageNumber - 1%>" class="btn btn-success btn-arrow-left">이전</a>
			<%
				} if(fileDAO.nextPage(pageNumber + 1)) {
			%>
				<a href="album.jsp?pageNumber=<%=pageNumber + 1%>" class="btn btn-success btn-arrow-left">다음</a>
			<%
				}
			%>
		</div>
	</div>
 	
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



