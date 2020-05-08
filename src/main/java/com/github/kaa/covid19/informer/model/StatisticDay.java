package com.github.kaa.covid19.informer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDay implements Serializable, Comparable<StatisticDay> {
    @Id
    @NonNull
    String id;
    @NonNull
    Long version;
    @ElementCollection(fetch = FetchType.EAGER)
    Map<String, Country> countryStatistic;
    @ElementCollection(fetch = FetchType.EAGER)
    Map<String, BigDecimal> commonStatistic;

    @Override
    public int compareTo(StatisticDay o) {
        if (o == null) {
            return 0;
        }
        return this.getVersion().compareTo(((StatisticDay) o).getVersion());
    }

    @NotNull
    public Map<String, BigDecimal> diffTo(StatisticDay o) {
        if (this.compareTo(o) < 0) {
            throw new IllegalArgumentException("Not implemented");
        }
        Map<String, BigDecimal> commonStatistic = new HashMap<>();
        for (String s : this.commonStatistic.keySet()) {
            commonStatistic.put(s, this.commonStatistic.get(s).subtract(o.getCommonStatistic().get(s)));
        }
        return commonStatistic;
    }

}
