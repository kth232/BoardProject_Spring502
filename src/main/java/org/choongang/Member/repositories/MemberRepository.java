package org.choongang.Member.repositories;

import org.choongang.Member.entities.Member;
import org.choongang.Member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>{
    //유무만 확인하기 때문에 boolean 타입

    @EntityGraph(attributePaths = "authorities") //권한 관련 모든 조회
    Optional<Member> findByEmail(String email); //즉시 로딩

    default boolean exists(String email){
        //이미 존재하는 메서드지만 가공해서 사용=메서드 오버로드
        QMember member = QMember.member;

        return exists(member.email.eq(email));
    }
}
