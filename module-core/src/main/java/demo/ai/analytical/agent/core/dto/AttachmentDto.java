package demo.ai.analytical.agent.core.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentDto {

    //todo add validation

    String fileName;
    String fileExtension;
    String fileUrl;

}
