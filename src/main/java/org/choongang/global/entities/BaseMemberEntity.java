package org.choongang.global.entities;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMemberEntity extends BaseEntity implements Serializable { //baseEntity 상속받아서 사용
    //회원정보에 자동 추가되지 않는 기본 정보 공통 속성화
    //작업했던 사용자의 정보 저장할 때, 필요할 때만 사용, 항상 필요한 경우는 baseEntity 사용?
    //로그인한 사용자

    @CreatedBy //스프링 표준
    @Column(updatable = false) //수정 불가
    private String createdBy; //자동으로 추가됨
    //처음 로그인한 경우 이메일 정보 저장

    @LastModifiedBy
    @Column(insertable = false) //생성할 때 입력 x
    private String modifiedBy;

}
