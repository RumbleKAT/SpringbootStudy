package com.rumblekat.springbootstudy.repository;

import com.rumblekat.springbootstudy.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {
}
