package com.example.MyBot;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Data
public class Config {
    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

    @Value("${spring.datasource.url}")
    String springUrl;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String springPassword;
}
