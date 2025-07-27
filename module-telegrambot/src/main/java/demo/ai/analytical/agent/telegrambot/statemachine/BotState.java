package demo.ai.analytical.agent.telegrambot.statemachine;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;

import static demo.ai.analytical.agent.telegrambot.statemachine.BotEvent.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum BotState {

    READY(List.of(
            RESIEVED_HELP_COMMAND_EVENT,
            RESIEVED_START_COMMAND_EVENT,
            RESIEVED_STOP_COMMAND_EVENT,
            RESIEVED_INN_COMMAND_EVENT,
            RESIEVED_CONTRACT_COMMAND_EVENT,
            RESIEVED_NO_NEED_CONTRACT_ANALYSIS_EVENT,
            RESIEVED_NO_NEED_ADDITIONAL_INFO_EVENT)),

    AWAITING_INN(List.of(
            RESIEVED_HELP_COMMAND_EVENT,
            RESIEVED_START_COMMAND_EVENT,
            RESIEVED_STOP_COMMAND_EVENT,
            RESIEVED_INN_COMMAND_EVENT)),

    AWAITING_REG_NUMBER(List.of(
            RESIEVED_HELP_COMMAND_EVENT,
            RESIEVED_START_COMMAND_EVENT,
            RESIEVED_STOP_COMMAND_EVENT,
            RESIEVED_INN_COMMAND_EVENT,
            RESIEVED_REG_NUMBER_VALUE_EVENT,
            RESIEVED_NEED_CONTRACT_ANALYSIS_EVENT)),

    AWAITING_ADDITIONAL_QUESTION(List.of(
            RESIEVED_HELP_COMMAND_EVENT,
            RESIEVED_START_COMMAND_EVENT,
            RESIEVED_STOP_COMMAND_EVENT,
            RESIEVED_INN_COMMAND_EVENT,
            RESIEVED_NEED_ADDITIONAL_INFO_EVENT)),

    DO_YOU_NEED_CONTRACT_ANALYSIS(List.of(
            RESIEVED_HELP_COMMAND_EVENT,
            RESIEVED_START_COMMAND_EVENT,
            RESIEVED_STOP_COMMAND_EVENT,
            RESIEVED_INN_COMMAND_EVENT)),

    DO_YOU_NEED_ADDITIONAL_INFO(List.of(
            RESIEVED_HELP_COMMAND_EVENT,
            RESIEVED_START_COMMAND_EVENT,
            RESIEVED_STOP_COMMAND_EVENT,
            RESIEVED_INN_COMMAND_EVENT,
            RESIEVED_QUESTION_VALUE_EVENT,
            RESIEVED_REG_NUMBER_VALUE_EVENT));

    //на этом шаге можно получить только указанные ивенты, другие не допустимы
    Collection<BotEvent> availableEvents;

    public boolean validate() {
        return true;
    }
}
