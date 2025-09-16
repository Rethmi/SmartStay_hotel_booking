package lk.ijse.gdse72.backend.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    void sendOtpEmail(String toEmail, String otp);

    void sendEmail(String to, String subject, String body);
    @Async
    void sendBookingConfirmationEmail(String toEmail, String bookingDetails, String paymentLink);
}
