package demo.ai.analytical.agent.core.integration.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class EpzClientConfiguration {

    @Value("${integration.epz.url}")
    String epzUrl;

    @Bean
    public RestClient getEpzClient() {
        return RestClient.builder()
                .baseUrl(epzUrl)
                .build();
    }
}
