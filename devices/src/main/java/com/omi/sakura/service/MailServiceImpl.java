package com.omi.sakura.service;

import com.omi.sakura.common.utils.Constants;
import com.omi.sakura.common.utils.GeneratePassword;
import com.omi.sakura.persistent.domain.User;
import com.omi.sakura.persistent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private final static String HOST = "smtp.sendgrid.net";

    private final static String USER_NAME = "apikey";

    private final static String PASSWORD = "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";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean forgotPassword(String email) {

        User user = userRepository.findByUserNameIs(email);
        String generatePassword = GeneratePassword.generatePassword();
        if (user != null) {
            if (sendMail(generatePassword, email, Constants.FORGOT_PASSWORD)) {
                user.setPassword(passwordEncoder.encode(generatePassword));
                user.setFirstTime(1);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sendMail(String generatePassword, String email, String type) {

        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("hanhchinh@ominext.com", "Hành Chính"));
            msg.setReplyTo(InternetAddress.parse("hanhchinh@ominext.com", false));
            StringBuilder stringBuilder = new StringBuilder();
            if (type.equals(Constants.FORGOT_PASSWORD)) {
                msg.setSubject("Thay đổi password", "UTF-8");
                stringBuilder.append("\n\nUserName của bạn là :").append(email);
                stringBuilder.append("\n\nPassword của bạn là :").append(generatePassword);
                stringBuilder.append("\n\nLink đăng nhập : http://dms.ominext.co/login");
                msg.setText(stringBuilder.toString(), "UTF-8");
            } else {
                msg.setSubject("Cấp phát password", "UTF-8");
                stringBuilder.append("\n\nUserName của bạn là : ").append(email);
                stringBuilder.append("\n\nPassword mới của bạn là : ").append(generatePassword);
                stringBuilder.append("\n\nLink đăng nhập : http://dms.ominext.co/login");
                msg.setText(stringBuilder.toString(), "UTF-8");
            }
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            Transport.send(msg);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            return false;
        }
    }

}
