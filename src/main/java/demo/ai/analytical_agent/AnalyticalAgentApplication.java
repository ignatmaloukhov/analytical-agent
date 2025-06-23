package demo.ai.analytical_agent;

import demo.ai.analytical_agent.core.dto.AnswerDto;
import demo.ai.analytical_agent.core.dto.AnswersDto;
import demo.ai.analytical_agent.core.service.FileService;
import demo.ai.analytical_agent.core.service.AttachmentService;
import demo.ai.analytical_agent.core.service.ContractContentService;
import demo.ai.analytical_agent.core.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class AnalyticalAgentApplication implements CommandLineRunner {

    @Autowired
    ContractService contractService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    ContractContentService contractContentService;

    @Autowired
    FileService fileService;

    public static void main(String[] args) {
        SpringApplication.run(AnalyticalAgentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        //List<AttachmentDto> att = attachmentService.getAttachmentsByRegNum("1666000863125000021");
        //contractContentService.extractContractText("1666000863125000021");

        AnswerDto answer1 = AnswerDto.builder()
                .question("Кто лучший IT лидер?")
                .answer("Александр Зенин")
                .clause("п. 1")
                .build();

        AnswerDto answer2 = AnswerDto.builder()
                .question("Кто лучший IT архитектор?")
                .answer("Александр Зенин")
                .clause("п. 2")
                .build();

        AnswerDto answer3 = AnswerDto.builder()
                .question("Кто лучший?")
                .answer("Александр Зенин")
                .clause("п. 3")
                .build();

        AnswersDto answers = AnswersDto.builder()
                .answers(List.of(answer1, answer2, answer3))
                .build();

        String json = """
                
                {
                  "answers": [
                    {
                      "question": "Кто лучший IT лидер?",
                      "answer": "Александр Зенин",
                      "clause": "п. 1"
                    },
                    {
                      "question": "Кто лучший IT архитектор?",
                      "answer": "Александр Зенин",
                      "clause": "п. 2"
                    },
                    {
                      "question": "Кто лучший?",
                      "answer": "Александр Зенин",
                      "clause": "п. 3"
                    }
                  ]
                }
                
                """;

        //System.out.println("Я сохранил ответы с id=" + fileService.saveJson(answers));

        UUID jsonId = fileService.saveJson(answers);

        UUID helloId = fileService.saveText("Привет");

        //System.out.println("Я записал привет в файл с id=" + helloId);
    }
}
