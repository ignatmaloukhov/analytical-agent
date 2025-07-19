package demo.ai.analytical.agent.core.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parameters {

    public static final String FOOTER_MESSAGE = "Документ создан: "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + ". " +
            "IT лидер проекта: Александр Зенин";
}
