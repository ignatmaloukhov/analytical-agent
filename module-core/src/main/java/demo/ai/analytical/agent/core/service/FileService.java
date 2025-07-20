package demo.ai.analytical.agent.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.ai.analytical.agent.core.dto.AnswersDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static demo.ai.analytical.agent.core.common.Parameters.FOOTER_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileService {

    ObjectMapper objectMapper;

    @NonFinal
    @Value("${answers.path:}")
    String answersPath;

    public UUID saveJson(AnswersDto answersDto) {

        UUID answerId = UUID.randomUUID();
        String fileName = answerId + ".docx";
        String filePath = answersPath + fileName;

        try (XWPFDocument doc = new XWPFDocument()) {

            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc);
            XWPFFooter footer = policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
            XWPFParagraph footerParagraph = footer.createParagraph();
            XWPFRun footerRun = footerParagraph.createRun();

            footerRun.setText(FOOTER_MESSAGE);

            answersDto.getAnswers().forEach(answer -> {
                XWPFParagraph questionParagraph = doc.createParagraph();
                XWPFRun questionRun = questionParagraph.createRun();
                questionRun.setBold(true);
                questionRun.setText("Вопрос: " + answer.getQuestion());

                XWPFParagraph answerParagraph = doc.createParagraph();
                XWPFRun answerRun = answerParagraph.createRun();
                answerRun.setText("Ответ: " + answer.getAnswer());

                XWPFParagraph clauseParagraph = doc.createParagraph();
                XWPFRun clauseRun = clauseParagraph.createRun();
                clauseRun.setText("Пункт: " + answer.getClause());

                doc.createParagraph();
            });

            //todo создать папку answers, если она отсутствует

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                doc.write(out);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Сохранен файл: " + answersPath + fileName);
        return answerId;
    }

    public UUID saveText(String text) {

        UUID answerId = UUID.randomUUID();
        String fileName = answerId + ".docx";
        String filePath = answersPath + fileName;

        try (XWPFDocument doc = new XWPFDocument()) {

            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc);
            XWPFFooter footer = policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
            XWPFParagraph footerParagraph = footer.createParagraph();
            XWPFRun footerRun = footerParagraph.createRun();

            footerRun.setText(FOOTER_MESSAGE);

            XWPFParagraph questionParagraph = doc.createParagraph();
            XWPFRun questionRun = questionParagraph.createRun();
            questionRun.setText(text);

            doc.createParagraph();

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                doc.write(out);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Сохранен файл: " + answersPath + fileName);
        return answerId;
    }

    public FileSystemResource getAnswer(UUID answersId) {

        //todo add validation

        String fileName = answersId + ".docx";
        String filePath = answersPath + fileName;

        return new FileSystemResource(filePath);
    }


//    public void jsonToDocx(String jsonString) throws IOException {
//        // Парсим JSON строку в дерево JsonNode
//        JsonNode rootNode = objectMapper.readTree(jsonString);
//
//        // Создаём новый документ DOCX
//        try (XWPFDocument document = new XWPFDocument()) {
//
//            // Добавляем заголовок
//            XWPFParagraph title = document.createParagraph();
//            title.createRun().setText("Данные из JSON:");
//
//            // Рекурсивно добавляем содержимое JSON в документ
//            addJsonNodeToDoc(rootNode, document, 0);
//
//            String fileName = UUID.randomUUID() + ".docx";
//            String filePath = answersPath + fileName;
//
//            try (FileOutputStream out = new FileOutputStream(filePath)) {
//                document.write(out);
//                System.out.println("Документ успешно сохранён по пути: " + filePath);
//            } catch (IOException e) {
//                e.printStackTrace();
//                // Обработка ошибки сохранения файла
//            }
//        }
//    }
//
//    /**
//     * Рекурсивно добавляет содержимое JsonNode в документ
//     *
//     * @param node        текущий узел JSON
//     * @param document    документ DOCX
//     * @param indentLevel уровень вложенности для отступов
//     */
//    private void addJsonNodeToDoc(JsonNode node, XWPFDocument document, int indentLevel) {
//        if (node.isObject()) {
//            node.fieldNames().forEachRemaining(fieldName -> {
//                XWPFParagraph p = document.createParagraph();
//                p.setIndentationLeft(indentLevel * 300); // отступы для вложенности
//                //p.createRun().setBold(true).setText(fieldName + ":");
//
//                addJsonNodeToDoc(node.get(fieldName), document, indentLevel + 1);
//            });
//        } else if (node.isArray()) {
//            for (JsonNode item : node) {
//                addJsonNodeToDoc(item, document, indentLevel + 1);
//            }
//        } else {
//            // Примитивное значение (строка, число, boolean, null)
//            XWPFParagraph p = document.createParagraph();
//            p.setIndentationLeft(indentLevel * 300);
//            String text = node.isNull() ? "null" : node.asText();
//            p.createRun().setText(text);
//        }
//    }
//

}
