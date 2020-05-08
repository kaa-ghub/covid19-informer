package com.github.kaa.covid19.informer.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommonStatistic {
    BigDecimal casesWithOutcome;
    BigDecimal RecoveredOrDischarged;
    BigDecimal SeriousOrCritical;
    BigDecimal CurrentlyInfected;
    BigDecimal Deaths;
    BigDecimal MildCondition;
}
