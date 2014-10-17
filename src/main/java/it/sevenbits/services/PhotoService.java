package it.sevenbits.services;

import it.sevenbits.web.controller.MainController;
import it.sevenbits.web.util.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class PhotoService {
    @Autowired
    private FileManager fileManager;

    public static final String DEFAULT_PHOTO = "no_photo.png";

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * validate photo name. Saves photo if photo name not empty.
     *
     * @return new file name of photo.
     */
    public String validateAndSavePhoto(final MultipartFile photoFile) {
        String newPhoto = null;
        if (photoFile.getOriginalFilename().equals("")) {
            newPhoto = DEFAULT_PHOTO;
        } else {
            newPhoto = fileManager.savePhotoFile(photoFile, true);
        }
        return newPhoto;
    }

    public String validateAndSavePhotoWhenEditing(final MultipartFile newPhotoFile, final String oldPhotoName) {
        String newPhoto = null;
        if (newPhotoFile.getOriginalFilename().equals("")) {
            newPhoto = oldPhotoName;
        } else {
            newPhoto = fileManager.savePhotoFile(newPhotoFile, true);
            if (!(oldPhotoName.equals("image1.jpg") || oldPhotoName.equals("image2.jpg") ||
                oldPhotoName.equals("image3.jpg") || oldPhotoName.equals(DEFAULT_PHOTO))) {
                File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + oldPhotoName);
                if (!advertisementOldImageFile.delete()) {
                    logger.info("file " + oldPhotoName + " has been deleted");
                }
            }
        }
        return newPhoto;
    }

    public void deletePhoto(final String photo) {
        if (!(photo.equals("image1.jpg") || photo.equals("image2.jpg") ||
            photo.equals("image3.jpg") || photo.equals(DEFAULT_PHOTO))) {
            File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + photo);
            if (!advertisementOldImageFile.delete()) {
                logger.info("file " + photo + " has been deleted");
            }
        }
    }
}
