package demo.ai.analytical.agent.core.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractContentDto {

    //todo add validation

    List<AttachmentDto> attachments;
}
