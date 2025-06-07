package demo.ai.analytical_agent.core.service;


import demo.ai.analytical_agent.core.dto.AttachmentDto;
import demo.ai.analytical_agent.core.dto.ContractDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static demo.ai.analytical_agent.core.util.CommonUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HtmlParserService {

    public List<ContractDto> parseContracts(InputStream content) {

        List<ContractDto> documents;

        try {
            String html = new String(content.readAllBytes(), StandardCharsets.UTF_8);
            Document htmlDocument = Jsoup.parse(html);

            //блок поле поиска
            Elements searchBlocks = htmlDocument.select("div.search-registry-entry-block");
            List<Element> searchBlocksList = searchBlocks.stream().toList();

            documents = searchBlocksList.stream().map(element -> {

                        ContractDto contractDto = new ContractDto();

                        //рег. номер
                        Element regNumber = element.select("a[target=_blank][href^=/epz/contract/contractCard/common-info.html]").first();
                        if (regNumber != null) {
                            contractDto.setRegNum(regNumber.text().substring(2));
                        }

                        //название Заказчика
                        Element customerName = element.select("a[href^=/epz/organization/view/info.html][target=_blank]").first();
                        if (customerName != null) {
                            contractDto.setCustomerName(customerName.text());
                        }

                        //номер Контракта
                        Element contractNumber = element.select("div.registry-entry__body-value").first();
                        if (contractNumber != null) {
                            contractDto.setContractNumber(contractNumber.text().substring(2));
                        }

                        //название Контракта
                        Elements nameBlocks = element.select("span.pl-0.col");
                        for (Element nameBlock : nameBlocks) {
                            Element contractName = nameBlock.selectFirst("> span");
                            if (contractName != null) {
                                contractDto.setContractName(contractName.text()
                                        .replace("&nbsp;", ""));
                            }
                        }

                        //статус Контракта
                        Element contractStatus = element.select("div.registry-entry__header-mid__title").first();
                        if (contractStatus != null) {
                            contractDto.setStatus(contractStatus.text());
                        }

                        //Цена Контракта
                        Element contractPrice = element.select("div.price-block__value").first();
                        if (contractPrice != null) {
                            contractDto.setContractPrice(contractPrice.text());
                        }

                        return contractDto;

                    })
                    .toList();

        } catch (IOException e) {
            return Collections.emptyList();
        }
        return documents;
    }

    public List<AttachmentDto> parseAttachments(InputStream content) {

        List<AttachmentDto> attachments;

        try {
            String html = new String(content.readAllBytes(), StandardCharsets.UTF_8);
            Document htmlDocument = Jsoup.parse(html);

            //блок Вложения
            Elements attachmentsBlocks = htmlDocument.select("span.section__value");
//            Elements attachmentsBlocks = htmlDocument.select("div.card-attachments__block");
            List<Element> attachmentsBlocksList = attachmentsBlocks.stream().toList();

            attachments = attachmentsBlocksList.stream().map(element -> {

                        AttachmentDto attachmentDto = new AttachmentDto();

                        //тег файл
                        Element rawFileName = element.select("div.clipText > span > a").first();
                        if (rawFileName != null) {
                            String fileFullName = cutFileSize(rawFileName.attr("title"));
                            String fileShortName = getFileName(fileFullName);
                            String fileExtension = getFileExtension(fileFullName);

                            attachmentDto.setFileName(fileShortName);
                            attachmentDto.setFileExtension(fileExtension);
                            attachmentDto.setFileUrl(rawFileName.attr("href"));
                        }
                        return attachmentDto;
                    })
                    .toList();

        } catch (IOException e) {
            return Collections.emptyList();
        }

        return attachments;
    }


}

