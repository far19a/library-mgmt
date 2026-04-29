package com.library.borrowing.service;

import com.library.borrowing.model.Borrowing;
import com.library.borrowing.model.MemberReference;
import com.library.borrowing.repository.BorrowingRepository;
import com.library.borrowing.repository.MemberReferenceRepository;
import com.library.borrowing.web.MemberBorrowingStatistic;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingDomainService {

    private final BorrowingRepository borrowingRepository;
    private final MemberReferenceRepository memberReferenceRepository;

    public BorrowingDomainService(BorrowingRepository borrowingRepository,
                                  MemberReferenceRepository memberReferenceRepository) {
        this.borrowingRepository = borrowingRepository;
        this.memberReferenceRepository = memberReferenceRepository;
    }

    public Borrowing checkout(Long memberId, Long bookId, LocalDate dueDate) {
        Borrowing borrowing = new Borrowing();
        borrowing.setMemberId(memberId);
        borrowing.setBookId(bookId);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(dueDate);
        borrowing.setFineAmount(BigDecimal.ZERO);
        return borrowingRepository.save(borrowing);
    }

    public Optional<Borrowing> returnBook(Long borrowingId, LocalDate returnDate) {
        return borrowingRepository.findById(borrowingId)
            .map(borrowing -> {
                borrowing.setActualReturnDate(returnDate);
                long overdueDays = Math.max(0, ChronoUnit.DAYS.between(borrowing.getDueDate(), returnDate));
                borrowing.setFineAmount(BigDecimal.valueOf(overdueDays).multiply(BigDecimal.valueOf(1.5)));
                return borrowingRepository.save(borrowing);
            });
    }

    public List<Borrowing> getBorrowingsFrom(LocalDate startDate) {
        return borrowingRepository.findByBorrowDateGreaterThanEqual(startDate);
    }

    public MemberReference saveMemberReference(MemberReference memberReference) {
        return memberReferenceRepository.save(memberReference);
    }

    public List<MemberBorrowingStatistic> memberStats(LocalDate startDate, int minBorrows) {
        return borrowingRepository.getMemberBorrowingStatistics(startDate, minBorrows);
    }
}
