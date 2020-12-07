package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class UserDAO {

	//	DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//	DB 질의문 변수
	private String sql;
	private PreparedStatement pstmt;
	private String returns;
	private ResultSet rs;

	public UserDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	public String join(UserDTO user) {
		try {
			conn = dbConnector.getConn();
			sql = "SELECT * FROM USER WHERE userID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserID());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				// 아이디 중복
				returns = "userID overlab";
			}
			else {
				sql = "INSERT INTO USER (userID, userPassword, userName, userEmail, userPhone) VALUES (?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getUserID());
				pstmt.setString(2, user.getUserPassword());
				pstmt.setString(3, user.getUserName());
				pstmt.setString(4, user.getUserEmail());
				pstmt.setString(5, user.getUserPhone());
				pstmt.executeUpdate();
				returns = "join Success";					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("User join SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("User join SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("User join SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User join SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public String login(String userID, String userPassword) {
		try {
			conn = dbConnector.getConn();
			sql = "SELECT userPassword FROM USER WHERE userID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					// 로그인 성공
					returns = "login Success";
				}
				else {
					// 로그인 실패 - 비밀번호 틀림
					returns = "login Failed";
				}
			}
			else {
				// 아이디 없음
				returns = "userID Not Exist";				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("User login SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("User login SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("User login SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User login SQLException error");
					returns = "error";
				}	
		}	
		
		return returns;
	}
}
