package demo.ai.analytical.agent.telegrambot.config;

import demo.ai.analytical.agent.telegrambot.service.AnalyticalBotReplyProcessor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static demo.ai.analytical.agent.telegrambot.enums.TelegramBotCommand.*;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyticalAgentBotConfig extends AbilityBot {

    AnalyticalBotReplyProcessor analyticalBotReplyProcessor;

    public AnalyticalAgentBotConfig(@Value("${bot.token}") String token,
                                    @Value("${bot.username}") String username,
                                    AnalyticalBotReplyProcessor analyticalBotReplyProcessor) {
        super(token, username);
        this.analyticalBotReplyProcessor = analyticalBotReplyProcessor;
    }

    public Ability help() {
        return Ability
                .builder()
                .name(HELP.getName())
                .info(HELP.getDescription())
                .locality(USER)
                .privacy(PUBLIC)
                .action(context -> {
                    log.info("The HELP command was received");

                    //todo add logs
                    //todo add validation

                    silent.send(analyticalBotReplyProcessor.replyOnHelp(context), context.chatId());
                    //todo add logs

                })
                .build();
    }

    public Ability start() {
        return Ability
                .builder()
                .name(START.getName())
                .info(START.getDescription())
                .locality(USER)
                .privacy(PUBLIC)
                .action(context -> {
                    log.info("The START command was received");

                    //todo add logs
                    //todo add validation

                    silent.send(analyticalBotReplyProcessor.replyOnStart(context), context.chatId());
                    //todo add logs

                })
                .build();
    }

    public Ability stop() {
        return Ability
                .builder()
                .name(STOP.getName())
                .info(STOP.getDescription())
                .locality(USER)
                .privacy(PUBLIC)
                .action(context -> {
                    log.info("The STOP command was received");

                    //todo add logs
                    //todo add validation

                    silent.send(analyticalBotReplyProcessor.replyOnStop(context), context.chatId());
                    //todo add logs

                })
                .build();
    }

    public Ability inn() {
        return Ability
                .builder()
                .name(INN.getName())
                .info(INN.getDescription())
                .locality(USER)
                .privacy(PUBLIC)
                .action(context -> {
                    log.info("The INN command was received");

                    //todo add logs
                    //todo add validation

                    silent.send(analyticalBotReplyProcessor.replyOnInn(context), context.chatId());
                    //todo add logs

                })
                .build();
    }

    public Ability contract() {
        return Ability
                .builder()
                .name(CONTRACT.getName())
                .info(CONTRACT.getDescription())
                .locality(USER)
                .privacy(PUBLIC)
                .action(context -> {

                    //todo add logs
                    //todo add validation

                    silent.send(analyticalBotReplyProcessor.replyOnContract(context), context.chatId());
                    //todo add logs

                })
                .build();
    }

    @Override
    public void onUpdateReceived(Update update) {

        //todo add logs
        //todo add validation

        if (isNotAbility(update)) {

            //todo add logs
            long chatId = update.getMessage().getChat().getId();
            List<String> messages = analyticalBotReplyProcessor.replyOnMessage(update);
            messages.forEach(msg -> silent.send(msg, chatId));

        }


        //todo add logs
        super.onUpdateReceived(update);
    }

    @Override
    public long creatorId() {
        return 1L;
    }

    private boolean isNotAbility(Update update) {
        if (update != null
                && update.hasMessage() &&
                update.getMessage().hasText()) {
            return !update.getMessage().getText().startsWith("/");
        } else {
            return false;
        }
    }
}