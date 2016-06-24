package carrera.atletica.proceso;

import java.util.Properties;
import java.util.Date;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.InternetAddress;

public class MailSender {

    public MailSender() {
    }

    public boolean send(String host, String user, String password, String toAddress, String subject, boolean isHTMLFormat, String body, boolean debug) {
        Properties properties = new Properties();

        String correo = user;
        String ctrn = password;

        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.user", correo);
        properties.setProperty("mail.smtp.port", "25");
        Session session = Session.getInstance(properties, null);
        session.setDebug(debug);
        try {
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            MimeMessage msg = new MimeMessage(session);
            MimeBodyPart adjunto1 = new MimeBodyPart();
            MimeBodyPart adjunto2 = new MimeBodyPart();

            msg.setFrom(new InternetAddress(correo));
            msg.setRecipients(Message.RecipientType.TO, toAddress);

            msg.setSubject(subject);
            msg.setSentDate(new Date());
            if (isHTMLFormat) {
                mbp.setContent(body, "text/html");
            } else {
                mbp.setText(body);
            }
            multipart.addBodyPart(mbp);

            msg.setContent(multipart);
            Transport t = session.getTransport("smtp");
            t.connect(correo, ctrn);
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Correo Enviado exitosamente");
            t.close();
        } catch (Exception mex) {
            System.out.println(">> MailSender.send() error = " + mex);
            return false;
        }
        return true;
    }
}
