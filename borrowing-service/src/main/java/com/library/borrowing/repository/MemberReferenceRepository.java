package com.library.borrowing.repository;

import com.library.borrowing.model.MemberReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReferenceRepository extends JpaRepository<MemberReference, Long> {
}
