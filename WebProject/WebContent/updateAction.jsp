<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs.BbsDTO" %>
<%@ page import="bbs.BbsDAO" %>

<%
	request.setCharacterEncoding("UTF-8");
	String userID = null;
	String bbsTitle = null;
	String bbsContent = null;
	
	// 로그인한 상태일시 회원가입 못하게 하기
	if(session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}

	if(userID == null) { // 로그인이 안되어 있을 때
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요.')");
		script.println("location.href = 'userLogin.jsp'");
		script.println("</script>");
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
	if (!userID.equals(bbsDTO.getUserID())) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.');");
		script.println("location.href = 'bbs.jsp'");
		script.println("</script>");
		script.close();
	}
	 else {	//	로그인이 되어 있을 때
		if(request.getParameter("bbsTitle") != null) {
			bbsTitle = request.getParameter("bbsTitle");
		}
		if(request.getParameter("bbsContent") != null) {
			bbsContent = request.getParameter("bbsContent");
		}

		// 빈 데이터가 있을 때 알림
		if(bbsTitle == null ||  bbsContent == null || bbsTitle.equals("") || bbsContent.equals("")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
		BbsDAO bbsDAO = new BbsDAO();
		String result;
		result = bbsDAO.update(bbsID, bbsTitle, bbsContent);

		if(result.equals("update Success")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('글수정 성공!');");
			script.println("location.href = 'bbs.jsp'");
			script.println("</script>");
			script.close();
		} else if(result.equals("update Failed")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('글수정 실패!');");
			script.println("history.back();");
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
	}
%>