package it.sevenbits.controller.User;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.TimeManager;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.json.simple.JSONObject;

import javax.annotation.Resource;
import java.io.IOException;


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
        //JSONParser parser = new JSONParser();
        String id = json.replaceAll("=","");
        mailSenderService.sendMail("dimaaasik.s@gmail.com", "Id", "!"+id+"!");
        JSONObject resultJson = new JSONObject();
        boolean result;
        User user = userDao.findEntityByVkId(id);
        if (user != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            //mailSenderService.sendMail("dimaaasik.s@gmail.com", "User", user.getUsername());
            UserDetails usrDet = myUserDetailsService.loadUserByUsername(user.getEmail());
            token.setDetails(usrDet);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(token);
            resultJson.put("success","true");
            result = true;
        } else {
            resultJson.put("success","false");
            result = false;
        }
        mailSenderService.sendMail("dimaaasik.s@gmail.com", "Id", "!"+resultJson.toString()+"!");
//        try {
//            Object obj = parser.parse(json);
//            JSONObject jsonObj = (JSONObject) obj;
//            String userId = (String) jsonObj.get("user_id");
//            String accessToken = (String) jsonObj.get("access_token");
//            mailSenderService.sendMail("dimaaasik.s@gmail.com", "POST2", userId);
//        } catch (org.json.simple.parser.ParseException e) {
//            mailSenderService.sendMail("dimaaasik.s@gmail.com", "Error", e.toString());
//        }
        return resultJson;
        //return result;
    }
    class UserDet{
        public String id;
        public String first_name;
        public String last_name;

        public String toString() {
            return id + "!" + first_name + "!" + last_name;
        }
    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.POST)
    public void vkRegistration(@RequestBody final String json) {
        //String email = json.get("email").toString();

        ObjectMapper mapper = new ObjectMapper();
        UserDet user = null;
        try {

            // read from file, convert it to user class
            user = mapper.readValue(json, UserDet.class);

            // display to console
            System.out.println(user);

        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (JsonParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        mailSenderService.sendMail("dimaaasik.s@gmail.com", "Id", "!"+ user.toString()+"!");
        User user = new User();
//        user.setEmail(userRegistrationFormParam.getEmail());
//        user.setPassword(userRegistrationFormParam.getPassword());
//        user.setFirstName(userRegistrationFormParam.getFirstName());
//        user.setLastName(userRegistrationFormParam.getLastName());
//        user.setVk_link(userRegistrationFormParam.getVkLink());
        user.setIsDeleted(false);
        user.setUpdateDate(TimeManager.getTime());
        user.setCreatedDate(TimeManager.getTime());
        user.setRole("ROLE_USER");
    }
}
