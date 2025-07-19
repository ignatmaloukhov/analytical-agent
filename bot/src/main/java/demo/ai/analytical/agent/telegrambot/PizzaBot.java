package demo.ai.analytical.agent.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Slf4j
@Component
public class PizzaBot extends AbilityBot {

    public PizzaBot(@Value("${bot.token}") String token,
                    @Value("${bot.username}") String username) {
        super(token, username);
    }

    public Ability startBot() {
        return Ability
                .builder()
                .name("hello")
                .info(Constants.START_DESCRIPTION)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
//                    eventHandler.hello();
                    silent.send("Hello", ctx.chatId());
                    log.info("Hello");
                })
                .build();
    }

    @Override
    public long creatorId() {
        return 1L;
    }
}