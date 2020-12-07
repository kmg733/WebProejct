package user;

public class UserDTO {

	private String userID;
	private String userPassword;
	private String userEmail;
	private String userPhone;
	private String userName;
	
	public UserDTO() {}
	
	public UserDTO(String userID, String userPassword, String userEmail, String userPhone, String userName) {
		super();
		this.userID = userID;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userName = userName;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


}
