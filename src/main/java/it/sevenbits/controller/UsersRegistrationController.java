package it.sevenbits.controller;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import it.sevenbits.service.mail.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "user")
public class UsersRegistrationController {

    final Logger logger = LoggerFactory
            .getLogger(AdvertisementController.class);

    @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
    public ModelAndView registrationForm() {

        ModelAndView modelAndView = new ModelAndView("user/registration");

        ApplicationContext context =
                new ClassPathXmlApplicationContext("application-context-resources.xml");
        MailSenderService mm = (MailSenderService) context.getBean("mailMail");
        try{
        mm.sendMail("naturalexchangeco@gmail.com","naturalexchangeco@gmail.com","test","hello my friend!");
        } catch(MailException me) {
             logger.error(me.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registrationRequest.html", method = RequestMethod.POST)
    public ModelAndView registrationRequestForm(@RequestParam("userName") String userName) {

        ModelAndView modelAndView = new ModelAndView("user/registrationRequest");
        modelAndView.addObject("userName",userName);
        return modelAndView;
    }
}
