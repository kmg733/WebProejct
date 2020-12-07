<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.UserDTO"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>

<%
	request.setCharacterEncoding("UTF-8");
	String userID = null;
	String userPassword = null;
	String userEmail = null;
	String userPhone = null;
	String userName = null;

	// 로그인한 상태일시 회원가입 못하게 하기
	if(session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}

	if(request.getParameter("userID") != null){
		userID = request.getParameter("userID");
	}
	if(request.getParameter("userPassword") != null){
		userPassword = request.getParameter("userPassword");
	}
	if(request.getParameter("userEmail") != null){
		userEmail = request.getParameter("userEmail");
	}
	if(request.getParameter("userPhone") != null){
		userPhone = request.getParameter("userPhone");
	}
	if(request.getParameter("userName") != null){
		userName = request.getParameter("userName");
	}
	if(userID == null || userPassword == null || userEmail == null || userName == null || userPhone == null
			|| userID.equals("") || userEmail.equals("") || userPassword.equals("")
			|| userEmail.equals("") || userPhone.equals("")) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안 된 사항이 있습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
	}
	
	UserDAO userDAO = new UserDAO();
	String result = userDAO.join(new UserDTO(userID, userPassword, userEmail, userPhone, userName));
	
	if(result.equals("userID overlab")){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 존재하는 아이디입니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
	} else if(result.equals("join Success")){
		session.setAttribute("userID", userID);
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('회원가입 성공!');");
		script.println("location.href = 'index.jsp'");
		script.println("</script>");
		script.close();
	} else if(result.equals("error")) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('데이터베이스오류!');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		
	}
%>
