package it.sevenbits.util.form.advertisement;

import org.springframework.web.multipart.MultipartFile;

/**
 * Class for advertisement placing spring form
 */
public class AdvertisementPlacingForm {
    private String title;
    private String text;
    private String tags;
    private String category;
    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(final MultipartFile image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getTags() { return tags; }

    public void setTags(String tags) { this.tags = tags; }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }
}