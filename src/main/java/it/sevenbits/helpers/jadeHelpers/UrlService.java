package it.sevenbits.helpers.jadeHelpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class UrlService {
    public static final String DEFAULT_PAGE = "http://naturalexchange.ru";
    private static final int START_URL_PART = 2;

    private String rootPath = "";
    private String afterDomenPath = "";

    public UrlService() {
        rootPath = getRoot();
        afterDomenPath = afterDomen();
    }

    public String uri(final String relative) {

        if (relative != null) {
            if (relative.length() != 0) {
                return rootPath + relative;
            }
        }
        return rootPath;
    }

    public String getAfterDomenPath() {
        return afterDomenPath;
    }

    /**
     * Domen in common.properties must be like "http://naturalexchange.ru/..."
     * @return part after main domen
     */
    public String afterDomen() {
        String result = this.rootPath;
        int length = result.length();
        StringTokenizer tokenizer = new StringTokenizer(result, "/");
        int parts = tokenizer.countTokens();
        List<String> urlParts = new ArrayList<String>();
        if (parts >= this.START_URL_PART) {

            for (int i = 0; i < parts; i++) {
                if (i >= this.START_URL_PART) {
                    urlParts.add(tokenizer.nextToken());
                } else {
                    tokenizer.nextToken();
                }
            }
            result = "";
            for (int i = 0; i < urlParts.size(); i++) {
                result += '/' + urlParts.get(i);
            }
        }
        return result;
    }

    public String getRoot() {
        Properties prop = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("common.properties");
            prop.load(inStream);
            inStream.close();
        } catch (IOException e) {
            return DEFAULT_PAGE;
        }
        return prop.getProperty("mail.service.domen");
    }
}
