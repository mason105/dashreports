package binky.reportrunner.engine;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;

public class EmailHandler {
	private static final boolean debug = false;
	private static final String subject = "Report Runner Files for: ";
	private static final String message = "Please find attached outputs for the above job.";
	private static Logger logger = Logger.getLogger(EmailHandler.class);
	public void sendEmail(String destinationEmail, String fromEmail,
			String smtpServer, String fileUrl, String jobName, String groupName)
			throws EmailException, IOException {
		logger.debug("sending email to: " + destinationEmail + " on host " + smtpServer);
		FileSystemHandler fs = new FileSystemHandler();
		MultiPartEmail email = new MultiPartEmail();
		email.setDebug(debug);
		email.setHostName(smtpServer);
		for (String toEmail : destinationEmail.split(",")) {
			email.addTo(toEmail.trim());
		}
		email.setFrom(fromEmail);
		email.setSubject(subject + jobName + " [" + groupName + "]");
		email.setMsg(message);
		List<String> tempFiles = new LinkedList<String>();

		EmailAttachment attachment = new EmailAttachment();

		String temp = copyToTemp(fileUrl);
		tempFiles.add(temp);
		attachment.setURL(fs.getURL(temp));
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Report Runner Report");

		// get the file name of the report from the complete path
		String name = fs.getFileName(fileUrl);
		attachment.setName(name);
		logger.debug("File: " + fileUrl);
		logger.debug("Name: " + name);

		email.attach(attachment);

		email.send();

		fs.deleteFile(temp);

	}

	public void sendEmail(String destinationEmail, String fromEmail,
			String smtpServer, List<String> fileUrls, String jobName,
			String groupName) throws EmailException, IOException {
		// logger.debug("sending email to: " + to + " on host " + server);
		FileSystemHandler fs = new FileSystemHandler();
		MultiPartEmail email = new MultiPartEmail();
		email.setDebug(debug);
		email.setHostName(smtpServer);
		for (String toEmail : destinationEmail.split(",")) {
			email.addTo(toEmail.trim());
		}
		email.setFrom(fromEmail);
		email.setSubject(subject + jobName + " [" + groupName + "]");
		email.setMsg("Please find attached outputs for the above job.");
		List<String> tempFiles = new LinkedList<String>();
		for (String url : fileUrls) {

			EmailAttachment attachment = new EmailAttachment();

			String temp = copyToTemp(url);
			tempFiles.add(temp);
			attachment.setURL(fs.getURL(temp));
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription(message);

			// get the file name of the report from the complete path
			String name = fs.getFileName(url);
			attachment.setName(name);
			 logger.debug("File: " + url);
			logger.debug("Name: " + name);

			email.attach(attachment);
		}

		email.send();
		for (String url : tempFiles) {
			fs.deleteFile(url);
		}
	}

	public void sendAlertEmail(String destinationEmail, String fromEmail,
			String smtpServer, String jobName, String groupName,
			boolean success, Date finishTime) throws EmailException,
			IOException {
	   logger.debug("sending email to: " + destinationEmail + " on host " + smtpServer);
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(smtpServer);
		email.setDebug(debug);
		for (String toEmail : destinationEmail.split(",")) {
			email.addTo(toEmail.trim());
		}
		email.setFrom(fromEmail);

		if (success) {
			email.setSubject("Success message for: " + jobName + " ["
					+ groupName + "]");

			email.setMsg("Job completed successfully at " + finishTime);

		} else {
			email.setSubject("Failure message for: " + jobName + " ["
					+ groupName + "]");

			email.setMsg("Job completed with error(s) at " + finishTime);

		}

		email.send();

	}

	private String copyToTemp(String url) throws IOException {
		logger.debug("copying file to temp to attach to email: " + url);
		FileSystemHandler fs = new FileSystemHandler();
		String tempDir = System.getProperty("java.io.tmpdir");

		String dest = "file://" + tempDir + "/email_" + fs.getFileName(url);
		fs.copyFile(url, dest);
		return dest;
	}

}
