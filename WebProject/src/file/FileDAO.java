package file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;
import bbs.BbsDTO;

public class FileDAO {
//	DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//	DB 질의문 변수
	private String sql;
	private PreparedStatement pstmt;
	private String returns;
	private ResultSet rs;

	public FileDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	public String getNext() {	//	다음 사진 번호 반환 메소드 - String으로 반환하기 때문에 쓸 때 parseInt해줘야함
		try {
			conn = dbConnector.getConn();
			sql = "SELECT fileID FROM FILEUP ORDER BY fileID DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = Integer.toString(rs.getInt(1) + 1);
			}
			else { // 첫번째 사진일 경우
				returns = "1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("File getNext SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("File getNext SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("File getNext SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("File getNext SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public boolean nextPage(int pageNumber) { // 페이징 처리 함수
		try {
			conn = dbConnector.getConn();
			//	내림차순으로 9개를 보이기
			sql = "SELECT * FROM FILEUP WHERE fileID < ? ORDER BY fileRealName DESC LIMIT 9";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//	한페이지에 9개씩 출력하는 조건
			pstmt.setInt(1, Integer.parseInt(getNext()) - (pageNumber - 1) * 9);	
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("File nextPage SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("File nextPage SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("File nextPage SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("File nextPage SQLException error");
					returns = "error";
				}	
		}	
		return false;
	}
	
	
	public String upload(String fileName, String fileRealName, String fileContent, String userID) {	//	사진파일명을 저장하는 메소드
		try {
			conn = dbConnector.getConn();
			sql = "INSERT INTO FILEUP (fileID, fileName, fileRealName, fileContent, userID) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(getNext()));
			pstmt.setString(2, fileName);
			pstmt.setString(3, fileRealName);
			pstmt.setString(4, fileContent);
			pstmt.setString(5, userID);
			if(pstmt.executeUpdate() > 0) {
				returns = "File Upload Success";
			} else {
				returns = "File Uploade Fail";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("File upload SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("File upload SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("File upload SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("File upload SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public ArrayList<FileDTO> getList(int pageNumber) {	//	업로드된 사진파일명을 가져오는 함수
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		try {
			conn = dbConnector.getConn();
			sql = "SELECT * FROM FILEUP WHERE fileID < ? ORDER BY fileID DESC LIMIT 9";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(getNext()) - (pageNumber - 1) * 9);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FileDTO fileDTO = new FileDTO();
				fileDTO.setFileID(rs.getInt(1));
				fileDTO.setFileName(rs.getString(2));
				fileDTO.setFileRealName(rs.getString(3));
				fileDTO.setFileContent(rs.getString(4));
				list.add(fileDTO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS getList SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS getList SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS getList SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("BBS getList SQLException error");
					returns = "error";
				}	
		}
		return list;
	}
}
