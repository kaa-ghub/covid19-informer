package com.github.kaa.covid19.informer.model;

import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Value
public class Statistic {
    Map<LocalDate, StatisticDay> statistic;
}
