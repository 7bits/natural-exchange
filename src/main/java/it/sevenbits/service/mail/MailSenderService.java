package it.sevenbits.service.mail;

import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.entity.Subscriber;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Service for mail posting
 */
public class MailSenderService {

    /**
     * Service mailbox
     */
    public static final String SERVICE_MAILBOX = "naturalexchangeco@gmail.com";
    /**
     *
     */
    public static final String THANKS_FOR_REGISTRATION = "Чтобы подтвердить регистрацию на нашем сайте, пройдите по ссылке: ";
    /**
     *
     */
    @Resource(name = "searchVariantDao")
    private SearchVariantDao searchVariantDao;

    @Resource(name = "subscriberDao")
    private SubscriberDao subscriberDao;

    private MailSender mailSender;

    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendMail(final String from, final String to, final String subject, final String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    private MailSenderService getMailService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context-resources.xml");
        return (MailSenderService) context.getBean("mailService");
    }

    public void sendSearchVariants() {
        MailSenderService mailService = getMailService();
        List<SearchVariant> searchVariants = this.searchVariantDao.find();
        for (SearchVariant entity : searchVariants) {
            String url = generateSearchVariantUrl(entity.getCategories(), entity.getKeyWords());
            mailService.sendMail(SERVICE_MAILBOX, entity.getEmail(), "Ваши варианты поиска", url);
        }
    }

    public void sendSearchVariant(final String email, final String keyWords, final String categories) {
        MailSenderService mailService = getMailService();
        mailService.sendMail(SERVICE_MAILBOX, email, "Ваши варианты поиска", generateSearchVariantUrl(keyWords, categories));
    }

    private String generateSearchVariantUrl(final String keyWords , final String categories) {
        String domen = getDomen();
        String baseUrl = domen + "/advertisement/list.html?" + "currentCategory=";
        return baseUrl + categories.replace(" ", "+") + "&keyWords=" + keyWords.replace(" ", "+");
    }

    public void newsPosting(final String title, final String text) {
        MailSenderService mailService = getMailService();
        List<Subscriber> subscribers = this.subscriberDao.find();
        for (Subscriber entity : subscribers) {
            mailService.sendMail(SERVICE_MAILBOX, entity.getEmail(), title, text);
        }
    }

    public void sendMail(final String email, final String title, final String text) {
        MailSenderService mailService = getMailService();
        mailService.sendMail(SERVICE_MAILBOX, email, title, text);
    }

    public void sendRegisterMail(final String to, final String code) {
        MailSenderService mailService = getMailService();
        String link = getDomen() + "/user/magic.html?user=" + code + "&mail=" + to;
        String text = THANKS_FOR_REGISTRATION + link;
        String title = "регистрация на сайте";
        mailService.sendMail(SERVICE_MAILBOX, to, title, text);
    }

    private  String getDomen() {
        Properties prop = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("common.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            //TODO:need to do something
            e.printStackTrace();
        }
        return prop.getProperty("mail.service.domen");
}   }
