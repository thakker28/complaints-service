package com.eca.service;

import com.eca.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientServiceImpl implements WebClientService{

    private final WebClient.Builder webClientBuilder;

    @Value("${webClientTimeoutSeconds}")
    private Integer webClientTimeoutSeconds;
    /**
     * Method to execute http GET request at uri9
     *
     * @param uri
     * @param headers
     * @return
     */
    public <T> T get(String uri, Map<String, String> headers, Class<T> responseType) {

        log.info("WebClientServiceImpl.get(_,_) Entry ----->");
        WebClient.RequestHeadersSpec<?> headersSpec;
        headersSpec = webClientBuilder.build().get().uri(uri);
        log.info("WebClientServiceImpl.get(_,_) Exit ----->");
        return fetchResponse(headersSpec, headers, responseType);
    }

    /**
     * Method to add headers
     *
     * @param headers
     * @return
     */
    public void addHeaders(WebClient.RequestHeadersSpec<?> headersSpec, Map<String, String> headers) {
        for (Map.Entry<String, String> e : headers.entrySet()) {
            if (Objects.equals(e.getKey(), "host")) continue;
            headersSpec.header(e.getKey(), e.getValue());
        }
    }

    /**
     * this method returns the Response body as string and throws an exception in case of error httpStatus Codes
     *
     * @param responseSpec
     * @return
     */
    private <T> T responseSpec(WebClient.ResponseSpec responseSpec, Class<T> responseType) {
        return responseSpec
                .onStatus(FORBIDDEN::equals, clientResponse -> clientResponse.bodyToMono(ErrorResponse.class)
                        .flatMap(error -> Mono.error(new AccessDeniedException(error.getMessage()))))
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> clientResponse.bodyToMono(ErrorResponse.class).flatMap(
                        error -> Mono.error(new EntityNotFoundException(error.getMessage()))))
                .bodyToMono(responseType).block(Duration.ofSeconds(webClientTimeoutSeconds));
    }

    /**
     * this method returns the Response as string
     *
     * @param headersSpec
     * @param headers
     * @return
     */
    private <T> T fetchResponse(WebClient.RequestHeadersSpec<?> headersSpec, Map<String, String> headers, Class<T> responseType) {
        addHeaders(headersSpec, headers);
        return responseSpec(headersSpec.retrieve(), responseType);
    }
}
