package demo.ai.analytical_agent.core.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractDto {

    //todo add validation

    String regNum;
    String customerName;
    String contractName;
    String contractNumber;
    String contractPrice;
    String status;

}
