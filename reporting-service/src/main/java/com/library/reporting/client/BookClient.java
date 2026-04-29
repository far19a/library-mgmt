package com.library.reporting.client;

import com.library.reporting.dto.BookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BookClient {

    private final RestTemplate restTemplate;
    private final String bookServiceUrl;

    public BookClient(RestTemplate restTemplate,
                      @Value("${services.book.url}") String bookServiceUrl) {
        this.restTemplate = restTemplate;
        this.bookServiceUrl = bookServiceUrl;
    }

    public List<BookDto> getAllBooks() {
        return restTemplate.exchange(
            bookServiceUrl + "/api/books",
            org.springframework.http.HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<BookDto>>() {
            }
        ).getBody();
    }
}
