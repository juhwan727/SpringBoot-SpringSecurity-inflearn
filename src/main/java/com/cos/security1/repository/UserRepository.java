package com.cos.security1.repository;

import com.cos.security1.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 Ioc 됨. 이유는 JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<Member, Integer> {
    // findBy 규칙 -> Username문법
    // select * from member where username = 1?
    public Member findByUsername(String username);      // Jpa Query methods
}   
