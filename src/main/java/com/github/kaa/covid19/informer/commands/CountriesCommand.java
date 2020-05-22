package com.github.kaa.covid19.informer.commands;

import com.github.kaa.covid19.informer.service.BotService;
import com.github.kaa.covid19.informer.service.ContextManagerService;
import com.github.kaa.covid19.informer.service.StatisticService;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.kaa.covid19.informer.support.mapper.CountryCodeMapper.getEmojiByCountry;
import static com.github.kaa.covid19.informer.util.Constants.*;

public class CountriesCommand extends BotCommand {

    private final BotService botService;
    private final StatisticService statisticService;
    private final ContextManagerService contextManagerService;
    private static int PAGE_ARGUMENT_POSITION = 1;

    /**
     * Construct a command
     *  @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     * @param statisticService
     * @param contextManagerService
     */
    @Inject
    public CountriesCommand(String commandIdentifier, String description, BotService botService, StatisticService statisticService, ContextManagerService contextManagerService) {
        super(commandIdentifier, description);
        this.botService = botService;
        this.statisticService = statisticService;
        this.contextManagerService = contextManagerService;
    }

    private static KeyboardRow apply(String c) {
        KeyboardRow row = new KeyboardRow();
        row.add(c + " " + getEmojiByCountry(c));
        return row;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage sendMessage = new SendMessage();
        final Long chatId = chat.getId();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText("List");

        int page = 1;
        if (isPage(arguments)) {
            page = Integer.parseInt(arguments[PAGE_ARGUMENT_POSITION]);
        }

        contextManagerService.set(chatId, COUNTRY_COMMAND);
        botService.pushMessage(chatId,
                sendMessage.setReplyMarkup(getCountriesKeyboard(statisticService.getCountriesList(), page)));
    }

    private boolean isPage(String[] arguments) {
        return arguments.length > PAGE_ARGUMENT_POSITION &&
                StringUtils.isNotBlank(arguments[PAGE_ARGUMENT_POSITION]) &&
                StringUtils.isNumeric(arguments[PAGE_ARGUMENT_POSITION]);
    }

    private ReplyKeyboardMarkup getCountriesKeyboard(List<String> countries, int page) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        int PAGE_SIZE = 10;
        int nextPage = page + 1;
        int pages = countries.size() / PAGE_SIZE;

        int min = page * PAGE_SIZE;
        int max = nextPage * PAGE_SIZE;


        List<String> countriesPage = countries.subList(min, max);
        List<KeyboardRow> keyboard = countriesPage.stream().map(CountriesCommand::apply).collect(Collectors.toList());

        // is last page
        if (page < pages - 1) {
            KeyboardRow row = new KeyboardRow();
            row.add(String.format("%s page %d of %d", COUNTRIES_COMMAND, nextPage, pages));
            keyboard.add(row);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}
