package demo.ai.analytical.agent.core.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TextReaderService {

    public String readDoc(InputStream docInputStream) {
        try (HWPFDocument document = new HWPFDocument(docInputStream);
             WordExtractor extractor = new WordExtractor(document)) {

            return extractor.getText();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String readDocx(InputStream docInputStream) {
        try (XWPFDocument document = new XWPFDocument(docInputStream)) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            return paragraphs.stream()
                    .map(XWPFParagraph::getText)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

