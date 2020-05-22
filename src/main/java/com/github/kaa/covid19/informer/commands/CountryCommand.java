package com.github.kaa.covid19.informer.commands;

import com.github.kaa.covid19.informer.service.BotService;
import com.github.kaa.covid19.informer.service.StatisticService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.inject.Inject;

public class CountryCommand extends BotCommand {

    private final BotService botService;
    private final StatisticService statisticService;

    /**
     * Construct a command
     *  @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     * @param statisticService
     */
    @Inject
    public CountryCommand(String commandIdentifier, String description, BotService botService, StatisticService statisticService) {
        super(commandIdentifier, description);
        this.botService = botService;
        this.statisticService = statisticService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (arguments.length < 1) {
            botService.pushMessage(chat.getId(), "Please enter country code, eq /country usa");
            return;
        }
        String country = arguments[0].trim();
        botService.pushMessage(chat.getId(), statisticService.getCountryStatistic(country));
    }
}
