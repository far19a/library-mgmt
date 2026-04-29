package com.library.reporting.client;

import com.library.reporting.dto.BorrowingDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BorrowingClient {

    private final RestTemplate restTemplate;
    private final String borrowingServiceUrl;

    public BorrowingClient(RestTemplate restTemplate,
                           @Value("${services.borrowing.url}") String borrowingServiceUrl) {
        this.restTemplate = restTemplate;
        this.borrowingServiceUrl = borrowingServiceUrl;
    }

    public List<BorrowingDto> getBorrowingsLastMonth() {
        return restTemplate.exchange(
            borrowingServiceUrl + "/api/borrowings?days=30",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<BorrowingDto>>() {
            }
        ).getBody();
    }
}
