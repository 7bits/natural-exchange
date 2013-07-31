package it.sevenbits.service.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailSenderService {

    public static final String SERVICE_MAILBOX = "naturalexchangeco@gmail.com";

    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    public void newsPosting() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context-resources.xml");
        MailSenderService mailService = (MailSenderService) context.getBean("mailService");
        mailService.sendMail(SERVICE_MAILBOX,"naturalexchangeco@gmail.com","test","hello my friend!");
    }
}
