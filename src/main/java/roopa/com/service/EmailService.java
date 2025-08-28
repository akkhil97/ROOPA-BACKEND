package roopa.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("roopaeducationalsociety1993@gmail.com");  // your app email
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendOtpEmail(String email, String otp) {
        String subject = "OTP for Password Reset - Roopa Educational Society";
        String body = "Dear Admin,\n\n"
                + "A request was received to reset your password for the Roopa Educational Society Admin Portal.\n\n"
                + "Please use the following One-Time Password (OTP):\n\n"
                + otp + "\n\n"
                + "If you did not initiate this request, please disregard this email.\n\n"
                + "This is an automated message. Please do not reply.\n\n"
                + "Regards,\n"
                + "Roopa Educational Society Team.";

        System.out.println("Sending OTP email to: " + email); // for debug
        System.out.println("OTP content: " + body);

        sendEmail(email, subject, body);
    }

    public void sendPasswordResetConfirmationEmail(String email) {
        String subject = "Password Change Confirmation - Roopa Educational Society";
        String body = "Dear Admin,\n\n"
                + "Your password was successfully changed.\n\n"
                + "If you did not perform this action, please contact our support team immediately.\n\n"
                + "This is an automated message. Please do not reply.\n\n"
                + "Regards,\n"
                + "Roopa Educational Society.";

        System.out.println("Sending password reset confirmation to: " + email); // for debug

        sendEmail(email, subject, body);
    }

}
