package com.teacheron.sales.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.teacheron.sales.enumerations.ErrorCodeEnum;
import com.teacheron.sales.model.ErrorMessageModel;

public class TeacheronException extends RuntimeException {

	private static final long serialVersionUID = -539278721585446966L;

	private List<ErrorMessageModel> messages = new ArrayList<>(1);
	private ErrorCodeEnum errorCode;
	private Date time;
	private boolean logged;

	public TeacheronException() {
	}

	public TeacheronException(final String message) {
		this.messages.add(new ErrorMessageModel(message));
		this.errorCode = ErrorCodeEnum.GENERIC_ERROR;
		init();
	}

	public TeacheronException(final String message, final ErrorCodeEnum code) {
		this.messages.add(new ErrorMessageModel(message));
		this.errorCode = code;
		init();
	}

	public TeacheronException(final String message, final String messageParam, final ErrorCodeEnum code) {
		messages = new ArrayList<>(1);
		messages.add(new ErrorMessageModel(message, messageParam));
		this.setMessages(messages);
		this.errorCode = code;
		init();
	}

	public TeacheronException(final String message, List<String> messageParams, final ErrorCodeEnum code) {
		messages = new ArrayList<>(1);
		messages.add(new ErrorMessageModel(message, messageParams));
		this.setMessages(messages);
		this.errorCode = code;
		init();
	}

	
	public TeacheronException(final ErrorMessageModel message, final ErrorCodeEnum code) {
		messages = new ArrayList<>(1);
		messages.add(message);
		this.setMessages(messages);
		this.errorCode = code;
		init();
	}

	public TeacheronException(final List<ErrorMessageModel> messages, final ErrorCodeEnum code) {
		this.setMessages(messages);
		this.errorCode = code;
		init();
	}
	


	public TeacheronException(final String message, final ErrorCodeEnum code, final Throwable throwable) {
		super(throwable);
		this.errorCode = code;
		this.messages.add(new ErrorMessageModel(message));
		init();
	}

	public TeacheronException(Throwable error) {
		super(error);
	}

	private void init() {
		time = new Date();
	}

	public List<ErrorMessageModel> getMessages() {
		return messages;
	}

	public void setMessages(List<ErrorMessageModel> messages) {
		this.messages = messages;
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the logged
	 */
	public boolean isLogged() {
		return logged;
	}

	/**
	 * @param logged
	 *            the logged to set
	 */
	public void setLogged(boolean logged) {
		this.logged = logged;
	}

}
