package it.sevenbits.controller.User;

import it.sevenbits.controller.newdesign.UserController;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "VK")
public class VKAuthorizationController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private VkService vkService;

    @Autowired
    private MailSenderService mailSenderService;

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
            String[] parameters = {"first_name", "last_name"};
            LinkedHashMap<String, Object> vkResponse = vkService.getUserDataById(userId, parameters);
            if (vkResponse == null) {
                return "redirect:/access_denied";
            }
            Map<String, String> errors = new HashMap<>();
            model.addAttribute("first_name", vkResponse.get("first_name"));
            model.addAttribute("last_name", vkResponse.get("last_name"));
            model.addAttribute("errors", errors);
            model.addAttribute("vk_link", userId);
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
            user.setIsBanned(false);
            user.setUpdateDate(TimeManager.getTime());
            user.setCreatedDate(TimeManager.getTime());
            user.setRole("ROLE_USER");
            user.setAvatar("noavatar.png");
            user.setActivationDate(TimeManager.addDate(UserController.REGISTRATION_PERIOD));
            Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
            String code = md5encoder.encodePassword(user.getPassword(), user.getEmail());
            user.setActivationCode(code);
            this.userDao.create(user);
            mailSenderService.sendRegisterMail(user.getEmail(), user.getActivationCode());
            return "VK/vkRegistrationText";
        } else {
            List<ObjectError> errors = bindingResult.getAllErrors();
            model.addAttribute("errors", errors);
            model.addAttribute("vkEntryEmailForm", vkEntryEmailForm);
            return "VK/auth";
        }
    }
}
