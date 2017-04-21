package email;

import java.util.Set;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class Email extends Thread {
	private String fromEmail;
	private String fromNome;
	private Set<String> emails;
	private String subject;
	private String message;
	private String caminhoAnexo;

	public Email(String fromEmail, String fromNome, Set<String> emails, String subject, String message,
			String caminhoAnexo) {
		this.fromEmail = fromEmail;
		this.fromNome = fromNome;
		this.emails = emails;
		this.subject = subject;
		this.message = message;
		this.caminhoAnexo = caminhoAnexo;
	}

	@Override
	public void run() {
		try {
			sendEmail(fromEmail, fromNome, emails, subject, message, caminhoAnexo);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public void sendEmail(String fromEmail, String fromNome, Set<String> emails, String subject, String message,
			String caminhoAnexo) throws EmailException {

		EmailAttachment emailAttachment = new EmailAttachment();
		emailAttachment.setPath(caminhoAnexo);
		emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);

		MultiPartEmail email = new MultiPartEmail();
		// email.setDebug(true);
		email.setHostName("101.1.0.91");
		email.setSmtpPort(25);

		for (String e : emails) {
			email.addTo(e);
		}

		email.setFrom(fromEmail, fromNome);
		email.setSubject(subject);
		email.setMsg(message);
		email.attach(emailAttachment);
		System.out.println("-------------------E-MAIL--------------------");
		email.send();
	}
}
