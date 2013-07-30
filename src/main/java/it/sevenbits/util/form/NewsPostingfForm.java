package it.sevenbits.util.form;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 7/29/13
 * Time: 3:19 PM
 *
 */
public class NewsPostingfForm {

    //@NotBlank(message = "Новость должна быть. Заполните поле.")
    //@Length(max=2000)
    private String newsText;

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }
}
