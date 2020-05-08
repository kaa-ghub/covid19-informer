package com.github.kaa.covid19.informer.support.mapper;

import com.github.kaa.covid19.informer.model.Country;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class TableToCountryMapper {
    public static Map<String, Country> convert(Elements elements) {
        final Elements tbody = elements.select("tbody:nth-child(2)");
        Map<String, Country> map = new HashMap<>();
        for (Element c : tbody.select("tr")) {
            Country country = new Country();
            country.setName(c.child(0).text());
            country.setTotalCases(c.child(1).text());
            country.setNewCases(c.child(2).text());
            country.setTotalDeaths(c.child(3).text());
            country.setNewDeaths(c.child(4).text());
            country.setTotalRecovered(c.child(5).text());
            country.setActiveCases(c.child(6).text());
            country.setSerious(c.child(7).text());
            country.setTotalCases1MPop(c.child(8).text());
            country.setDeaths1MPop(c.child(9).text());
            country.setTotalTests(c.child(10).text());
            country.setTests1MPop(c.child(11).text());

            map.put(country.getName(), country);
        }
        return map;
    }
}
