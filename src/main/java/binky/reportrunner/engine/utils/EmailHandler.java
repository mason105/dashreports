package binky.reportrunner.engine.utils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;

public interface EmailHandler {

	public abstract void sendEmail(String destinationEmail, String fromEmail,
			String smtpServer, String fileUrl, String jobName, String groupName)
			throws EmailException, IOException;

	public abstract void sendEmail(String destinationEmail, String fromEmail,
			String smtpServer, List<String> fileUrls, String jobName,
			String groupName) throws EmailException, IOException;

	public abstract void sendAlertEmail(String destinationEmail,
			String fromEmail, String smtpServer, String jobName,
			String groupName, boolean success, Date finishTime)
			throws EmailException, IOException;

}