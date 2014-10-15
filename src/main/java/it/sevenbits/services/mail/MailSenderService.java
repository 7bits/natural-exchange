package it.sevenbits.services.mail;

import it.sevenbits.repository.dao.SearchVariantDao;
import it.sevenbits.repository.dao.SubscriberDao;
import it.sevenbits.repository.dao.UserDao;
import it.sevenbits.repository.entity.SearchVariant;
import it.sevenbits.repository.entity.Subscriber;
import it.sevenbits.repository.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

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

    private final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    /**
     *
     */
    @Resource(name = "searchVariantDao")
    private SearchVariantDao searchVariantDao;

    @Resource(name = "subscriberDao")
    private SubscriberDao subscriberDao;

    @Resource(name = "userDao")
    private UserDao userDao;

    private MailSender mailSender;

    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
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

    //не используется, зачем нужен?
    public void sendSearchVariants() {
        MailSenderService mailService = getMailService();
        List<SearchVariant> searchVariants = this.searchVariantDao.findAll();
        for (SearchVariant entity : searchVariants) {
//            String url = generateSearchVariantUrl(entity.getCategories(), entity.getKeyWords());
//            mailService.sendMail(SERVICE_MAILBOX, entity.getEmail(), "Ваши варианты поиска", url);
        }
    }

    public void sendSearchVariant(final String email, final String keyWords, final String categories) {
        MailSenderService mailService = getMailService();
        mailService.sendMail(SERVICE_MAILBOX, email, "Ваши варианты поиска", generateSearchVariantUrl(keyWords, categories));
    }

    private String generateSearchVariantUrl(final String keyWords, final String categories) {
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

    @Async
    public void sendMail(final String email, final String title, final String text) {
        MailSenderService mailService = getMailService();
        mailService.sendMail(SERVICE_MAILBOX, email, title, text);
    }

    @Async
    public void sendRegisterMail(final String to, final String code) {
        MailSenderService mailService = getMailService();
        String link = getDomen() + "/user/magic.html?code=" + code + "&mail=" + to;
        String text = THANKS_FOR_REGISTRATION + link;
        String title = "регистрация на сайте";
        mailService.sendMail(SERVICE_MAILBOX, to, title, text);
    }

    @Async
    public void sendNotifyToModerator(final Long id, final String category) {
        MailSenderService mailService = getMailService();
        String link = getDomen() + "/advertisement/view.html?id=" + id + "&currentCategory=" + category;
        List<User> moderators = this.userDao.findAllModerators();
        for (User user : moderators) {
            mailService.sendMail(SERVICE_MAILBOX, user.getEmail(), "Новое предложение", link);
        }
    }

    public String getDomen() {
        Properties prop = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("common.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            //TODO:need to do something
            logger.warn("Can't open file in common.properties");
            e.printStackTrace();
        }
        String result = prop.getProperty("mail.service.domen");
        if (result == null) {
            result = prop.getProperty("application.domen");
        }
        return result;
    }
}
