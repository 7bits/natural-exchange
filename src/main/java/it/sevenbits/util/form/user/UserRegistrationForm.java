package it.sevenbits.util.form.user;

public class UserRegistrationForm {

    //@NotBlank(message = "Новость должна быть. Заполните поле.")
    //@Length(max=2000)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isReceiveNews;
    private String vkLink;

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(final String vkLink) {
        this.vkLink = vkLink;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean getIsReceiveNews() {
        return isReceiveNews;
    }

    public void setIsReceiveNews(final boolean receiveNews) {
        isReceiveNews = receiveNews;
    }
}
