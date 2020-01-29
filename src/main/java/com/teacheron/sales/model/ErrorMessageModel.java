package com.teacheron.sales.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessageModel {

	private String key;
	private List<String> messageParams = new ArrayList<>(1);

	public ErrorMessageModel(String key) {
		super();
		this.key = key;
	}

	public ErrorMessageModel(String key, String messageParam) {
		super();
		this.key = key;
		this.messageParams.add(messageParam);
	}

	public ErrorMessageModel(String key, List<String> messageParams) {
		super();
		this.key = key;
		this.messageParams = messageParams;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getMessageParams() {
		return messageParams;
	}

	public void setMessageParams(List<String> messageParams) {
		this.messageParams = messageParams;
	}
}
