package com.github.kaa.covid19.informer.support;

import org.springframework.stereotype.Component;

import static com.github.kaa.covid19.informer.util.Constants.*;

@Component
public class CommandHelper {

    public String getCountry(String s) {
        return s.substring(COUNTRY_COMMAND.length()).trim();
    }
}
