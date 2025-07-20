package demo.ai.analytical.agent.telegrambot.service;

import demo.ai.analytical.agent.core.dto.ContractDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageFormatterService {

    public String noFormat(String input) {
        return input;
    }

    public String formatInn(String inn, List<ContractDto> contracts) {

        String message = "Для ИНН " + inn + " найдено " + contracts.size() + " контрактов на стадии Исполнение:\n\n";

        for (ContractDto contract : contracts) {
            String line = StringUtils.abbreviate(contract.getContractName(), 100) +
                    ", рег. номер контракта " + contract.getRegNum() + "\n\n";
            message = message + line;
        }

        return message;
    }
}
