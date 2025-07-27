package demo.ai.analytical.agent.telegrambot.statemachine;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static demo.ai.analytical.agent.telegrambot.statemachine.BotState.*;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SimpleStateMachine {

    Map<Long, BotState> state;

    public BotState getState(Long chatId) {

        // todo add logs

        state.putIfAbsent(chatId, BotState.READY);
        return state.get(chatId);
    }

    public BotState setState(Long chatId, BotState state) {
        this.state.put(chatId, state);
        return state;
    }

    public BotState handleEvent(Long chatId, BotEvent event) {

        if (validate(chatId, event)) {
            return nextState(chatId, event);
        } else {
            return getState(chatId);
        }

    }

    boolean validate(Long chatId, BotEvent event) {

        //todo switch

        return true;
    }


    BotState nextState(Long chatId, BotEvent botEvent) {

        //todo add logs
        //todo add validation

        BotState nextState = switch (botEvent) {
            case RESIEVED_START_COMMAND_EVENT -> BotState.READY;
            case RESIEVED_STOP_COMMAND_EVENT -> BotState.READY;
            case RESIEVED_HELP_COMMAND_EVENT -> getState(chatId);
            case RESIEVED_INN_COMMAND_EVENT -> AWAITING_INN;
            case RESIEVED_CONTRACT_COMMAND_EVENT -> BotState.AWAITING_REG_NUMBER;

            case RESIEVED_INN_VALUE_EVENT -> DO_YOU_NEED_CONTRACT_ANALYSIS;
            case RESIEVED_REG_NUMBER_VALUE_EVENT -> DO_YOU_NEED_ADDITIONAL_INFO;

            case RESIEVED_NEED_CONTRACT_ANALYSIS_EVENT -> BotState.AWAITING_REG_NUMBER;
            case RESIEVED_NO_NEED_CONTRACT_ANALYSIS_EVENT -> BotState.READY;

            case RESIEVED_NEED_ADDITIONAL_INFO_EVENT -> BotState.AWAITING_ADDITIONAL_QUESTION;
            case RESIEVED_NO_NEED_ADDITIONAL_INFO_EVENT -> BotState.READY;

            case RESIEVED_QUESTION_VALUE_EVENT -> DO_YOU_NEED_ADDITIONAL_INFO;

        };

        state.put(chatId, nextState);
        log.info("Bot state: {}", nextState);

//        log.info("\u001B[31mBot state: {}\u001B[31m", nextState);


        return nextState;

    }


}
