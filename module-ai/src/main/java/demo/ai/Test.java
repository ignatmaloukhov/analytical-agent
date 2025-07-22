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
        embeddingQuery("Сколько будет два плюс два?");
        System.out.println("finish");

    }
}