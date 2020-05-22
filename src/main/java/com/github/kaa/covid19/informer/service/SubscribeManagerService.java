package com.github.kaa.covid19.informer.service;

import com.github.kaa.covid19.informer.dao.SubscribeRepository;
import com.github.kaa.covid19.informer.model.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeManagerService {
    private final SubscribeRepository subscribeRepository;

    public String addSubscribe(Long chatId, String command) {
        Subscribe subscribe = subscribeRepository.findById(chatId).orElse(new Subscribe(chatId));
        subscribe.getCommands().add(command);
        subscribeRepository.save(subscribe);
        return "Success. Current subscribes: " + subscribe.getCommands();
    }

    public String getSubscribe(Long chatId) {
        Subscribe subscribe = subscribeRepository.findById(chatId).orElse(new Subscribe(chatId));
        return "Current subscribes: " + subscribe.getCommands();
    }

    public List<Subscribe> getSubscribes() {
        return subscribeRepository.findAll();
    }
}
