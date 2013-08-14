package it.sevenbits.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Class for working with files
 */
public class FileManager {

    public String savingFile(final MultipartFile multipartFile) {
        UUID id = UUID.randomUUID();
        String idStr = id.toString().replaceAll("-", "");
        String contentType = getType(multipartFile.getOriginalFilename());
        String fileName = "img_" + idStr + "." + contentType;
        String directory = "/home/sdmitry/Myprojects/n-exchange/src/main/webapp/resources/images/user_images/";
        String filePath = directory + fileName;
        File file = new File(filePath);
        try {
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private String getType(final String fileName) {
        StringTokenizer token = new StringTokenizer(fileName, ".");
        String type;
        for (int i = 0 ; i < token.countTokens() - 1 ; i++) {
            token.nextToken();
        }
        type = token.nextToken();
        return type;
    }
}
