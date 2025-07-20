package demo.ai.analytical.agent.core.service;

import demo.ai.analytical.agent.core.dto.AttachmentDto;
import demo.ai.analytical.agent.core.integration.rest.client.EpzClient;
import lombok.*;
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
public class AttachmentService {

    EpzClient epzClient;
    HtmlParserService htmlParserService;

    public List<AttachmentDto> getAttachmentsByRegNum(String regNum){

        //todo add validation

        String searchContractsByInnUrl = "/epz/contract/contractCard/document-info.html?reestrNumber=" + regNum;
        Optional<InputStream> contentOptional = epzClient.getContentByUrl(searchContractsByInnUrl);

        List<AttachmentDto> attachments = contentOptional
                .map(content -> {
                    log.info("Content is present, parsing documents.");
                    return htmlParserService.parseAttachments(content);
                })
                .orElseGet(() -> {
                    log.warn("Content is empty");
                    return Collections.emptyList();
                });

        return attachments;

    }
}
