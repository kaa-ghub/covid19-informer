package com.github.kaa.covid19.informer.service;

import com.github.kaa.covid19.informer.config.BotConfig;
import com.github.kaa.covid19.informer.support.CommandHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

import static com.github.kaa.covid19.informer.util.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotService extends TelegramLongPollingBot {
	private final CommandHelper commandHelper;
	private final BotConfig botConfig;
	private final StatisticService statisticService;
	private final SubscribeService subscribeService;
	
	@Override
	public String getBotToken() {
		return botConfig.getToken();
	}
	
	@Override
	public String getBotUsername() {
		return botConfig.getUsername();
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			Long chatId = message.getChatId();
			String text = message.getText();
			text = proceedCommand(chatId, text);
			pushMessage(chatId, text);
		}
	}

	public void pushMessage(@NonNull Long chatId, String text) {
		if (StringUtils.isEmpty(text)) {
			log.warn("Empty message text");
			return;
		}
		SendMessage response = new SendMessage();
		response.setText(text);
		response.setChatId(chatId);
		try {
			execute(response);
			log.info("Sent message \"{}\" to {}", text, chatId);
		} catch (TelegramApiException e) {
			log.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
		}
	}

	private String proceedCommand(Long chatId, String s) {
		if(s.startsWith(TODAY_COMMAND)) {
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
			s = subscribeService.addSubscribe(chatId, command);
		}
		else if(s.startsWith(COUNTRY_COMMAND)) {
			String country = commandHelper.getCountry(s);
			s = statisticService.getCountryStatistic(country);
		}
		return s;
	}

	@PostConstruct
	public void start() {
		log.info("username: {}, token: {}", botConfig.getUsername(), botConfig.getToken());
	}

}
