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
import java.util.List;

/**
 * Service for mail posting
 */
public class MailSenderService {

    /**
     * Service mailbox
     */
    public static final String SERVICE_MAILBOX = "naturalexchangeco@gmail.com";

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
        String baseUrl = "http://n-exchange.local/n-exchange/advertisement/list.html?" + "currentCategory=";
        return baseUrl + categories.replace(" ", "+") + "&keyWords=" + keyWords.replace(" ", "+");
    }

    public void newsPosting(final String title, final String text) {
        MailSenderService mailService = getMailService();
        List<Subscriber> subscribers = this.subscriberDao.find();
        for (Subscriber entity : subscribers) {
            mailService.sendMail(SERVICE_MAILBOX, entity.getEmail(), title, text);
        }
    }
}
