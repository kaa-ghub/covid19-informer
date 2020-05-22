package com.github.kaa.covid19.informer.support.mapper;

import com.github.kaa.covid19.informer.support.Emoji;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;

@Nullable
public class CountryCodeMapper {
    public static String getCodeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if("Indonesia".equals(name)) {
            return "id_flag";
        }

        String[] countries = java.util.Locale.getISOCountries();

        for (String code : countries) {
            Locale locale = new Locale("", code);
            if (locale.getDisplayCountry().toLowerCase().equals(name.toLowerCase())) {
                return Optional.of(locale)
                        .map(Locale::getCountry)
                        .map(String::toLowerCase)
                        .orElse(null);
            }
        }
        return null;
    }

    public static String getEmojiByCountry(String name) {
        return Emoji.get(getCodeByName(name));
    }
}
