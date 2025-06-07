package demo.ai.analytical_agent.core.service;

import demo.ai.analytical_agent.core.dto.AttachmentDto;
import demo.ai.analytical_agent.core.dto.ContractContentDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContractContentGenerator implements AbstractContractContentGenerator {

    public Set<String> supportedExtensions() {
        return Set.of("doc", "docx");
    }

    public ContractContentDto prepareContent(List<AttachmentDto> attachments) {

        String requiredFileName = "контракт";

        Optional<AttachmentDto> attachmentOptional = attachments.stream()
                .filter(attachment -> supportedExtensions()
                        .contains(attachment.getFileExtension())
                        || requiredFileName.contains(attachment.getFileName().toLowerCase()))
                .findFirst();

        List<AttachmentDto> preparedAttachments = attachmentOptional.map(List::of)
                .orElse(Collections.emptyList());

        return ContractContentDto.builder()
                .attachments(preparedAttachments)
                .build();
    }

}
