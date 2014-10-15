package it.sevenbits.web.util.form.user;

public class UserEntryForm {

    //@NotBlank(message = "Новость должна быть. Заполните поле.")
    //@Length(max=2000)
    private String email;
    private String password;

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
}