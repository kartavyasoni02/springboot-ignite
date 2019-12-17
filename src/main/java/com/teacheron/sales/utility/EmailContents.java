package com.teacheron.sales.utility;

import java.util.HashMap;
import java.util.Map;

public class EmailContents {

	private String recipient;

	private String subject;

	private Map<String, String> mailContent;

	private String templateName;

	private String from;

	public EmailContents() {
		/* Do nothing because default constructor for email contents */
	}

	public EmailContents(String recipient, String subject, String templateName) {
		this.recipient = recipient;
		this.subject = subject;
		this.templateName = templateName;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSubject() {
		return subject;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Map<String, String> getMailContent() {
		return mailContent;
	}

	public void addEmailContents(String key, String value) {
		if (mailContent == null) {
			mailContent = new HashMap<>();
		}
		mailContent.put(key, value);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
