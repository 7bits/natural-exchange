package it.sevenbits.helpers.jadeHelpers;

public class VkAuthService {
    private String redirectUrl = "";

    public VkAuthService() {
        UrlService urlService = new UrlService();
        redirectUrl = "https://oauth.vk.com/authorize?client_id=4491913&scope=notify&"
        + "redirect_uri=" + urlService.uri("/new/VK/auth.html") + "&display=popup&response_type=code";
    }

    public String getVkUrl() {
        return redirectUrl;
    }
}