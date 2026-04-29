package com.library.borrowing.web;

public interface MemberBorrowingStatistic {
    Long getMemberId();
    String getMemberName();
    Long getBorrowCount();
    Long getActiveOverdueCount();
    Double getAvgLoanDays();
}
