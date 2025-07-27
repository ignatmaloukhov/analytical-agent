package demo.ai.analytical.agent.telegrambot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalyticalBotReplyService {

    public String startMessage() {
        return "Введена команда start, тут будет написано сообщение для команды start";
    }

    public String stopMessage() {
        return "Введена команда stop, тут будет написано сообщение для команды stop";
    }

    public String helpMessage() {
        return "Введена команда help, тут будет написано сообщение для команды help";
    }

    public String innMessage() {
        return "Введите ИНН";
    }

    public String regNumberMessage() {
        return "Введена команда contract, введите рег номер контракта";
    }
}
