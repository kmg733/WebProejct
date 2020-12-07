package bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;

public class BbsDAO {
	//	DB ���� ����
	private DBConnector dbConnector;
	private Connection conn;
	
	//	DB ���ǹ� ����
	private String sql;
	private PreparedStatement pstmt;
	private String returns;
	private ResultSet rs;

	public BbsDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	public String getDate() {	//	���� ��¥ ��ȯ �޼ҵ�
		try {
			conn = dbConnector.getConn();
			sql = "SELECT NOW()";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = rs.getString(1);
			} else {
				returns = "getDate Failed";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS getDate SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS getDate SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS getDate SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User getDate SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public String getNext() {	//	���� �Խù� ��ȣ ��ȯ �޼ҵ� - String���� ��ȯ�ϱ� ������ �� �� parseInt�������
		try {
			conn = dbConnector.getConn();
			sql = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = Integer.toString(rs.getInt(1) + 1);
			}
			else { // ù��° �Խù��� ���
				returns = "1";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS getNext SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS getNext SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS getNext SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User getNext SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public String write(BbsDTO bbs) {	//	�Խù� ���� �޼ҵ�
		try {
			conn = dbConnector.getConn();
			sql = "INSERT INTO BBS (bbsID, bbsTitle, userID, bbsDate, bbsContent, bbsAvailable) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// getNext �޼ҵ忡�� pstmt�� close�Ǳ� ������ SQLException �߻��� �� ����
			pstmt.setInt(1, Integer.parseInt(getNext()));	
			pstmt.setString(2, bbs.getBbsTitle());
			pstmt.setString(3, bbs.getUserID());
			// getDate �޼ҵ忡�� pstmt�� close�Ǳ� ������ SQLException �߻��� �� ����
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbs.getBbsContent());
			pstmt.setInt(6, 1);
			if(pstmt.executeUpdate() > 0) {
				returns = "write Success";
			} else {
				returns = "write Failed";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS write SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS write SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS write SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User write SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public ArrayList<BbsDTO> getList(int pageNumber) {	//	DB�κ��� �Խù� ������ �������� �Լ�
		ArrayList<BbsDTO> list = new ArrayList<BbsDTO>();
		try {
			conn = dbConnector.getConn();
			sql = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(getNext()) - (pageNumber - 1) * 10);	
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BbsDTO bbs = new BbsDTO();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS write SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS write SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS write SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User write SQLException error");
					returns = "error";
				}	
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) { // ����¡ ó�� �Լ�
		try {
			conn = dbConnector.getConn();
			//	������������ �ֽű� 10���� ���̱�
			sql = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//	���������� 10���� ����ϴ� ����
			pstmt.setInt(1, Integer.parseInt(getNext()) - (pageNumber - 1) * 10);	
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS nextPage SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS nextPage SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS nextPage SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User nextPage SQLException error");
					returns = "error";
				}	
		}	
		return false;
	}
	
	public BbsDTO getBbs(int bbsID) {
		try {
			conn = dbConnector.getConn();
			sql = "SELECT * FROM BBS WHERE bbsID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsID);	
			rs = pstmt.executeQuery();
			if(rs.next()) {
				BbsDTO bbsDTO = new BbsDTO();
				bbsDTO.setBbsID(rs.getInt(1));
				bbsDTO.setBbsTitle(rs.getString(2));
				bbsDTO.setUserID(rs.getString(3));
				bbsDTO.setBbsDate(rs.getString(4));
				bbsDTO.setBbsContent(rs.getString(5));
				bbsDTO.setBbsAvailable(rs.getInt(6));
				return bbsDTO;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS getBbs SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS getBbs SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS getBbs SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User getBbs SQLException error");
					returns = "error";
				}	
		}	
		return null;
	}
	
	public String update(int bbsID, String bbsTitle, String bbsContent) {
		try {
			conn = dbConnector.getConn();
			sql = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			if(pstmt.executeUpdate() > 0) {
				returns = "update Success";
			} else {
				returns = "update Failed";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS update SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS update SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS update SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User update SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
	
	public String delete(int bbsID) {
		try {
			conn = dbConnector.getConn();
			sql = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsID);
			if(pstmt.executeUpdate() > 0) {
				returns = "delete Success";
			} else {
				returns = "delete Failed";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("BBS delete SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("BBS delete SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("BBS delete SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User delete SQLException error");
					returns = "error";
				}	
		}	
		return returns;
	}
}
