package com.github.kaa.covid19.informer.service;

import com.github.kaa.covid19.informer.commands.*;
import com.github.kaa.covid19.informer.config.BotConfig;
import com.github.kaa.covid19.informer.support.CommandHelper;
import com.github.kaa.covid19.informer.support.Emoji;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

import static com.github.kaa.covid19.informer.util.Constants.*;

@Service
@Slf4j
public class BotService extends TelegramLongPollingCommandBot {
	public static final String MENU_COMMAND = "menu";
	private final CommandHelper commandHelper;
	private final BotConfig botConfig;
	private final StatisticService statisticService;
	private final SubscribeManagerService subscribeManagerService;
	private final ContextManagerService contextManagerService;

	public BotService(CommandHelper commandHelper, BotConfig botConfig, StatisticService statisticService, SubscribeManagerService subscribeManagerService, ContextManagerService contextManagerService) {
		super(botConfig.getUsername());
		this.commandHelper = commandHelper;
		this.botConfig = botConfig;
		this.statisticService = statisticService;
		this.subscribeManagerService = subscribeManagerService;
		this.contextManagerService = contextManagerService;

		register(new StartCommand("start", "hello command", this));
		register(new MenuCommand(MENU_COMMAND, "menu command", this));
		register(new CountryCommand(COUNTRY_COMMAND, "country command", this, statisticService));
		register(new CountriesCommand(COUNTRIES_COMMAND, "countries list command", this, statisticService, contextManagerService));

		final HelpCommand helpCommand = new HelpCommand(this);
		register(helpCommand);

		registerDefaultAction((absSender, message) -> {
			SendMessage commandUnknownMessage = new SendMessage();
			commandUnknownMessage.setChatId(message.getChatId());
			commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Here comes some help " + Emoji.get("ambulance"));
			try {
				absSender.execute(commandUnknownMessage);
			} catch (TelegramApiException e) {
				log.error("err", e);
			}
			helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
		});
	}

	@Override
	public String getBotToken() {
		return botConfig.getToken();
	}

	@Override
	public void processNonCommandUpdate(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			Long chatId = message.getChatId();
			final String context = contextManagerService.get(chatId);
			String text = message.getText();
			if (StringUtils.isNotBlank(context)) {
				text = context + " " + text;
				contextManagerService.clear(chatId);
			}
			text = proceedCommand(chatId, text);
			pushMessage(chatId, text);
		}
	}

	public void pushMessage(@NonNull Long chatId, String text) {
		if (StringUtils.isBlank(text)) {
			log.warn("Empty message text");
			return;
		}
		SendMessage message = new SendMessage();
		message.setText(text);
		message.setChatId(chatId);
		message.enableMarkdown(true);
		try {
			execute(message);
			log.info("Sent message \"{}\" to {}", text, chatId);
		} catch (TelegramApiException e) {
			log.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
		}
	}

	public void pushMessage(@NonNull Long chatId, SendMessage message) {
		try {
			execute(message);
			log.info("Sent message \"{}\" to {}", message.toString(), chatId);
		} catch (TelegramApiException e) {
			log.error("Failed to send message \"{}\" to {} due to error: {}", message, chatId, e.getMessage());
		}
	}

	private String proceedCommand(Long chatId, String s) {

		if(s.startsWith(TODAY_COMMAND)) {
			s = statisticService.getTodayStatistic();
		}
		else if(s.startsWith(MENU_COMMAND)) {
			s = statisticService.getTodayStatistic();
		}
		else if(s.startsWith(YESTERDAY_COMMAND)) {
			s = statisticService.getTodayStatistic();
		}
		else if(s.startsWith(DYNAMICS_COMMAND)) {
			s = statisticService.getTodayStatistic();
		}
		else if(s.startsWith(SUBSCRIBE_COMMAND)) {
			String command = commandHelper.getCountry(s);
			s = subscribeManagerService.addSubscribe(chatId, command);
		}
		else if(s.startsWith(SUBSCRIBES_COMMAND)) {
			s = subscribeManagerService.getSubscribe(chatId);
		}
		else if(s.startsWith(COUNTRY_COMMAND)) {
			String country = commandHelper.getCountry(s).trim();
			s = statisticService.getCountryStatistic(country);
		}
		return s;
	}

	@PostConstruct
	public void start() {
		log.info("username: {}, token: {}", botConfig.getUsername(), botConfig.getToken());
	}

}
