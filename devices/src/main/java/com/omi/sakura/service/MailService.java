package com.omi.sakura.service;

public interface MailService {

    boolean forgotPassword(String mail);

    boolean sendMail(String generatePassword, String email, String type);

}
