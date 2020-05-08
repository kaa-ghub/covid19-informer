package com.github.kaa.covid19.informer.service;

import com.github.kaa.covid19.informer.dao.SubscribeRepository;
import com.github.kaa.covid19.informer.model.Subscribe;
import com.github.kaa.covid19.informer.support.CommandHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.kaa.covid19.informer.util.Constants.*;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final StatisticService statisticService;
    private final CommandHelper commandHelper;
    private final BotService botService;

    public void process() {
        final List<Subscribe> subscribes = subscribeRepository.findAll();


        for (Subscribe subscribe : subscribes) {
            final List<String> commands = subscribe.getCommands();
            for (String command : commands) {
                if (command.startsWith(COUNTRY_COMMAND)) {
                    String country = commandHelper.getCountry(command);
                    botService.pushMessage(subscribe.getChatId(), statisticService.getCountryStatistic(country));
                }
            }
        }
    }

    public String addSubscribe(Long chatId, String command) {
        Subscribe subscribe = subscribeRepository.findById(chatId).orElse(new Subscribe(chatId));
        subscribe.getCommands().add(command);
        subscribeRepository.save(subscribe);
        return "Success. Current subscribes: " + subscribe.getCommands();
    }
}
