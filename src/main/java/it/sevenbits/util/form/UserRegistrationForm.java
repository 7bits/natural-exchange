package it.sevenbits.util.form;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 7/29/13
 * Time: 3:19 PM
 *
 */
public class UserRegistrationForm {

    //@NotBlank(message = "Новость должна быть. Заполните поле.")
    //@Length(max=2000)
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String isReceiveNews;
    private String vkLink;

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(final String vkLink) {
        this.vkLink = vkLink;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getIsReceiveNews() {
        return isReceiveNews;
    }

    public void setIsReceiveNews(final String receiveNews) {
        isReceiveNews = receiveNews;
    }
}
