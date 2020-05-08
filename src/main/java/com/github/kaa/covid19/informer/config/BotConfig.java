package com.github.kaa.covid19.informer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class BotConfig {
    @Value("${bot.token}")
    private String token;
    @Value("${bot.username}")
    private String username;
}
