package lk.ijse.gdse72.backend.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lk.ijse.gdse72.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        String subject = "Your OTP for Registration";
        String body = "Dear User,\n\n" +
                "Your OTP for registration is: " + otp + "\n\n" +
                "Please enter this OTP to complete your registration.\n\n" +
                "Best regards,\nSmart Hotel Booking Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("reththennakoon.edu@gmail.com");

        mailSender.send(message);
        System.out.println("OTP email sent successfully to " + toEmail);
    }

    @Override

    public void sendBookingConfirmationEmail(String toEmail, String bookingDetails, String paymentLink) {
        String subject = "Booking Confirmation";
        String body = "<html><body>" +
                "<p>Dear User,</p>" +
                "<p>Your booking has been confirmed. Here are the details:</p>" +
                "<p>" + bookingDetails + "</p>" +
                "<p>Click the button below to proceed with the payment:</p>" +
                "<a href='" + paymentLink + "' style='display: inline-block; padding: 10px 20px; font-size: 16px; " +
                "color: #fff; background-color: #28a745; text-decoration: none; border-radius: 5px;'>Pay Now</a>" +
                "<p>Thank you for choosing our service.</p>" +
                "<p>Best regards,<br>Smart Hotel Booking Team</p>" +
                "</body></html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Enable HTML
            helper.setFrom("reththennakoon.edu@gmail.com");

            mailSender.send(message);
            System.out.println("Booking confirmation email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

}
