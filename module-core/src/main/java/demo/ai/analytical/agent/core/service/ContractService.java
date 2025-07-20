package demo.ai.analytical.agent.core.service;

import demo.ai.analytical.agent.core.dto.ContractDto;
import demo.ai.analytical.agent.core.integration.rest.client.EpzClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContractService {

    EpzClient epzClient;
    HtmlParserService htmlParserService;

    public List<ContractDto> getContractsByInn(String inn) {

        //todo add validation

        String searchContractsByInnUrl = "/epz/contract/search/results.html?contractStageList_0=on&contractStageList=0&selectedContractDataChanges=ANY&recordsPerPage=_100&supplierTitle=" + inn;
        Optional<InputStream> contentOptional = epzClient.getContentByUrl(searchContractsByInnUrl);

        List<ContractDto> contracts = contentOptional
                .map(content -> {
                    log.info("Content is present, parsing documents.");
                    return htmlParserService.parseContracts(content);
                })
                .orElseGet(() -> {
                    log.warn("Content is empty");
                    return Collections.emptyList();
                });

        return contracts;
    }


}
