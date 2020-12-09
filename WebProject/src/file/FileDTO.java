package file;

public class FileDTO {
	private int fileID;
	private String fileName;
	private String fileRealName;
	private String fileContent;
	private String userID;
	

	public FileDTO() { }
	
	public FileDTO(int fileID, String fileName, String fileRealName, String fileContent, String userID) {
		super();
		this.fileID = fileID;
		this.fileName = fileName;
		this.fileRealName = fileRealName;
		this.fileContent = fileContent;
		this.userID = userID;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
