package demo.ai.analytical.agent.core.service;

import demo.ai.analytical.agent.core.dto.AttachmentDto;
import demo.ai.analytical.agent.core.dto.ContractContentDto;

import java.util.List;
import java.util.Set;

public interface AbstractContractContentGenerator {

    ContractContentDto prepareContent(List<AttachmentDto> attachments);

    Set<String> supportedExtensions();
}
