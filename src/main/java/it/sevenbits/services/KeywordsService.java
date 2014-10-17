package it.sevenbits.services;

import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

@Service
public class KeywordsService {
    public String[] stringToKeyWords(final String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.split(StringUtils.trim(str));
    }
}
