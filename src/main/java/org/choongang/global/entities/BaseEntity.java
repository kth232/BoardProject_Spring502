package org.choongang.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false) //처음 입력 이후 값 바뀌지 않음
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false) //처음 입력할 때는 값이 들어가지 않음, 수정 시 값 들어감
    private LocalDateTime modifiedAt;

    @Column(insertable = false) //처음 입력할 때는 값이 들어가지 않음, 수정 시 값 들어감
    private LocalDateTime deletedAt;

    //삭제 표시만 하고 일정 시간이 지나면 일괄 삭제되도록 하는 것이 안전하다=소프트 삭제
    //정보는 언제 다시 필요할지 모르고 작은 것 하나라도 중요하기 때문
}
