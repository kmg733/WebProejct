<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs.BbsDTO" %> 
<%@ page import="bbs.BbsDAO" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>우리의 커뮤니티 사이트!</title>
	 
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
	if(userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요.');");
		script.println("location.href = 'userLogin.jsp';");
		script.println("</script>");
		script.close();
	}
	
	int bbsID = 0;
	if (request.getParameter("bbsID") != null) {
		bbsID = Integer.parseInt(request.getParameter("bbsID"));
	}
	if (bbsID == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('유효하지 않은 글입니다.');");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
		script.close();
	}
	BbsDTO bbsDTO = new BbsDAO().getBbs(bbsID);
%>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">우리의 커뮤니티 사이트!</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="index.jsp">메인</a>
				</li>
				
				<li class="nav-item active">
					<a class="nav-link" href="bbs.jsp">게시판</a>
				</li>
				
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
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
			<form class="form-inline my-2 my-lg-0">
				<input class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>
	
	<section class="container">
		<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="3" style="backgroud-color: #eeeeee; text-align: center;">게시판 글 보기</th>
				</tr>
			</thead>
			<tbody>
				<tr>	
					<td style="width: 20%;">글 제목</td>
					<td colspan="2"><%= bbsDTO.getBbsTitle().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></td>
				</tr>
				<tr>	
					<td>작성자</td>
					<td colspan="2"><%= bbsDTO.getUserID() %></td>
				</tr>
				<tr>	
					<td>작성일자</td>
					<td colspan="2"><%= bbsDTO.getBbsDate().substring(0, 11) + bbsDTO.getBbsDate().substring(11, 13) + "시 " + bbsDTO.getBbsDate().substring(14, 16) + "분" %></td>
				</tr>
				<tr>	
					<td>내용</td>
					<td colspan="2" style="height: 500px; text-align: left;"><%= bbsDTO.getBbsContent().replaceAll(" ", "&nbsp;")
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") %></td>
				</tr>
			</tbody>
		</table>
		<div class="d-flex bd-highlight mb-3">
			<div class="mr-auto p-2 bd-highlight">
				<a href="bbs.jsp" class="btn btn-primary mr-sm-2">목록</a>
			<!-- 글 쓴게 본인이라면 수정과 삭제가 가능하게 만듬 -->
			<%
				if(userID != null && userID.equals(bbsDTO.getUserID())) {
			%>
					<a href="update.jsp?bbsID=<%= bbsID %>" class="btn btn-primary mr-sm-2">수정</a> 
					<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="deleteAction.jsp?bbsID=<%= bbsID %>" class="btn btn-danger mr-sm-2">삭제</a> 
			<%
				}
			%>
			</div>
			<div class="p-2 bd-highlight">
				<!-- writeAction으로 작성한 게시글 전송 -->
				<a href="write.jsp" class="btn btn-primary mr-sm-2">글쓰기</a>
			</div>
		</div>
	</section>
	
	
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



