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

//    private Visit visit;
//    private String emailBody;

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

//    public void setVisit(){
//        this.visit = visit;
//        System.out.println(visit);
//    }

    public void sendEmailReservation(final Visit visit) throws AddressException, MessagingException {
        System.out.println("inside");
        System.out.println(visit);
//        String emailBody = prepareMessageReservation(visit);
//        System.out.println(emailBody);
        generateAndSendEmail(visit, false);
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }

    public void sendEmailCancelReservation(final Visit visit) throws AddressException, MessagingException {
//        String emailBody = prepareMessageReservationCanceled(visit);
        generateAndSendEmail(visit, true);
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }

    private void generateAndSendEmail(final Visit visit, final boolean cancel) throws AddressException, MessagingException {

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
        String emailBody = cancel?prepareMessageReservationCanceled(visit):prepareMessageReservation(visit);
        System.out.println(emailBody);
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

    private String prepareMessageReservation(final Visit visit){
        return "Hello "+visit.getClient().getUser().getFirstName()+" "+visit.getClient().getUser().getLastName()+"!<br><br>"
                +"You have just reserved a visit in our salon for "+visit.getHairService().getName()+".<br>"
                +"Our hairdresser, "+visit.getHairdresser().getUser().getFirstName()+" "+visit.getHairdresser().getUser().getLastName()
                +" will be waiting for you on "+visit.getDate()+" at "+visit.getTime()+"."
                + "<br><br>Best regards, <br>Hair Salon";
    }

    private String prepareMessageReservationCanceled(final Visit visit){
        return "Hello "+visit.getClient().getUser().getFirstName()+" "+visit.getClient().getUser().getLastName()+"!<br><br>"
                +"Your visit in our salon for "+visit.getHairService().getName()
                +" to our hairdresser "+visit.getHairdresser().getUser().getFirstName()+" "+visit.getHairdresser().getUser().getLastName()
                +" on "+visit.getDate()+" at "+visit.getTime()+" has been canceled."
                + "<br><br>Best regards, <br>Hair Salon";
    }
}
