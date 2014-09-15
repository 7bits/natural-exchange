package it.sevenbits.controller.User;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.security.MyUserDetailsService;
import it.sevenbits.services.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "new/VK")
public class VKAuthorizationController {

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
            return "redirect:/new/advertisement/list.html";
        } else {
            String[] parameters = {"first_name", "last_name"};
            LinkedHashMap<String, Object> vkResponse = vkService.getUserDataById(userId, parameters);
            if (vkResponse == null) {
                return "redirect:/access_denied";
            }
            model.addAttribute("first_name", vkResponse.get("first_name"));
            model.addAttribute("last_name", vkResponse.get("last_name"));
            model.addAttribute("vk_link", userId);
            return "VK/auth";
        }
    }
}
