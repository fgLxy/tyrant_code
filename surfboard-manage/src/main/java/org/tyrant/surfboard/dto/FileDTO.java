package org.tyrant.surfboard.dto;


public class FileDTO {
	private String fileName;
	private String path;
	public FileDTO(String fileName, String path) {
		this.fileName = fileName;
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
