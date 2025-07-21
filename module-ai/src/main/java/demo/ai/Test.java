package demo.ai;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;

import java.util.Optional;

public class Test {

    static String apiKey = Optional.ofNullable(System.getenv("GIGACHAT_API_KEY"))
            .orElseThrow(() -> new IllegalStateException("GIGACHAT_API_KEY env var is not defined"));

    public static void main(String[] args) {
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
                        .content("Сколько будет два плюс два?")
                        .role(ChatMessage.Role.USER)
                        .build())
                .build()));
    }
}