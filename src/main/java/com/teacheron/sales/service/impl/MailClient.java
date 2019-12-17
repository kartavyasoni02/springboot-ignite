package com.teacheron.sales.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.teacheron.sales.utility.EmailContents;

/**	
 * This class is to parepare a mail client
 * 
 * @author kartavya.soni
 * @since 12-15-2019
 */
@Service
@PropertySource(value = { "classpath:application.properties" })
public class MailClient {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Autowired
	private Environment environment;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * This method prepares all the prerequisite for email and then send email
	 * 
	 * @param emailContents
	 * @return String
	 */
	public String prepareAndSend(EmailContents emailContents) {
		try {
			Runnable task = () -> {
				MimeMessagePreparator messagePreparator = mimeMessage -> {
						MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
						if (emailContents.getFrom() != null && !emailContents.getFrom().isEmpty()) {
							messageHelper.setFrom(emailContents.getFrom());
						} else {
							messageHelper.setFrom(environment.getRequiredProperty("spring.mail.username"));
						}
						 messageHelper.setTo(emailContents.getRecipient());
						messageHelper.setSubject(emailContents.getSubject());
						String content = mailContentBuilder.build(emailContents.getMailContent(), emailContents.getTemplateName());
						messageHelper.setText(content, true);
				};

				mailSender.send(messagePreparator);
			};
			// start the thread
			new Thread(task).start();
			return "{\"message\": \"OK\"}";
		} catch (Exception e) {
			logger.error("MailClient.prepareAndSend() - {\"message\": \"Error\"}", e);
			return "{\"message\": \"Error\"}";
		}
	}
}
