package demo.ai.analytical.agent.telegrambot.service;

import demo.ai.analytical.agent.core.dto.ContractDto;
import demo.ai.analytical.agent.core.service.ContractService;
import demo.ai.analytical.agent.telegrambot.statemachine.BotEvent;
import demo.ai.analytical.agent.telegrambot.statemachine.SimpleStateMachine;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Locale;

import static demo.ai.analytical.agent.telegrambot.statemachine.BotState.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalyticalBotReplyProcessor {

    SimpleStateMachine stateMachine;
    AnalyticalBotReplyService replyService;
    ContractService contractService;
    MessageValidationService messageValidationService;

    public String replyOnStart(MessageContext context) {

        //todo add logs
        //todo add validation

        stateMachine.handleEvent(context.chatId(), BotEvent.RESIEVED_START_COMMAND_EVENT);
        return replyService.startMessage();
    }

    public String replyOnStop(MessageContext context) {

        //todo add logs
        //todo add validation

        stateMachine.handleEvent(context.chatId(), BotEvent.RESIEVED_STOP_COMMAND_EVENT);
        return replyService.stopMessage();
    }

    public String replyOnHelp(MessageContext context) {

        //todo add logs
        //todo add validation

        stateMachine.handleEvent(context.chatId(), BotEvent.RESIEVED_HELP_COMMAND_EVENT);
        return replyService.helpMessage();
    }

    public String replyOnInn(MessageContext context) {

        //todo add logs
        //todo add validation

        stateMachine.handleEvent(context.chatId(), BotEvent.RESIEVED_INN_COMMAND_EVENT);
        return replyService.innMessage();
    }

    public String replyOnContract(MessageContext context) {

        //todo add logs
        //todo add validation

        stateMachine.handleEvent(context.chatId(), BotEvent.RESIEVED_CONTRACT_COMMAND_EVENT);
        return replyService.regNumberMessage();
    }

    public List<String> replyOnMessage(Update update) {

        //todo add logs
        //todo add validation

        String message = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if (AWAITING_INN.equals(stateMachine.getState(chatId))) {
            if (messageValidationService.validateInn(message)) {
                stateMachine.handleEvent(chatId, BotEvent.RESIEVED_INN_VALUE_EVENT);
                return replayOnInnMessage(message);
            } else {
                return invalidInn(message);
            }
        }

        if (AWAITING_REG_NUMBER.equals(stateMachine.getState(chatId))) {
            stateMachine.handleEvent(chatId, BotEvent.RESIEVED_REG_NUMBER_VALUE_EVENT);
            return replayOnRegNumberMessage(message);
        }

        if (AWAITING_ADDITIONAL_QUESTION.equals(stateMachine.getState(chatId))) {
            stateMachine.handleEvent(chatId, BotEvent.RESIEVED_QUESTION_VALUE_EVENT);
            return replayOnAdditionalQuestionMessage(message);
        }

        if (DO_YOU_NEED_CONTRACT_ANALYSIS.equals(stateMachine.getState(chatId))) {

            if (isNoMessage(message)) {
                stateMachine.handleEvent(chatId, BotEvent.RESIEVED_NO_NEED_CONTRACT_ANALYSIS_EVENT);
                return replayOnNoMessage(message);
            } else {
                stateMachine.handleEvent(chatId, BotEvent.RESIEVED_REG_NUMBER_VALUE_EVENT);
                return replayOnRegNumberMessage(message);
            }
        }

        if (DO_YOU_NEED_ADDITIONAL_INFO.equals(stateMachine.getState(chatId))) {

            if (isNoMessage(message)) {
                stateMachine.handleEvent(chatId, BotEvent.RESIEVED_NO_NEED_ADDITIONAL_INFO_EVENT);
                return replayOnNoMessage(message);
            } else {
                stateMachine.handleEvent(chatId, BotEvent.RESIEVED_QUESTION_VALUE_EVENT);
                return replayOnQuestionMessage(message);
            }
        }

        return replayOnUndefinedMessage(message);
    }

    private List<String> replayOnInnMessage(String message) {
        //todo add validation

        List<ContractDto> contracts = contractService.getContractsByInn(message);

        StringBuilder listOfContracts = new StringBuilder("Для ИНН " + message + " найдено " + contracts.size() +
                " контрактов на стадии Исполнение:\n\n");

        for (ContractDto dto : contracts) {
            listOfContracts.append(StringUtils.abbreviate(dto.getContractName(), 100))
                    .append(" рег номер ").append(dto.getRegNum()).append("\n\n");
        }

        String additionalInfo = "Для анализа контракта введите его рег номер, " +
                "или напишите нет";
        return List.of(listOfContracts.toString(), additionalInfo);
    }

    private List<String> replayOnRegNumberMessage(String message) {
        //message = reg number
        List<String> gigaAnswer = replayOnQuestionMessage(message);
        return gigaAnswer;
    }

    private List<String> replayOnQuestionMessage(String message) {
        String gigaAnswer = "Когда меня подключат к giga я скачаю контракт с ЕИС Закупки " +
                "отправлю его в LLM, получу ответ и напишу его вам\n\n " +
                "А еще я запишу вопросы и ответы в файл и скину на него ссылку";
        String doYouNeedAdditionalInfoMessage = "Напишите вопрос по контракту либо нет " +
                "чтобы закончить анализ";
        return List.of(gigaAnswer, doYouNeedAdditionalInfoMessage);
    }

    private List<String> replayOnNeedContractAnalysisMessage(String message) {
        return List.of("Введите рег номер контракта");
    }

    private List<String> replayOnNeedAdditionalInfoMessage(String message) {
        return List.of("Введите вопрос по контракту ---> удалить этот шаг");
    }

    private List<String> replayOnAdditionalQuestionMessage(String message) {
        return List.of("Введите вопрос по контракту");
    }

    private List<String> replayOnNoMessage(String message) {
        return List.of("Анализ контракта закончен," +
                "для анализа контракта наберите команду /contract, " +
                "для поиска контрактов по ИНН наберите команду /inn");
    }

    private List<String> replayOnUndefinedMessage(String message) {
        return List.of("Ответ неожиданный, напишите другой");
    }

    private boolean isNoMessage(String message) {
        return List.of("нет", "н", "no", "n").contains(message.toLowerCase(Locale.ROOT));
    }

    private List<String> invalidInn(String message) {
        String invalidInn = "Invalid inn " + message;
        return List.of(invalidInn);
    }
}
