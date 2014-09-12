package it.sevenbits.util.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * Class for advertisement placing spring form
 */
public class AdvertisementEditingForm {
    private String title;
    private String text;
    private String tags;
    private String category;
    private MultipartFile image;
    private Long advertisementId;

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

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }
}