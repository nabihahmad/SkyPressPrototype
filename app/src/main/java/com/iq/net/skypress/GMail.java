package com.iq.net.skypress;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMail {

	final String emailPort = "587";// gmail's smtp port
	final String smtpAuth = "true";
	final String starttls = "true";
	final String emailHost = "smtp.gmail.com";

	String fromName;
	String fromEmail = "sana3aapp@gmail.com";
	String fromPassword = "";
	List toEmailList;
	String emailSubject;
	String emailBody;

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;

	public GMail() {
	}

	public GMail(String fromName, List toEmailList, String emailSubject, String emailBody) {
		this.fromName = fromName;
		this.toEmailList = toEmailList;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", smtpAuth);
		emailProperties.put("mail.smtp.starttls.enable", starttls);
	}

	public MimeMessage createEmailMessage() throws AddressException,
			MessagingException, UnsupportedEncodingException {
		try {
			mailSession = Session.getDefaultInstance(emailProperties);
		} catch (Exception e) {
		}
		emailMessage = new MimeMessage(mailSession);

		emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
		for (Object toEmail : toEmailList) {
			emailMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress((String) toEmail));
		}

		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html"); // for a html email
		return emailMessage;
	}

	public void sendEmail() throws AddressException, MessagingException {
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromEmail, fromPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
	}
}