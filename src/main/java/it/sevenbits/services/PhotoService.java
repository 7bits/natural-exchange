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

    public final String DEFAULT_PHOTO = "no_photo.png";
    public final String DEFAULT_PHOTO_IMAGE_1 = "image1.jpg";
    public final String DEFAULT_PHOTO_IMAGE_2 = "image2.jpg";
    public final String DEFAULT_PHOTO_IMAGE_3 = "image3.jpg";
    public final String DEFAULT_AVATAR = "noavatar.png";

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
            if (!(oldPhotoName.equals(DEFAULT_PHOTO_IMAGE_1) || oldPhotoName.equals(DEFAULT_PHOTO_IMAGE_2) ||
                oldPhotoName.equals(DEFAULT_PHOTO_IMAGE_3) || oldPhotoName.equals(DEFAULT_PHOTO))) {
                File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + oldPhotoName);
                if (!advertisementOldImageFile.delete()) {
                    logger.info("file " + oldPhotoName + " has been deleted");
                }
            }
        }
        return newPhoto;
    }

    public void deletePhoto(final String photo) {
        if (!(photo.equals(DEFAULT_PHOTO_IMAGE_1) || photo.equals(DEFAULT_PHOTO_IMAGE_2) ||
            photo.equals(DEFAULT_PHOTO_IMAGE_3) || photo.equals(DEFAULT_PHOTO))) {
            File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + photo);
            if (!advertisementOldImageFile.delete()) {
                logger.info("file " + photo + " has been deleted");
            }
        }
    }

    public String validateAndSaveAvatarWhenEditing(final MultipartFile newAvatarFile, final String oldAvatarName) {
        String newAvatar = oldAvatarName;
        if (!newAvatarFile.getOriginalFilename().equals("")) {
            fileManager.deleteFile(newAvatar, false);
            newAvatar = fileManager.savePhotoFile(newAvatarFile, false);
        }
        return newAvatar;
    }

    public void deleteAvatar(final String avatar) {
        if (!(avatar.equals(DEFAULT_AVATAR))) {
            File advertisementOldImageFile = new File(fileManager.getImagesFilesPath() + avatar);
            if (!advertisementOldImageFile.delete()) {
                logger.info("file " + avatar + " has been deleted");
            }
        }
    }
}
