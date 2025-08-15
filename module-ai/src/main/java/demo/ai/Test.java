package demo.ai;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.TokenCountRequest;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.embedding.EmbeddingRequest;

import java.util.List;
import java.util.Optional;

public class Test {


    static String prompt = """
            Ты кредитный инспектор.
            Тебе будет передан текст контракта.
            Твоя задача анализировать каждый пункт контракта и собрать summary по конкретным вопросам по тексту контракта.
            Список вопросов:
            Кто заказчик контракта?
            Кто исполнитель контракта?
            Какова максимальная цена контракта?
            Какие меры ответственности предусмотрены за просрочку исполнения обязательств поставщиком?
            Какой процент от цены контракта составляет обеспечение контракта?
            Какие аетидемпенговые меры предусмотрены контрактом?
            Можно ли изменить существенные условия контракта после его заключения?
            Имеет ли поставщик право требовать от заказчика предоставление информации?
            Что происходит в случае просрочки исполнения обязательств заказчиком?
            В какие сроки заказчик оплачивает поставленный товар?
            Каковы особенности хранения и транспортировки товара?
            Какой предмет контракта?
            При ответе на вопросы ничего не добавляй от себя,
            каждый ответ должен содержать номер пункта контракта.
            Ответы предоставь в следующем формате:
            Вопрос: [текст вопроса]
            Ответ: [текст ответа]
            Номер пункта: [номер пункта контракта, в котором ты нашел ответ на вопрос]
            """;


    static String apiKey = Optional.ofNullable(System.getenv("GIGACHAT_API_KEY"))
            .orElseThrow(() -> new IllegalStateException("GIGACHAT_API_KEY env var is not defined"));


    static void simpleQuery(String query) {
        GigaChatClient client = GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(apiKey)
                                .build())
                        .build())
                .build();

        System.out.println(client.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT)
                .message(ChatMessage.builder()
                        .content(query)
                        .role(ChatMessage.Role.USER)
                        .build())
                .build()));
    }

    static void embeddingQuery(String query) {

        GigaChatClient client = GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(apiKey)
                                .build())
                        .build())
                .build();
        try {
            System.out.println(client.embeddings(EmbeddingRequest.builder()
                    .model("EmbeddingsGigaR")
                    .input(List.of(query))
                    .build()));
        } catch (HttpClientException ex) {
            System.out.println(ex.statusCode() + " " + ex.bodyAsString());
        }
    }

    static void tokens() {
        GigaChatClient client = GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(apiKey)
                                .build())
                        .build())
                .build();
        try {
            System.out.println(client.tokensCount(TokenCountRequest.builder()
                    .model(ModelName.GIGA_CHAT_MAX)
                    .input(List.of("Привет, как дела?", "Расскажи о себе"))
                    .build()));
        } catch (HttpClientException ex) {
            System.out.println(ex.statusCode() + " " + ex.bodyAsString());
        }
    }


    public static void main(String[] args) {

//        simpleQuery("Сколько будет два плюс два?");
//        embeddingQuery("Сколько будет два плюс два?");
        System.out.println(prompt.length() / 4);
        System.out.println("finish");

    }
}