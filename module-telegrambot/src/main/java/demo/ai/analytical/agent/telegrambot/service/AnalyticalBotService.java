package demo.ai.analytical.agent.telegrambot.service;

import demo.ai.analytical.agent.core.dto.ContractDto;
import demo.ai.analytical.agent.core.service.ContractService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalyticalBotService {

    ContractService contractService;

    public List<ContractDto> getContractsByInn(@NotEmpty String inn) {

        //todo add logs
        //todo add validation

        return contractService.getContractsByInn(inn);

    }
}
