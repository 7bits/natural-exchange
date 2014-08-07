package it.sevenbits.controller.User;

import it.sevenbits.controller.UsersController;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.service.mail.MailSenderService;
import it.sevenbits.util.TimeManager;
import it.sevenbits.util.form.VkEntryEmailForm;
import it.sevenbits.util.form.validator.VkEntryEmailFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


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
    public String vkAuthorization(@RequestParam final String code, final Model model, final RedirectAttributes redirectAttributes) {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        // Request to VK API to get token, user_id and etc. Using code getting in auth.jsp
        map.add("client_id", "4491913");
        map.add("client_secret", "Vvsmg0wg4bLTjBguOjcN");
        map.add("code", code);
        map.add("redirect_uri", "http://naturalexchange.ru/VK/auth.html");
        Map<String, Object> response = rest.postForObject("https://oauth.vk.com/access_token", map, Map.class);
        Integer id = (Integer)response.get("user_id");
        String userId = id.toString();
        User user = userDao.findEntityByVkId(userId);
        if (user != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            UserDetails usrDet = myUserDetailsService.loadUserByUsername(user.getEmail());
            token.setDetails(usrDet);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(token);
            return "redirect:/advertisement/list.html";
        } else {
            MultiValueMap<String, String> vkMap = new LinkedMultiValueMap<String, String>();
            vkMap.add("uids", userId);
            vkMap.add("fields", "first_name,last_name");
            Map<String, Object> userInfo = rest.postForObject("https://api.vk.com/method/users.get", vkMap, Map.class);
            if (userInfo.containsKey("error")) {
                return "access_denied";
            }
            VkEntryEmailForm vkEntryEmailForm = new VkEntryEmailForm();
            ArrayList<Object> data = (ArrayList<Object>)userInfo.get("response");
            LinkedHashMap<String, Object> vkResponse = (LinkedHashMap<String, Object>)data.get(0);
            vkEntryEmailForm.setFirst_name((String)vkResponse.get("first_name"));
            vkEntryEmailForm.setLast_name((String)vkResponse.get("last_name"));
            vkEntryEmailForm.setVk_link(userId);
            model.addAttribute("vkEntryEmailForm", vkEntryEmailForm);
            return "VK/auth";
        }
    }

    @Autowired
    private VkEntryEmailFormValidator vkEntryEmailFormValidator;

    @RequestMapping(value = "/auth.html", method = RequestMethod.POST)
    public String vkRegistrationComplete(final VkEntryEmailForm vkEntryEmailForm,
        final BindingResult bindingResult, final Model model) {
        vkEntryEmailFormValidator.validate(vkEntryEmailForm, bindingResult);
        if (!bindingResult.hasErrors()) {
            User user = new User();
            user.setEmail(vkEntryEmailForm.getEmail());
            user.setPassword("dsfklosdaaevvsdfywewehwehsdu");
            user.setFirstName(vkEntryEmailForm.getFirst_name());
            user.setLastName(vkEntryEmailForm.getLast_name());
            user.setVk_link(vkEntryEmailForm.getVk_link());
            user.setIsDeleted(false);
            user.setUpdateDate(TimeManager.getTime());
            user.setCreatedDate(TimeManager.getTime());
            user.setRole("ROLE_USER");
            user.setActivationDate(TimeManager.addDate(UsersController.REGISTRATION_PERIOD));
            Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
            String code = md5encoder.encodePassword(user.getPassword(), user.getEmail());
            user.setActivationCode(code);
            this.userDao.create(user);
            mailSenderService.sendRegisterMail(user.getEmail(), user.getActivationCode());
            return "VK/vkRegistrationText";
        } else {
            model.addAttribute("vkEntryEmailForm", vkEntryEmailForm);
            return "VK/auth";
        }
    }
}
