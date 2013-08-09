package it.sevenbits.controller;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.User;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.NewsPostingForm;
import it.sevenbits.util.form.UserRegistrationForm;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import it.sevenbits.util.form.validator.NewsPostingValidator;
import it.sevenbits.util.form.validator.UserRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class NewsPostingController {

    @Resource(name ="mailService")
    private MailSenderService mailSenderService;

    final Logger logger = LoggerFactory
            .getLogger(NewsPostingController.class);

    @RequestMapping(value = "/post.html", method = RequestMethod.GET)
    public ModelAndView post() {

        ModelAndView modelAndView = new ModelAndView("advertisement/post");
        NewsPostingForm newsPostingForm = new NewsPostingForm();
        modelAndView.addObject("newsPostingForm",newsPostingForm);
        return modelAndView;
    }



    @Autowired
    NewsPostingValidator newsPostingValidator;

    @RequestMapping(value = "/post.html", method = RequestMethod.POST)
    public ModelAndView posting(NewsPostingForm newsPostingFormParam, BindingResult result) {

        if(newsPostingFormParam != null ){
            newsPostingValidator.validate(newsPostingFormParam, result);
            if (result.hasErrors()) {
                return new ModelAndView("/post");
            }
            this.mailSenderService.newsPosting(newsPostingFormParam.getNewsTitle(), newsPostingFormParam.getNewsText());
        }
        return new ModelAndView("/post");
    }

}
