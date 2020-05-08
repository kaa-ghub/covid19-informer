package com.github.kaa.covid19.informer.service;

import com.github.kaa.covid19.informer.dao.SubscribeRepository;
import com.github.kaa.covid19.informer.model.StatisticDay;
import com.github.kaa.covid19.informer.support.CovidParserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {
    private final CovidParserHelper covidParserHelper;
    private final StatisticService statisticService;
    private final SubscribeService subscribeService;

    @Scheduled(fixedDelayString = "${application.statistic.fixedDelay}")
    private void getStatistic() {
        log.info("I'm going to crawl");
        final Pair<StatisticDay, StatisticDay> pair = covidParserHelper.getStatistic();
        StatisticDay today = pair.getLeft();
        StatisticDay yesterday = pair.getRight();
        statisticService.putStatistic(today, yesterday);
        log.info("finish");
    }

    @Scheduled(fixedDelayString = "${application.subscribe.fixedDelay}")
    private void proceedSubscribes() {
        log.info("I'm going to process subscribes");
        subscribeService.process();
        log.info("finish");
    }
}
