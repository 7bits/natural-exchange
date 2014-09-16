package it.sevenbits.helpers;

public class FilePathHelper {
    private String imagesPath;
    private String avatarsPath;

    public FilePathHelper() {
        imagesPath = "/uploads/user_images/";
        avatarsPath = "/uploads/avatars/";
    }

    public String getAvatarsPath() {
        return avatarsPath;
    }

    public void setAvatarsPath(String avatarsPath) {
        this.avatarsPath = avatarsPath;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }
}
