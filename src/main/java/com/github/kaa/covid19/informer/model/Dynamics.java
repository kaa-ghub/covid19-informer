package com.github.kaa.covid19.informer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Dynamics {
    @Id
    @NonNull
    String id;
    @CreationTimestamp
    LocalDateTime created;
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    Map<String, BigDecimal> delta;
}
