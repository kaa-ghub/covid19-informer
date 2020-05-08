package com.github.kaa.covid19.informer.dao;

import com.github.kaa.covid19.informer.model.StatisticDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatisticDayRepository extends JpaRepository<StatisticDay, String> {
}
