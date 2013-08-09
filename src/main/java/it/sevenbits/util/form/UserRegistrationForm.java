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
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String vkLink;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegistrationForm that = (UserRegistrationForm) o;

        if (!confirmPassword.equals(that.confirmPassword)) return false;
        if (!email.equals(that.email)) return false;
        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        if (!password.equals(that.password)) return false;
        if (!vkLink.equals(that.vkLink)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + confirmPassword.hashCode();
        result = 31 * result + vkLink.hashCode();
        return result;
    }
}
