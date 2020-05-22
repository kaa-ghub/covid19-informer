package com.github.kaa.covid19.informer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ContextManagerService {
    private final Map<Long, String> context = new ConcurrentHashMap<>();

    public String get(Long chatId) {
        return context.get(chatId);
    }

    public void set(Long chatId, String data) {
        context.put(chatId, data);
    }

    public void clear(Long chatId) { context.remove(chatId); }
}
