package it.sevenbits.security;

import it.sevenbits.dao.SubscriberDao;
import it.sevenbits.entity.Subscriber;
import it.sevenbits.entity.User;

import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.validator.MailingNewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 8/6/13
 * Time: 1:40 PM
 *
 */
@Controller
public class LoginController {
    @Resource(name = "subscriberDao")
    private SubscriberDao subscribertDao;

    @Autowired
    private MailingNewsValidator mailingNewsValidator;


    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("mailingNewsForm",new MailingNewsForm());
        return modelAndView;
    }


    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public ModelAndView processLogin(
            it.sevenbits.entity.User userParam,
            BindingResult result,
            MailingNewsForm mailingNewsFormParam,
            BindingResult mailRes) {

        if(mailingNewsFormParam.getEmail() != null ){

            mailingNewsValidator.validate(mailingNewsFormParam, mailRes);
            ModelAndView mdv = new ModelAndView("advertisement/placing");
            if (!mailRes.hasErrors()) {
                this.subscribertDao.create(new Subscriber(mailingNewsFormParam.getEmail()));
                MailingNewsForm mailingNewsForm = new MailingNewsForm();
                mailingNewsForm.setEmail("Ваш e-mail добавлен.");
                mdv.addObject("mailingNewsForm",mailingNewsForm);

            }
            return mdv;
        }
        /*******************************************************/

        MyUserDetailsService service = new MyUserDetailsService();
        UserDetails user = new User();
        try {
            user = service.loadUserByUsername(userParam.getUsername());
            //TODO password check!!
            Authentication request = new UsernamePasswordAuthenticationToken(userParam.getUsername(), userParam.getPassword());

            if (user.getPassword().equals(request.getCredentials())) {
                //request good
                SecurityContextHolder.getContext().
                        setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities()));

            } else {
                throw new BadCredentialsException("password");
            }

        } catch(AuthenticationException e) {
            return new ModelAndView("/user/auth_failed");
        }

        ModelAndView modelAndView = new ModelAndView("/user/loginRes");
        modelAndView.addObject("userDB", user);
        return modelAndView;
    }


}
