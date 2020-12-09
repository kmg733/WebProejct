<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.File" %>
<%@ page import="file.*" %>


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
	} else {
		
		String directory = application.getRealPath("/upload/");
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		
		MultipartRequest multipartRequest 
		= new MultipartRequest(request, directory, maxSize, encoding, new DefaultFileRenamePolicy());
		
		String fileName = multipartRequest.getOriginalFileName("file");
		String fileRealName = multipartRequest.getFilesystemName("file");
		String fileContent = multipartRequest.getParameter("fileContent");

		FileDAO fileDao = new FileDAO();
		String result = fileDao.upload(fileName, fileRealName, fileContent, userID);
		
		if(result.equals("File Upload Success")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('파일 업로드 성공!');");
			script.println("location.href = 'album.jsp'");
			script.println("</script>");
			script.close();
		} else if(result.equals("File Uploade Fail")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('파일 업로드 실패!');");
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



