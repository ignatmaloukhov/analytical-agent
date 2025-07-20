package demo.ai;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;

public class Test {

    public static void main(String[] args) {
        GigaChatClient client = GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey("OWJmNGUwYjgtMTQ3Zi00NTk3LTgxYzEtY2U1NWE3MzNlNmFhOjE4ZTc0NmYzLWRhOGYtNDE4Mi1iMTc2LTIzMzUwMjAyYjA0OQ==")
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
