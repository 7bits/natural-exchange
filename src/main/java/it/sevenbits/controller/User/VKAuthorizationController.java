package it.sevenbits.controller.User;

import it.sevenbits.controller.UsersController;
import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.services.mail.MailSenderService;
import it.sevenbits.services.vk.VkService;
import it.sevenbits.util.TimeManager;
import it.sevenbits.util.form.VkEntryEmailForm;
import it.sevenbits.util.form.validator.VkEntryEmailFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "VK")
public class VKAuthorizationController {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private VkService vkService;

    @RequestMapping(value = "/auth.html", method = RequestMethod.GET)
    public String authorization(@RequestParam final String code, final Model model) {
        Map<String, Object> response = vkService.getTokenAndInfo(code);
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
            String[] parametres = {"first_name", "last_name"};
            Map<String, Object> userInfo = vkService.getUserDataById(userId, parametres);
            if (userInfo.containsKey("error")) {
                return "access_denied";
            }
            VkEntryEmailForm VkEntryEmailForm = new VkEntryEmailForm();
            ArrayList<Object> data = (ArrayList<Object>)userInfo.get("response");
            LinkedHashMap<String, Object> vkResponse = (LinkedHashMap<String, Object>)data.get(0);
            VkEntryEmailForm.setFirst_name((String)vkResponse.get("first_name"));
            VkEntryEmailForm.setLast_name((String)vkResponse.get("last_name"));
            VkEntryEmailForm.setVk_link(userId);
            model.addAttribute("vkEntryEmailForm", VkEntryEmailForm);
            return "VK/auth";
        }
    }

    @Autowired
    VkEntryEmailFormValidator vkEntryEmailFormValidator;

    @RequestMapping(value = "/auth.html", method = RequestMethod.POST)
    public String registrationComplete(final VkEntryEmailForm vkEntryEmailForm,
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
