package DBConnect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Security.DataAES;

public class DBConnector {
	private static DBConnector instance = new DBConnector();
	
	public static DBConnector getInstance() {
		return instance;
	}
	
	private String dbURL = "jdbc:mysql://localhost:3307/WebProject?serverTimezone=UTC";
	private String dbID = "Crouxi";
	private String dbPassword = "";
	
	private FileInputStream fis;
	private FileInputStream key_fis;

	public DBConnector() {
		try {
			// 암호화된 DB password read
			String propFile = "E:/eclipse/webProject_git/WebProject/src/Security/AESkey.properties";
			Properties props = new Properties();
			fis = new FileInputStream(propFile);
			props.load(new java.io.BufferedInputStream(fis));

			// 암호화에 사용할 키 read
			String read_key = "E:/eclipse/jar_files/key_management/keymanagement.properties";
			Properties key = new Properties();
			key_fis = new FileInputStream(read_key);
			key.load(new java.io.BufferedInputStream(key_fis));
			
			dbPassword = DataAES.aesDecryption(props.getProperty("password"), key.getProperty("key"));
			
			
		} catch (FileNotFoundException e) {// 예외처리 ,대응부재 제거
			System.err.println("CommDAO FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("CommDAO IOException error");
		} catch (InvalidKeyException e) {
			System.err.println("CommDAO InvalidKeyException error");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("CommDAO NoSuchAlgorithmException error");
		} catch (NoSuchPaddingException e) {
			System.err.println("CommDAO NoSuchPaddingException error");
		} catch (InvalidAlgorithmParameterException e) {
			System.err.println("CommDAO InvalidAlgorithmParameterException error");
		} catch (IllegalBlockSizeException e) {
			System.err.println("CommDAO IllegalBlockSizeException error");
		} catch (BadPaddingException e) {
			System.err.println("CommDAO BadPaddingException error");
		} finally { // 자원 해제
			try {
				if (fis != null)
					fis.close(); // 부적절한 자원 해제
				if (key_fis != null)
					key_fis.close();
			} catch (IOException e) {
				System.err.println("DBConnector close IOTException error");
			}

		}
	}
	
	public Connection getConn() {	//	Connection 객체 반환
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (SQLException e) {
			System.err.println("DBconnector SQLException error");
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("DBConnector ClassNotFoundException error");
			return null;
		}
	}
}
