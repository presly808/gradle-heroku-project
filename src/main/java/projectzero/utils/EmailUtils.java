package projectzero.utils;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class EmailUtils {

    private static Properties props;
    private Logger logger = LogUtils.getLogger(EmailUtils.class);

    static {
        props = new Properties();
        props.setProperty("mail.smtp.host", "mtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "avax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "465");
    }

    public static void notifyUser(String email, String title, String text) {
        CompletableFuture.runAsync(() ->
        {
            try {
                sendHTMLEmail(
                        email,
                        title,
                        "<h1>" + text + "</h1>"
                );
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Send html email via Gmail
     *
     * @param to       - recipient's email address
     * @param title    - subject of email
     * @param htmlText - html email
     * @throws MessagingException if problems with connection or authentication
     */
    private static void sendHTMLEmail(String to, String title, String htmlText)
            throws MessagingException {
        Message message = new MimeMessage(getSession("service.projectzero@gmail.com", "projectZero.pass"));

        message.setFrom(new InternetAddress("service.projectzero@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
        message.setContent(htmlText, "text/html");

        Transport.send(message);
    }

    private static Session getSession(String email, String password) {
        return Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email.substring(0, email.indexOf("@")), password);
                    }
                });
    }
}
