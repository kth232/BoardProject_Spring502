package org.choongang.file.services.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.global.entities.BaseMemberEntity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseMemberEntity {
    //주로 관리자가 확인하는 용도로 사용

    @Id
    @Column(length = 30)
    private String bId;

    @Column(length = 60, nullable = false)
    private String bName;

}
