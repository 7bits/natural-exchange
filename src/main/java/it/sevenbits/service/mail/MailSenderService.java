package it.sevenbits.service.mail;

import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.entity.SearchVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

public class MailSenderService {

    public static final String SERVICE_MAILBOX = "naturalexchangeco@gmail.com";

    @Resource(name = "searchVariantDao")
    private SearchVariantDao searchVariantDao;

    private MailSender mailSender;


    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendMail(String from, String to, String subject, String msg) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    private MailSenderService getMailService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context-resources.xml");
        MailSenderService mailService = (MailSenderService) context.getBean("mailService");
        return mailService;
    }

    public void sendSearchVariants(){
        MailSenderService mailService = getMailService();
        List<SearchVariant> searchVariants = this.searchVariantDao.find();
        mailService.sendMail(SERVICE_MAILBOX,"dimaaasik.s@gmail.com","test","hello my friend2");
        for (SearchVariant entity : searchVariants) {
            mailService.sendMail(SERVICE_MAILBOX,entity.getEmail(),"test",entity.getCategories());
        }
    }

    public void newsPosting() {
        MailSenderService mailService = getMailService();
        mailService.sendMail(SERVICE_MAILBOX,"dimaaasik.s@gmail.com","test","hello my friend!");
    }
}
