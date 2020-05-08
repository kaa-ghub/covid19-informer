package com.github.kaa.covid19.informer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Subscribe {
    @Id
    @NonNull
    Long chatId;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> commands = new ArrayList<>();
}
