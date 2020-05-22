package com.github.kaa.covid19.informer.model;

import com.github.kaa.covid19.informer.support.Emoji;
import lombok.Data;

import javax.persistence.Embeddable;
import java.util.StringJoiner;

import static com.github.kaa.covid19.informer.support.mapper.CountryCodeMapper.getEmojiByCountry;

@Data
@Embeddable
public class Country {
    String name;
    String totalCases;
    String newCases;
    String totalDeaths;
    String newDeaths;
    String totalRecovered;
    String activeCases;
    String serious;
    String totalCases1MPop;
    String deaths1MPop;
    String totalTests;
    String tests1MPop;

    @Override
    public String toString() {
        return new StringJoiner("\n", "Country statistic \n", "")
                .add("name: " + name)
                .add("flag: " + getEmojiByCountry(name))
                .add("totalCases: " + totalCases)
                .add("newCases: " + newCases)
                .add("totalDeaths: " + totalDeaths)
                .add("newDeaths: " + newDeaths)
                .add("totalRecovered: " + totalRecovered)
                .add("activeCases: " + activeCases)
                .add("serious: " + serious)
                .add("totalCases1MPop: " + totalCases1MPop)
                .add("deaths1MPop: " + deaths1MPop)
                .add("totalTests: " + totalTests)
                .add("tests1MPop: " + tests1MPop)
                .toString();
    }
}
