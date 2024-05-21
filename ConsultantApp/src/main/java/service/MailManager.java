package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;


import javax.mail.Message;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

public class MailManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_FOLDER = "C:\\Users\\Milos\\Documents\\apache-tomcat-9.0.78\\apache-tomcat-9.0.78\\webapps\\upload";
	private static transient String gmailUsername;
	private static transient String gmailPassword;
	
	public MailManager() {
	}

	private static Properties loadMailConfig() throws FileNotFoundException, IOException {
		Properties mailProp = new Properties();
		mailProp.load(new FileInputStream(new File("C:\\Users\\Milos\\eclipse-workspace\\ConsultantApp\\src\\main\\webapp\\WEB-INF\\gmail.properties")));

		gmailUsername = mailProp.getProperty("username");
		gmailPassword = mailProp.getProperty("password");
		return mailProp;
	}

	public static boolean sendMail(String to, String title, String bodyText, File filePath) throws FileNotFoundException, IOException {

		Properties props = loadMailConfig();

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmailUsername, gmailPassword);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gmailUsername"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(title);
			//message.setText(body);
			
			Multipart content = new MimeMultipart();
			if(bodyText != null) {
				BodyPart body = new MimeBodyPart();
				body.setText(bodyText);
				content.addBodyPart(body);
			}
			
			if(filePath != null && UPLOAD_FOLDER.equals(filePath.toString()) == false) {
				MimeBodyPart attachment = new MimeBodyPart();
				attachment.attachFile(filePath);
				content.addBodyPart(attachment);
			}
			
			message.setContent(content);
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
}