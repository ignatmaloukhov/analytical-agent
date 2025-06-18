package demo.ai.analytical_agent.core.controller;

import demo.ai.analytical_agent.core.dto.AnswersDto;
import demo.ai.analytical_agent.core.dto.ContractDto;
import demo.ai.analytical_agent.core.service.ContractContentService;
import demo.ai.analytical_agent.core.service.ContractService;
import demo.ai.analytical_agent.core.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MainController {

    ContractService contractService;
    ContractContentService contractContentService;
    FileService fileService;

    @GetMapping("contracts/inn/{inn}")
    public ResponseEntity<List<ContractDto>> getContractsByInn(@PathVariable("inn") String inn) {

        //todo add validation

        return ResponseEntity.ok(contractService.getContractsByInn(inn));
    }

    @GetMapping("contract/reg.number/{regNumber}")
    public ResponseEntity<String> getContractText(@PathVariable("regNumber") String regNumber) throws IOException {

        //todo add validation

        Optional<String> contentOptional = contractContentService.extractContractText(regNumber);

        return contentOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("answer/save/json")
    public ResponseEntity<UUID> saveAnswer(@RequestBody AnswersDto answers) {

        //todo add validation

        return ResponseEntity.ok(fileService.saveJson(answers));
    }

    @PostMapping("answer/save/text")
    public ResponseEntity<UUID> saveText(@RequestBody String text) {

        //todo add validation

        return ResponseEntity.ok(fileService.saveText(text));
    }

    @GetMapping("answer/{answerId}")
    public ResponseEntity<FileSystemResource> getAnswer(@PathVariable UUID answerId) {

        //todo add validation

        FileSystemResource resource = fileService.getAnswer(answerId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/msword");
        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}