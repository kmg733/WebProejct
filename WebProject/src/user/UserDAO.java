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
	
	public String join(String id, String password, String name, String email, String phone) {
		try {
			conn = dbConnector.getConn();
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = "join Failed";
				return returns;
			}
			
			sql = "insert into user (id, password, name, email, phone) values (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.setString(5, phone);
			if (pstmt.executeUpdate() < 1) {
				return "join Failed";
			}
			returns = "join Success";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("User addUser_Add SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_Add SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_Add SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_Add SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public String login(String id, String password) {
		try {
			conn = dbConnector.getConn();
			sql = "select * from user where id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = "login Success";
			}
			else {
				returns = "login Failed";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("User addUser_Add SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_Add SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_Add SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_Add SQLException error");
					returns = "error";
				}	
		}	
		
		return returns;
	}
}
