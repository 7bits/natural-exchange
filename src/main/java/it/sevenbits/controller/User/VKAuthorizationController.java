package it.sevenbits.controller.User;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.service.mail.MailSenderService;
import org.json.simple.parser.JSONParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/auth.html", method = RequestMethod.GET)
    public ModelAndView vkAuthorization(@RequestParam final String code) {
        mailSenderService.sendMail("dimaaasik.s@gmail.com", "GET", code);
        ModelAndView modelAndView = new ModelAndView("loginVK");

        String op = "https://oauth.vk.com/access_token?client_id=3862800&client_secret=8vaXZngg9Frx7MncBLAO&code=" + code + "&redirect_uri=http://naturalexchange.ru/VK/auth.html";
        modelAndView.addObject("vkAuth", op);
        return modelAndView;
    }

    @Resource(name = "auth")
    private MyUserDetailsService myUserDetailsService;

    @Resource(name = "userDao")
    private UserDao userDao;

    @RequestMapping(value = "/auth.html", method = RequestMethod.POST)
    public ModelAndView vkAuthorization2(@RequestBody final String json) {
        JSONParser parser = new JSONParser();
        mailSenderService.sendMail("dimaaasik.s@gmail.com", "POST1", json);

        User user = userDao.findEntityByVkId(json);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        UserDetails usrDet = myUserDetailsService.loadUserByUsername(user.getEmail());
        token.setDetails(usrDet);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
//        try {
//            Object obj = parser.parse(json);
//            JSONObject jsonObj = (JSONObject) obj;
//            String userId = (String) jsonObj.get("user_id");
//            String accessToken = (String) jsonObj.get("access_token");
//            mailSenderService.sendMail("dimaaasik.s@gmail.com", "POST2", userId);
//        } catch (org.json.simple.parser.ParseException e) {
//            mailSenderService.sendMail("dimaaasik.s@gmail.com", "Error", e.toString());
//        }

        return new ModelAndView("advertisement/list");
    }
}
