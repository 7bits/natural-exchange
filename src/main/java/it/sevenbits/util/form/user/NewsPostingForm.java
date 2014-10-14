package it.sevenbits.util.form.user;

public class NewsPostingForm {

    //@NotBlank(message = "Новость должна быть. Заполните поле.")
    //@Length(max=2000)
    private String newsText;
    private String newsTitle;

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(final String newsText) {
        this.newsText = newsText;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(final String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
