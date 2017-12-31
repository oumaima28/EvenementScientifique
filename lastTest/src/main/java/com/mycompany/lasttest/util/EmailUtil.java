/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lasttest.util;
 
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
 
/**
 *
 * @author BOUKAID
 */
public class EmailUtil {
 
    public static boolean sendMail( String from , String passwooord , String message, String to, String subject) throws MessagingException {
        System.out.println("hanii :D");
        String host = "smtp.gmail.com";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", "gotoeventapp@gmail.com");
        props.put("mail.smtp.password", "//gotoeventapp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", 10000);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress("gotoeventapp@gmail.com"));
            System.out.println("hanii f emailUtil");
            mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message, "UTF-8", "html");
            System.out.println("haa lcontent " + mimeMessage.getContent());
            Transport transport = session.getTransport("smtp");
            transport.connect(host,"gotoeventapp@gmail.com","//gotoeventapp");
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return false;
    }
}