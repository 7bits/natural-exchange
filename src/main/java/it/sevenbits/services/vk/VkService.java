package it.sevenbits.services.vk;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class VkService {

    /**
     * Method return token, expires and userId using code.
     */
    public Map<String, Object> getTokenAndInfo(final String code) {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        // Request to VK API to get token, user_id and etc. Using code getting in auth.jsp
        map.add("client_id", "4491913");
        map.add("client_secret", "Vvsmg0wg4bLTjBguOjcN");
        map.add("code", code);
//        map.add("redirect_uri", "http://naturalexchange.ru/VK/auth.html");
        map.add("redirect_uri", "http://n-exchange.local/n-exchange/VK/auth.html"); // local
        return rest.postForObject("https://oauth.vk.com/access_token", map, Map.class);
    }

    /**
     * Function uses VK API method users.get with parametres.
     */
    public Map<String, Object> getUserDataById(final String userId, final String[] parametres) {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> vkMap = new LinkedMultiValueMap<>();
        String params = new String();
        for (int i = 0; i < parametres.length; i++) {
            params.concat(parametres[i]);
        }
        vkMap.add("uids", userId);
        vkMap.add("fields", params);
        return rest.postForObject("https://api.vk.com/method/users.get", vkMap, Map.class);
    }
}
