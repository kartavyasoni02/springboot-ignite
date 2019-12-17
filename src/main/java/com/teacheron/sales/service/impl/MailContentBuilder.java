package com.teacheron.sales.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * This Class is responsible for creating mail contents
 * 
 * @author kartavya.soni
 * @since 12-15-2019
 * @version 1.0
 */
@Service
public class MailContentBuilder {

	@Autowired
	private TemplateEngine templateEngine;

	/**
	 * This method builds the HTML template for email body to String
	 * 
	 * @param message
	 * @return String: converted HTML template
	 */
	public String build(String message) {
		Context context = new Context();
		context.setVariable("message", message);
		return templateEngine.process("mailTemplate", context);
	}

	/**
	 * This method builds the HTML template with dynamic data for email body
	 * contents
	 * 
	 * @param messageAndValue
	 *            map of property name as key and property value as value
	 * @param templateName
	 *            Name of the template for email
	 * @return String: converted HTML template
	 */
	public String build(Map<String, String> messageAndValue, String templateName) {
		Context context = new Context();
		Set<String> keys = messageAndValue.keySet();
		for (String key : keys) {
			context.setVariable(key, messageAndValue.get(key));
		}
		return templateEngine.process(templateName, context);
	}

}