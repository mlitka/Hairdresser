package com.litkowska.martyna.hairdresser.app.util;

import com.litkowska.martyna.hairdresser.app.configuration.EmailConfiguration;
import com.litkowska.martyna.hairdresser.app.dto.ClientDTO;
import com.litkowska.martyna.hairdresser.app.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Martyna on 03.11.2016.
 */
@Service
public class EmailSender {

    @Autowired
    private EmailConfiguration emailConfiguration;

    private Visit visit;
    private ClientDTO client;

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public void sendEmail(final Visit visit, final ClientDTO client) throws AddressException, MessagingException {
        this.visit = visit;
        this.client = client;
        generateAndSendEmail();
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }

    private void generateAndSendEmail() throws AddressException, MessagingException {

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", emailConfiguration.getSmtp().getPort());
        mailServerProperties.put("mail.smtp.auth", emailConfiguration.getSmtp().isAuth());
        mailServerProperties.put("mail.smtp.starttls.enable", emailConfiguration.getSmtp().isStarttlsEnable());
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("m_litkow@vp.pl"));
//        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
        generateMailMessage.setSubject("HAIR SALON VISIT");
        String emailBody = "Test email by EmLitka JavaMail API example. " + "<br><br> Regards, <br>M L ;)";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport(emailConfiguration.getTransport());

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(emailConfiguration.getConnect(), emailConfiguration.getGmailID(), emailConfiguration.getGmailPass());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    private String prepareMessage(){
        return "Hello "+this.client.getFirstName()+" "+this.client.getLastName()+"!<br><br>"
                +"You have just reserved a visit in our salon for "+this.visit.getHairService().getName()+".<br>"
                +"Our hairdresser, "+this.visit.getHairdresser().getFirstName()+" "+this.visit.getHairdresser().getLastName()
                +" will be waiting for you on "+this.visit.getDate()+" at "+this.visit.getTime()+"."
                + "<br><br>Best regards, <br>Hair Salon";
    }
}
