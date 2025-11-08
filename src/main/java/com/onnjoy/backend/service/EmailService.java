package com.onnjoy.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(String toEmail, String verificationToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("tallinntraining@gmail.com");
            message.setTo(toEmail);
            message.setSubject("OnnJoyGym - Verify Your Email");
            message.setText(
                    "Welcome to OnnJoyGym!\n\n" +
                            "Click the link below to verify your email:\n" +
                            baseUrl + "/api/auth/verify?token=" + verificationToken + "\n\n" +
                            "If you didn't register, ignore this email.\n\n" +
                            "Best regards,\n" +
                            "OnnJoyGym Team"
            );

            mailSender.send(message);
            System.out.println("✅ Verification email sent to: " + toEmail);

        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            // Don't throw exception - soft verification!
        }
    }
}