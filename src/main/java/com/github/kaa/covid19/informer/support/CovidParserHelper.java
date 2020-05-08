package com.github.kaa.covid19.informer.support;

import com.github.kaa.covid19.informer.model.StatisticDay;
import com.github.kaa.covid19.informer.support.mapper.TableToCountryMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CovidParserHelper {
    private final BrowserHelper browser;

    /**
     * @return pair of today on left and yesterday on right
     */
    public Pair<StatisticDay, StatisticDay> getStatistic() {
        String query;
        String url;
        browser.navigate("https://www.worldometers.info/coronavirus/");

        // Country table
        query = "html body div.container div.row div.col-md-8 div#nav-tabContent.tab-content div#nav-today.tab-pane.active div.main_table_countries_div table#main_table_countries_today.table.table-bordered.table-hover.main_table_countries.dataTable.no-footer";
        final Elements elementsToday = browser.query(query);
        query = "html body div.container div.row div.col-md-8 div#nav-tabContent.tab-content div#nav-yesterday.tab-pane div.main_table_countries_div table#main_table_countries_yesterday.table.table-bordered.table-hover.main_table_countries.dataTable.no-footer";
        final Elements elementsYesterday = browser.query(query);

        browser.navigate("https://www.worldometers.info/coronavirus/coronavirus-cases/");

        //Cases with Outcome
        query = "html body div.container div.row div.col-md-8 div.content-inner div div div.col-md-6:nth-child(2) div.table-responsive table.table.table-striped.table-bordered tbody tr";
        Map<String, BigDecimal> map = browser.queryToMap(query);
        query = "html body div.container div.row div.col-md-8 div.content-inner div div div.col-md-6 div.table-responsive table.table.table-striped.table-bordered tbody tr";
        map.putAll(browser.queryToMap(query));

        StatisticDay today = new StatisticDay(LocalDate.now().toString(), Instant.now().toEpochMilli(), TableToCountryMapper.convert(elementsToday), map);
        StatisticDay yesterday = new StatisticDay(LocalDate.now().minusDays(1).toString(), Instant.now().toEpochMilli()+1, TableToCountryMapper.convert(elementsYesterday), new HashMap<>());
        return Pair.of(today, yesterday);
    }

}
