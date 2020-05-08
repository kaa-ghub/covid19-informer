package com.github.kaa.covid19.informer.dao;

import com.github.kaa.covid19.informer.model.Dynamics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicsRepository extends JpaRepository<Dynamics, String> {
}
