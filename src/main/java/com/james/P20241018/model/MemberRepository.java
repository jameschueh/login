package com.james.P20241018.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByAccount(String account); // 用於查詢帳號
}
