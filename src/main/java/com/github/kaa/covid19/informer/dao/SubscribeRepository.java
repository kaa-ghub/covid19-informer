package com.github.kaa.covid19.informer.dao;

import com.github.kaa.covid19.informer.model.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
}
