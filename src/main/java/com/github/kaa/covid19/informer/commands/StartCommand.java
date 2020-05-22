package com.github.kaa.covid19.informer.commands;

import com.github.kaa.covid19.informer.service.BotService;
import com.github.kaa.covid19.informer.support.Emoji;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.inject.Inject;

public class StartCommand extends BotCommand {

    private final BotService botService;

    /**
     * Construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     */
    @Inject
    public StartCommand(String commandIdentifier, String description, BotService botService) {
        super(commandIdentifier, description);
        this.botService = botService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        botService.pushMessage(chat.getId(), "hello " + Emoji.get("snowman"));
    }
}
