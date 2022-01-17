package com.rumblekat.mreivew.repository;

import com.rumblekat.mreivew.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
