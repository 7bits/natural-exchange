package it.sevenbits.controller.User;

import it.sevenbits.controller.UsersController;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.TimeManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.json.simple.JSONObject;

import javax.annotation.Resource;


/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 06.09.13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = "VK")
public class VKAuthorizationController {

    @Resource(name = "mailService")
    private MailSenderService mailSenderService;

    @Resource(name = "auth")
    private MyUserDetailsService myUserDetailsService;

    @Resource(name = "userDao")
    private UserDao userDao;

    @RequestMapping(value = "/auth.html", method = RequestMethod.GET)
    public ModelAndView vkAuthorization(@RequestParam final String code) {
        mailSenderService.sendMail("dimaaasik.s@gmail.com", "GET", code);
        ModelAndView modelAndView = new ModelAndView("loginVK");

        String op = "https://oauth.vk.com/access_token?client_id=3862800&client_secret=8vaXZngg9Frx7MncBLAO&code=" + code + "&redirect_uri=http://naturalexchange.ru/VK/auth.html";
        modelAndView.addObject("vkAuth", op);
        return modelAndView;
    }

    @RequestMapping(value = "/auth.html", method = RequestMethod.POST)
    public @ResponseBody JSONObject vkAuthorization2(@RequestBody final String json) {
        String id = json.replaceAll("=","");
        JSONObject resultJson = new JSONObject();
        User user = userDao.findEntityByVkId(id);
        if (user != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            UserDetails usrDet = myUserDetailsService.loadUserByUsername(user.getEmail());
            token.setDetails(usrDet);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(token);
            resultJson.put("success","true");
        } else {
            resultJson.put("success","false");
        }
        return resultJson;
    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    public ModelAndView vkRegistration(@RequestParam final String email,
                               @RequestParam final String first_name,
                               @RequestParam final String last_name,
                               @RequestParam final String id) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("dsfklosdaaevvsdfywewehwehsdu");
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setVk_link(id);
        user.setIsDeleted(false);
        user.setUpdateDate(TimeManager.getTime());
        user.setCreatedDate(TimeManager.getTime());
        user.setRole("ROLE_USER");
        user.setActivationDate(TimeManager.addDate(UsersController.REGISTRATION_PERIOD));
        Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
        String code = md5encoder.encodePassword(user.getPassword(), user.getEmail() );
        user.setActivationCode(code);
        this.userDao.create(user);
        mailSenderService.sendRegisterMail(user.getEmail(), user.getActivationCode());
        ModelAndView modelAndView = new ModelAndView("user/regUserLink");
        return modelAndView;
    }
}
