package com.teacheron.sales.dto;

public class MessageDTO {
	private String message;
	private MessageType type;
	private String errorCode;

	public MessageDTO() {
		super();
	}

	public MessageDTO(MessageType type, String message) {
		super();
		this.message = message;
		this.type = type;
	}

	public MessageDTO(MessageType type, String message, String errorCode) {
		super();
		this.message = message;
		this.type = type;
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
