package it.sevenbits.services.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class VkService {
    private final Logger logger = LoggerFactory.getLogger(VkService.class);

    /**
     * Method return token, expires and userId using code.
     */
    public Map<String, Object> getTokenAndInfo(final String code) {
        String domen = getDomen();
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        // Request to VK API to get token, user_id and etc. Using code getting in auth.jsp
        map.add("client_id", "4491913");
        map.add("client_secret", "Vvsmg0wg4bLTjBguOjcN");
        map.add("code", code);
        map.add("redirect_uri", domen + "/new/VK/auth.html");
        return rest.postForObject("https://oauth.vk.com/access_token", map, Map.class);
    }

    /**
     * Function uses VK API method users.get with parameters.
     */
    public LinkedHashMap<String, Object> getUserDataById(final String userId, final String[] parameters) {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> vkMap = new LinkedMultiValueMap<>();
        String params = "";
        for (String param : parameters) {
            params.concat(param);
        }
        vkMap.add("uids", userId);
        vkMap.add("fields", params);
        Map<String, Object> userInfo = rest.postForObject("https://api.vk.com/method/users.get", vkMap, Map.class);
        if (userInfo.containsKey("error")) {
            return null;
        }
        ArrayList<Object> data = (ArrayList<Object>)userInfo.get("response");
        return (LinkedHashMap<String, Object>)data.get(0);
    }

    public String getDomen() {
        Properties prop = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("common.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            //TODO:need to do something
            logger.warn("Can't open file in common.properties");
            e.printStackTrace();
        }
        String result = prop.getProperty("mail.service.domen");
        if (result == null) {
            result = prop.getProperty("application.domen");
        }
        return result;
    }
}
