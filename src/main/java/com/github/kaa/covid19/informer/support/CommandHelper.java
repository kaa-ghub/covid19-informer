package com.github.kaa.covid19.informer.support;

import org.springframework.stereotype.Component;

import static com.github.kaa.covid19.informer.util.Constants.*;

@Component
public class CommandHelper {

    public String getCountry(String s) {
        String countryCommand = COUNTRY_COMMAND + " ";
        s = Emoji.clear(s.substring(countryCommand.length())).trim();
        return s;
    }
}
