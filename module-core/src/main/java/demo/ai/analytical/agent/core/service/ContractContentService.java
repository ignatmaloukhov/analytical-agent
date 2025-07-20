package demo.ai.analytical.agent.core.service;

import demo.ai.analytical.agent.core.dto.AttachmentDto;
import demo.ai.analytical.agent.core.dto.ContractContentDto;
import demo.ai.analytical.agent.core.integration.rest.client.EpzClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContractContentService {

    AttachmentService attachmentService;
    ContractContentGenerator contractContentGenerator;
    EpzClient epzClient;
    TextReaderService textReaderService;


    public Optional<String> extractContractText(String regNumber) {

        //todo add validation

        List<AttachmentDto> attachments = attachmentService.getAttachmentsByRegNum(regNumber);

        ContractContentDto contractContent = contractContentGenerator.prepareContent(attachments);

        String url = contractContent.getAttachments().getFirst().getFileUrl();
        Optional<InputStream> content = epzClient.getContentByUrl(url);

        Optional<String> text = content.map(con -> {
            if ("doc".equals(contractContent
                    .getAttachments()
                    .getFirst()
                    .getFileExtension())) {
                return textReaderService.readDoc(con);
            }

            if ("docx".equals(contractContent
                    .getAttachments()
                    .getFirst()
                    .getFileExtension())) {
                return textReaderService.readDocx(con);
            }

            return null;

        });

        return text;
    }
}
