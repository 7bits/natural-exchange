package it.sevenbits.helpers.jadeHelpers;

public class VkAuthService {
    private String redirectUrl = "";

    public VkAuthService() {
        String end = "&";
        UrlService urlService = new UrlService();
        redirectUrl = "https://oauth.vk.com/authorize" + "?" + "client_id=" + "4491913" + end + "scope=" + "notify" + end
        + "redirect_uri=" + urlService.uri("/new/VK/auth.html") + end + "display=" + "popup" + end + "response_type=" + "code";
    }

    public String getVkUrl() {
        return redirectUrl;
    }
}