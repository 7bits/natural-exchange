package it.sevenbits.controller.User;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.json.simple.JSONObject;


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

    @RequestMapping(value = "/auth.html", method = RequestMethod.GET)
    public String vkAuthorization(@RequestParam String code) {
        String op = "https://oauth.vk.com/access_token?client_id=3862800&client_secret=8vaXZngg9Frx7MncBLAO&code="+code+"&redirect_uri=http://naturalexchange.ru/VK/auth.html";
        return "redirect:"+op;
    }

    @RequestMapping(value = "/auth.html", method = RequestMethod.POST)
    public ModelAndView vkAuthorization2(@RequestBody String json) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(json);
            JSONObject jsonObj = (JSONObject) obj;
        } catch (org.json.simple.parser.ParseException e) {

        }
        return new ModelAndView("advertisement/list");
    }
}
