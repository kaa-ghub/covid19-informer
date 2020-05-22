package com.github.kaa.covid19.informer.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


public class CommandsHandler extends TelegramLongPollingCommandBot {

    public CommandsHandler(DefaultBotOptions options, boolean allowCommandsWithUsername, String botUsername) {
        super(options, allowCommandsWithUsername, botUsername);
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }

    @Override
    public String getBotToken() {
        return null;
    }
}
