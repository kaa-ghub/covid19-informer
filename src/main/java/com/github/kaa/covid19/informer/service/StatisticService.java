package com.github.kaa.covid19.informer.service;

import com.github.kaa.covid19.informer.dao.DynamicsRepository;
import com.github.kaa.covid19.informer.dao.StatisticDayRepository;
import com.github.kaa.covid19.informer.model.Country;
import com.github.kaa.covid19.informer.model.Dynamics;
import com.github.kaa.covid19.informer.model.Statistic;
import com.github.kaa.covid19.informer.model.StatisticDay;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    public static final String NO_DATA = "No data";
    public static final String TODAY = "TODAY";
    private Statistic statistic = new Statistic(new HashMap<>());
    private final StatisticDayRepository statisticDayRepository;
    private final DynamicsRepository dynamicsRepository;

    public void putStatistic(StatisticDay today, StatisticDay yesterday) {
        statistic.getStatistic().put(LocalDate.now(), today);
        statistic.getStatistic().put(LocalDate.now().minusDays(1), yesterday);

        saveToday(today);
        statisticDayRepository.save(yesterday);
        //@NotNull final Map<String, BigDecimal> decimalMap = statisticDayRepository.findAll().get(0).diffTo(statisticDayRepository.findAll().get(1));
        final List<StatisticDay> all = statisticDayRepository.findAll();
    }

    public String getTodayStatistic() {
        final StatisticDay today = statisticDayRepository.findById(TODAY).orElseThrow(() -> new RuntimeException("Not ready"));
        String result = formatMessage(today);
        if (StringUtils.isEmpty(result)) {
            return NO_DATA;
        }
        return result;
    }

    public String getYesterdayStatistic() {
        final StatisticDay yesterday = statisticDayRepository.findById(LocalDate.now().minusDays(1).toString())
                .orElseThrow(() -> new RuntimeException("Not ready"));
        String result = formatMessage(yesterday);
        if (StringUtils.isEmpty(result)) {
            return NO_DATA;
        }
        return result;
    }

    private String formatMessage(@NonNull StatisticDay statisticDay) {
        StringBuilder result = new StringBuilder();
        for (String k : statisticDay.getCommonStatistic().keySet()) {
            result.append(k);
            result.append(" : ");
            result.append(statisticDay.getCommonStatistic().get(k).toString());
            result.append("\n");
        }
        return result.toString();
    }

    private void saveToday(StatisticDay today) {
        final Map<String, BigDecimal> commonStatistic = today.getCommonStatistic();
        final Optional<StatisticDay> yesterday = statisticDayRepository.findById(TODAY);
        if (yesterday.isPresent()) {
            Map<String, BigDecimal> diffTo = today.diffTo(yesterday.get());
            dynamicsRepository.save(new Dynamics(TODAY, diffTo));
        }
        StatisticDay clonedToday = cloneStatisticDay(today);
        statisticDayRepository.save(clonedToday);
    }

    private StatisticDay cloneStatisticDay(StatisticDay today) {
        StatisticDay statisticDay = new StatisticDay();
        statisticDay.setVersion(today.getVersion());
        statisticDay.setCommonStatistic(today.getCommonStatistic());
        statisticDay.setCountryStatistic(today.getCountryStatistic());
        statisticDay.setId(TODAY);
        return statisticDay;
    }

    public String getCountryStatistic(String s) {
        final StatisticDay today = statisticDayRepository.findById(TODAY).orElseThrow(() -> new RuntimeException("Not ready"));
        final Country country = today.getCountryStatistic().get(s);
        if (country == null) {
            return "Country " + s + " not found";
        }
        return country.toString();
    }

    public List<String> getCountriesList() {
        final StatisticDay today = statisticDayRepository.findById(TODAY).orElseThrow(() -> new RuntimeException("Not ready"));
        return today.getCountryStatistic().keySet().stream().collect(Collectors.toList());
    }
}
