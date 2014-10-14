package it.sevenbits.util.form.user;

public class MailingNewsForm {

    private String emailNews;

    public MailingNewsForm() {}

    public MailingNewsForm(String previousEmail) {
        emailNews = previousEmail;
    }

    public String getEmailNews() {
        return emailNews;
    }

    public void setEmailNews(final String email) {
        this.emailNews = email;
    }
}
