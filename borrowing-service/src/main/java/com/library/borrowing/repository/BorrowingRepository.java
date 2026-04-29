package com.library.borrowing.repository;

import com.library.borrowing.model.Borrowing;
import com.library.borrowing.web.MemberBorrowingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByBorrowDateGreaterThanEqual(LocalDate startDate);

    @Query(value = """
        SELECT m.id AS memberId, m.name AS memberName, COUNT(b.id) AS borrowCount,
               SUM(CASE WHEN b.actual_return_date IS NULL AND b.due_date < CURRENT_DATE THEN 1 ELSE 0 END) AS activeOverdueCount,
               AVG(b.actual_return_date - b.borrow_date) AS avgLoanDays
        FROM borrowing b
        JOIN member_ref m ON b.member_id = m.id
        WHERE b.borrow_date >= :startDate
        GROUP BY m.id, m.name
        HAVING COUNT(b.id) > :minBorrows
        ORDER BY borrowCount DESC
        """, nativeQuery = true)
    List<MemberBorrowingStatistic> getMemberBorrowingStatistics(@Param("startDate") LocalDate startDate,
                                                                @Param("minBorrows") int minBorrows);
}
