package com.library.reporting.service;

import com.library.reporting.client.BookClient;
import com.library.reporting.client.BorrowingClient;
import com.library.reporting.dto.BookDto;
import com.library.reporting.dto.BorrowingDto;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportGenerator {

    private final BorrowingClient borrowingClient;
    private final BookClient bookClient;

    public ReportGenerator(BorrowingClient borrowingClient, BookClient bookClient) {
        this.borrowingClient = borrowingClient;
        this.bookClient = bookClient;
    }

    public List<BookDto> topBooksLastMonth(int topN) {
        List<BorrowingDto> records = Optional.ofNullable(borrowingClient.getBorrowingsLastMonth())
            .orElseGet(List::of);
        Map<Long, Long> borrowCounts = records.stream()
            .collect(Collectors.groupingBy(BorrowingDto::getBookId, Collectors.counting()));
        Map<Long, BookDto> booksById = Optional.ofNullable(bookClient.getAllBooks())
            .orElseGet(List::of)
            .stream()
            .collect(Collectors.toMap(BookDto::getId, b -> b));

        return borrowCounts.entrySet().stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
            .limit(topN)
            .map(entry -> booksById.get(entry.getKey()))
            .filter(Objects::nonNull)
            .toList();
    }

    public Map<String, Object> borrowingOverview() {
        List<BorrowingDto> records = Optional.ofNullable(borrowingClient.getBorrowingsLastMonth())
            .orElseGet(List::of);

        double avgLoanDays = records.stream()
            .filter(r -> r.getActualReturnDate() != null)
            .mapToLong(r -> ChronoUnit.DAYS.between(r.getBorrowDate(), r.getActualReturnDate()))
            .average()
            .orElse(0.0);

        long overdueNow = records.stream()
            .filter(r -> r.getActualReturnDate() == null && r.getDueDate() != null && r.getDueDate().isBefore(java.time.LocalDate.now()))
            .count();

        Map<String, Long> byBookGenre = records.stream()
            .map(BorrowingDto::getBookId)
            .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        Map<String, Object> result = new HashMap<>();
        result.put("recordsLastMonth", records.size());
        result.put("avgLoanDays", avgLoanDays);
        result.put("activeOverdueCount", overdueNow);
        result.put("countsByBookId", byBookGenre);
        return result;
    }
}
