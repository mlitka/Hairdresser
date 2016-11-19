package com.litkowska.martyna.hairdresser.app.util;

import com.litkowska.martyna.hairdresser.app.configuration.EmailConfiguration;
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
    private String emailBody;

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public void setVisit(final Visit visit){
        this.visit = visit;
    }

    public void sendEmailReservation() throws AddressException, MessagingException {
        prepareMessageReservation();
        generateAndSendEmail();
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }

    public void sendEmailCancelReservation() throws AddressException, MessagingException {
        prepareMessageReservationCanceled();
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
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(visit.getClient().getUser().getEmail()));
//        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
        generateMailMessage.setSubject("HAIR SALON VISIT");
//        String emailBody = "Test email by EmLitka JavaMail API example. " + "<br><br> Regards, <br>M L ;)";
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

    private void prepareMessageReservation(){
        emailBody = "Hello "+this.visit.getClient().getUser().getFirstName()+" "+this.visit.getClient().getUser().getLastName()+"!<br><br>"
                +"You have just reserved a visit in our salon for "+this.visit.getHairService().getName()+".<br>"
                +"Our hairdresser, "+this.visit.getHairdresser().getUser().getFirstName()+" "+this.visit.getHairdresser().getUser().getLastName()
                +" will be waiting for you on "+this.visit.getDate()+" at "+this.visit.getTime()+"."
                + "<br><br>Best regards, <br>Hair Salon";
    }

    private void prepareMessageReservationCanceled(){
        emailBody = "Hello "+this.visit.getClient().getUser().getFirstName()+" "+this.visit.getClient().getUser().getLastName()+"!<br><br>"
                +"Your visit in our salon for "+this.visit.getHairService().getName()
                +" to our hairdresser "+this.visit.getHairdresser().getUser().getFirstName()+" "+this.visit.getHairdresser().getUser().getLastName()
                +" on "+this.visit.getDate()+" at "+this.visit.getTime()+" has been canceled."
                + "<br><br>Best regards, <br>Hair Salon";
    }
}
