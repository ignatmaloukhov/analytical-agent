package demo.ai.analytical.agent.telegrambot.config;

import demo.ai.analytical.agent.core.dto.ContractDto;
import demo.ai.analytical.agent.telegrambot.service.AnalyticalBotService;
import demo.ai.analytical.agent.telegrambot.service.MessageFormatterService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import java.util.List;

import static demo.ai.analytical.agent.telegrambot.enums.TelegramBotCommand.*;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticalAgentBotConfig extends AbilityBot {

    AnalyticalBotService analyticalBotService;
    MessageFormatterService messageFormatterService;

    public AnalyticalAgentBotConfig(@Value("${bot.token}") String token,
                                    @Value("${bot.username}") String username,
                                    AnalyticalBotService analyticalBotService,
                                    MessageFormatterService messageFormatterService) {
        super(token, username);
        this.analyticalBotService = analyticalBotService;
        this.messageFormatterService = messageFormatterService;
    }

    public Ability start() {
        return Ability
                .builder()
                .name(START.getName())
                .info(START.getDescription())
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {

                    //todo add logs
                    //todo add validation

                    silent.send(messageFormatterService.noFormat(START.getMessage()), ctx.chatId());

                    //todo add logs
                })
                .build();
    }

    public Ability help() {
        return Ability
                .builder()
                .name(HELP.getName())
                .info(HELP.getDescription())
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {

                    //todo add logs
                    //todo add validation

                    silent.send(messageFormatterService.noFormat(HELP.getMessage()), ctx.chatId());

                    //todo add logs
                })
                .build();
    }

    public Ability inn() {
        return Ability
                .builder()
                .name(INN.getName())
                .info(INN.getDescription())
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {

                    //todo add logs
                    //todo add validation

                    String inn = ctx.arguments()[0];
                    List<ContractDto> contracts = analyticalBotService.getContractsByInn(inn);
                    silent.send(messageFormatterService.formatInn(inn, contracts), ctx.chatId());

                    //todo add logs
                })
                .build();
    }


    public Ability startBot() {
        return Ability
                .builder()
                .name("hello")
                .info("hello command")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
//                    eventHandler.hello();
                    silent.send("Hello", ctx.chatId());
                    log.info("Hello");
                })
                .post(ctx -> {
                    silent.send("Good bye", ctx.chatId());
                    log.info("Good bye");
                })
                .build();
    }

    @Override
    public long creatorId() {
        return 1L;
    }
}