package it.sevenbits.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodeDecodeHelper {
    private final Logger logger = LoggerFactory.getLogger(EncodeDecodeHelper.class);

    public static String encode(String notEncodeString) {
        if (notEncodeString == null) {
            return "";
        }
        try {
            return URLEncoder.encode(notEncodeString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //TODO: нужно или как-то залоггировать, или пробросить сообщение
        }
        return "";
    }

    public static String decode(String encodeString) {
        if (encodeString == null) {
            return "";
        }
        try {
            return URLDecoder.decode(encodeString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //TODO: нужно или как-то залоггировать, или пробросить сообщение
        }
        return "";
    }
}
