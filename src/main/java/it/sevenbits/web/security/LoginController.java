package it.sevenbits.web.security;
import it.sevenbits.services.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for login page
 */
@Controller
public class LoginController {

    @Autowired
    VkService vkService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("url", vkService.getDomen() + "/VK/auth.html");
        return modelAndView;
    }
}
