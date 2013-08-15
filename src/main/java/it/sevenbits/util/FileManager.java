package it.sevenbits.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Class for working with files
 */
public class FileManager {

    private String path;

    public FileManager() {
        Properties prop = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("common.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            //TODO:need to do something
            e.printStackTrace();
        }
        path = prop.getProperty("file.manager.path");
    }

    public String savingFile(final MultipartFile multipartFile) {
        UUID id = UUID.randomUUID();
        String idStr = id.toString().replaceAll("-", "");
        String contentType = getType(multipartFile.getOriginalFilename());
        String fileName = "img_" + idStr + "." + contentType;
        String directory = path;
        String filePath = directory + fileName;
        File file = new File(filePath);
        try {
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
        } catch (Throwable e) {
            //TODO:need to do something
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
