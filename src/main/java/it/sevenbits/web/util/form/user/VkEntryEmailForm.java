package it.sevenbits.web.util.form.user;

public class VkEntryEmailForm {
    private String email;
    private String first_name;
    private String last_name;
    private String vk_link;

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(final String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(final String last_name) {
        this.last_name = last_name;
    }

    public String getVk_link() {
        return vk_link;
    }

    public void setVk_link(final String vk_link) {
        this.vk_link = vk_link;
    }
}
