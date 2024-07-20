package com.iac.onboarding;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    private final String username = "bloodmusic700@gmail.com"; // Your email ID
    private final String password = "rgvx vetf jrea gtbl"; // Your email password

    public void sendEmail(String toEmail, String subject, String body, UTMLinkGenerator utmLinkGenerator) {
        String utmLink = utmLinkGenerator.generateLink();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server
        props.put("mail.smtp.port", "587"); // Replace with your SMTP port

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Create the email content with UTM link
            String emailContent = body + "\n\n";
            emailContent += "Click here to visit our website: " + utmLink;

            message.setText(emailContent);

            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);
            // Uncomment the line below if you want to store user details after sending email
            // saveUserDetails(username, toEmail, "password");

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public static void main(String[] args) {
        // Test usage
    }
}
