package org.choongang.member.repositories;

import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.AuthoritiesId;
import org.choongang.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesId>, QuerydslPredicateExecutor<Authorities> {
    //기본키가 AuthoritiesId
    //권한별로 다양한 조건을 걸어서 조회하기 위해 QuerydslPredicateExecutor 상속
    List<Authorities> findByMember(Member member);
}
