package file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;
import bbs.BbsDTO;

public class FileDAO {
//	DB ���� ����
	private DBConnector dbConnector;
	private Connection conn;
	
	//	DB ���ǹ� ����
	private String sql;
	private PreparedStatement pstmt;
	private String returns;
	private ResultSet rs;

	public FileDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	public String getNext() {	//	���� ���� ��ȣ ��ȯ �޼ҵ� - String���� ��ȯ�ϱ� ������ �� �� parseInt�������
		try {
			conn = dbConnector.getConn();
			sql = "SELECT fileID FROM FILEUP ORDER BY fileID DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = Integer.toString(rs.getInt(1) + 1);
			}
			else { // ù��° ������ ���
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
	
	public boolean nextPage(int pageNumber) { // ����¡ ó�� �Լ�
		try {
			conn = dbConnector.getConn();
			//	������������ 9���� ���̱�
			sql = "SELECT * FROM FILEUP WHERE fileID < ? ORDER BY fileRealName DESC LIMIT 9";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//	���������� 9���� ����ϴ� ����
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
	
	
	public String upload(String fileName, String fileRealName, String fileContent, String userID) {	//	�������ϸ��� �����ϴ� �޼ҵ�
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
	
	public ArrayList<FileDTO> getList(int pageNumber) {	//	���ε�� �������ϸ��� �������� �Լ�
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
