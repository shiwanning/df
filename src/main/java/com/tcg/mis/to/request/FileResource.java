package com.tcg.mis.to.request;

public class FileResource {

	private Long id;
	private String filename;
	private String fileType;
	private byte[] fileValue;
	private Integer fileSize;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileValue() {
		return fileValue;
	}

	public void setFileValue(byte[] fileValue) {
		this.fileValue = fileValue;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileResource [id=" + id + ", filename=" + filename
				+ ", fileType=" + fileType + ", fileSize=" + fileSize + "]";
	}

}
