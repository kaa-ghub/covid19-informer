package com.github.kaa.covid19.informer.support;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParserHelper {
    public Map<String, BigDecimal> queryToMap(Document doc, String query) {
        final Elements cases = query(doc, query);
        Map<String, BigDecimal> map = new HashMap<>();
        for (int i = 0; i < cases.size(); i++) {
            if (i % 2 == 0) {
                map.put(cases.get(i).text(), convert(cases.get(i+1).text()));
            }
        }
        return map;
    }

    private BigDecimal convert(String s) {
        s = s.replace(",", "");
        s = s.replaceAll("[^0-9.]", "");
        return new BigDecimal(s);
    }

    public Elements query(Document doc, String query) {
        return doc.select(query);
    }
}
