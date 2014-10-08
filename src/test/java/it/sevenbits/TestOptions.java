package it.sevenbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestOptions {
    private final Logger logger = LoggerFactory.getLogger(TestOptions.class);
    
    public String getDomen() {
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
        String result = prop.getProperty("mail.service.domen");
        if (result == null) {
            result = prop.getProperty("application.domen");
        }
        return result;
    }
}
