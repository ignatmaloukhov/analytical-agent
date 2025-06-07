package demo.ai.analytical_agent.core.integration.rest.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EpzClient {

    RestClient restEpzClient;

    public Optional<InputStream> getContentByUrl(String request) {

        //todo add validation
        //todo add errors handling

        URI url = URI.create(request);

        byte[] content = restEpzClient.get()
                .uri(url)
                .retrieve()
                .body(byte[].class);

        if (content == null) {
            return Optional.empty();
        }
        return Optional.of(new ByteArrayInputStream(content));
    }

}
