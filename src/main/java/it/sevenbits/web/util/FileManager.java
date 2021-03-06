package it.sevenbits.web.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Class for working with files
 */
@Component
public class FileManager {

    private final Logger logger = LoggerFactory.getLogger(FileManager.class);
    private static final String defaultUserPhoto = "noavatar.png";
    private static final String defaultAdvertisementPicture = "no_photo.png";

    private String advertisementImagePath;
    private String avatarImagePath;

    public FileManager() {
        Properties prop = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("common.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            //TODO:need to do something
            logger.warn("Can't open file in common.properties");
            e.printStackTrace();
        }
        advertisementImagePath = prop.getProperty("file.manager.advertisementimagepath");
        avatarImagePath = prop.getProperty("file.manager.avatarpath");
    }

    public String savePhotoFile(final MultipartFile multipartFile, boolean isAdvertisement) {
        UUID id = UUID.randomUUID();
        String idStr = id.toString().replaceAll("-", "");
        String contentType = getType(multipartFile.getOriginalFilename());
        String fileName = "img_" + idStr + "." + contentType;
        String directory = getPath(isAdvertisement);
        String filePath = directory + fileName;
        File file = new File(filePath);
        try {
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
        } catch (Throwable e) {
            //TODO:need to do something
            logger.warn("Can't record file");
            e.printStackTrace();
        }
        return fileName;
    }

    private String getPath(boolean isAdvertisement) {
        String res = null;
        if (isAdvertisement) {
            res = advertisementImagePath;
        } else {
            res = avatarImagePath;
        }
        return res;
    }

    public boolean deleteFile(String fileName, boolean isAdvertisement) {
        String path = getPath(isAdvertisement);
        File file = new File(path + fileName);
        if (!file.exists()) {
            return false;
        }
        if (fileName.equals(defaultUserPhoto) || fileName.equals(defaultAdvertisementPicture)) {
            return true;
        }
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            //TODO: logs if file doesn't record
            logger.warn("Can't delete file");
            e.printStackTrace();
        }
        return true;
    }

    public final String getImagesFilesPath() {
        return advertisementImagePath;
    }

    private String getType(final String fileName) {
        String[] parts = StringUtils.split(fileName, '.');
        int length = parts.length;
        return parts[length - 1];
    }
}
