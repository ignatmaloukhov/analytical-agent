package demo.ai.analytical.agent.telegrambot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelegramBotCommand {

    START("start", "Starts the bot",
            "@analytical_agent_bot помогает анализировать контракты единой информационной системы " +
                    "в сфере закупок 44 ФЗ и 223 ФЗ zakupki.gov.ru\n\n" +
                    "Введите команду /inn и ИНН через пробел для поиска всех контрактов, заключенных с данным лицом\n\n" +
                    "Для получения справки введите команду /help"),

    HELP("help", "Outputs the bot's commands",
            "Введите команду /inn и ИНН через пробел для поиска всех контрактов, заключенных с данным лицом"),

    INN("inn", "Search for contracts by INN",
                 "ИНН");

    String name;
    String description;
    String message;

}