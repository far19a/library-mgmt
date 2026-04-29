package com.library.borrowing.web;

import com.library.borrowing.model.Borrowing;
import com.library.borrowing.model.MemberReference;
import com.library.borrowing.service.BorrowingDomainService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {

    private final BorrowingDomainService borrowingDomainService;

    public BorrowingController(BorrowingDomainService borrowingDomainService) {
        this.borrowingDomainService = borrowingDomainService;
    }

    @PostMapping("/checkout")
    public Borrowing checkout(@RequestBody Map<String, String> request) {
        Long memberId = Long.parseLong(request.get("memberId"));
        Long bookId = Long.parseLong(request.get("bookId"));
        LocalDate dueDate = LocalDate.parse(request.get("dueDate"));
        return borrowingDomainService.checkout(memberId, bookId, dueDate);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Borrowing> returnBook(@PathVariable Long id,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        return borrowingDomainService.returnBook(id, returnDate)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Borrowing> getRecentBorrowings(@RequestParam(defaultValue = "30") long days) {
        return borrowingDomainService.getBorrowingsFrom(LocalDate.now().minusDays(days));
    }

    @PostMapping("/member-ref")
    public MemberReference upsertMemberRef(@RequestBody MemberReference memberReference) {
        return borrowingDomainService.saveMemberReference(memberReference);
    }

    @GetMapping("/stats/members")
    public List<MemberBorrowingStatistic> memberStatistics(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(defaultValue = "0") int minBorrows) {
        return borrowingDomainService.memberStats(startDate, minBorrows);
    }
}
