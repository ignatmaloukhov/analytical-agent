package demo.ai.analytical.agent.telegrambot.service;

import demo.ai.analytical.agent.telegrambot.statemachine.SimpleStateMachine;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.MessageContext;
//import org.telegram.abilitybots.api.sender.SilentSender;
//import org.telegram.telegrambots.meta.api.objects.Message;

import static demo.ai.analytical.agent.telegrambot.statemachine.BotEvent.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalyticalBotResponseHandler {

    //SilentSender sender;
    SimpleStateMachine stateMachine;
    AnalyticalBotService analyticalBotService;
    MessageFormatterService messageFormatterService;


//    private final SilentSender sender;
//    private final Map<Long, UserState> chatStates;
//
//    public ResponseHandler(SilentSender sender, DBContext db) {
//        this.sender = sender;
//        chatStates = db.getMap(Constants.CHAT_STATES);
//    }


    public void handleHelp(MessageContext context) {

        //todo add logs
        //todo add validation

        //sender.send(HELP.getMessage(), context.chatId());
        //todo add logs

        stateMachine.handleEvent(context.chatId(), RESIEVED_HELP_COMMAND_EVENT);
        //todo add logs

    }

    public void handleStart(MessageContext context) {

        //todo add logs
        //todo add validation

        //sender.send(START.getMessage(), context.chatId());
        //todo add logs

        stateMachine.handleEvent(context.chatId(), RESIEVED_START_COMMAND_EVENT);
        //todo add logs
    }

    public void handleInn(MessageContext context) {

        //todo add logs
        //todo add validation

        //sender.send(INN.getMessage(), context.chatId());
        //todo add logs

        stateMachine.handleEvent(context.chatId(), RESIEVED_INN_COMMAND_EVENT);
        //todo add logs

    }

    public void handleStop(MessageContext context) {

        //todo add logs
        //todo add validation

        //sender.send(STOP.getMessage(), context.chatId());
        //todo add logs

        stateMachine.handleEvent(context.chatId(), RESIEVED_STOP_COMMAND_EVENT);
        //todo add logs
    }

//    public void handleMessage(Message message) {
//
//        stateMachine.setState(message.getChatId(), BotState.AWAITING_INN);
//
//
//
//        //todo add logs
//        //todo add validation
//
//        if (stateMachine.getState(message.getChatId()).equals(BotState.AWAITING_INN)) {
//            replyToInn(message);
//
//            //todo add logs
//
//        }
//
//        if (stateMachine.getState(message.getChatId()).equals(BotState.DO_YOU_NEED_CONTRACT_SUMMARY)) {
//            replyToContractSummary(message);
//
//            //todo add logs
//
//        }
//
//        if (stateMachine.getState(message.getChatId()).equals(BotState.AWAITING_REG_NUMBER)) {
//            replyToRegNumber(message);
//
//            //todo add logs
//
//        }
//
//        if (stateMachine.getState(message.getChatId()).equals(BotState.DO_YOU_NEED_ADDITIONAL_REQUEST)) {
//            replyToAdditionalRequest(message);
//
//            //todo add logs
//
//        }
//
//
//    }
//
//
//    private void replyToInn(Message message) {
//
//        //todo add logs
//        //todo add validation
//
//        String inn = message.getText();
//        List<ContractDto> contracts = analyticalBotService.getContractsByInn(inn);
//
//        log.info(contracts.toString());
//        //sender.send(messageFormatterService.formatInn(inn, contracts), message.getChat().getId());
//
//        //todo add logs
//    }
//
//    private void replyToContractSummary(Message message) {
//    }
//
//    private void replyToRegNumber(Message message) {
//    }
//
//    private void replyToAdditionalRequest(Message message) {
//    }
//

}