package it.sevenbits.helpers;

public class VkAuthHelper {
    private String redirectUrl = "";

    public VkAuthHelper() {
        UrlHelper urlHelper = new UrlHelper();
        redirectUrl = "https://oauth.vk.com/authorize?client_id=4491913&scope=notify&"
        + "redirect_uri=" + urlHelper.uri("/new/VK/auth.html") + "&display=popup&response_type=code";
    }

    public String getVkUrl() {
        return redirectUrl;
    }
}