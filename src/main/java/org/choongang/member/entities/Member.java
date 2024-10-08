package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.*;
import org.choongang.file.entities.FileInfo;
import org.choongang.global.entities.BaseEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity implements Serializable {

    @Id //필수 애노테이션, 없으면 오류 발생
    @GeneratedValue
    private Long seq; //자동 증감 번호, 기본키

    @Column(length=45, nullable = false)
    private String gid; //프로필

    @Column(length = 65, unique = true, nullable = false) //notNull 제약조건 o
    private String email;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 40, nullable = false)
    private String userName;

    @Column(length = 15, nullable = false)
    private String mobile;

    @ToString.Exclude //toString 배제
    @OneToMany(mappedBy = "member")
    private List<Authorities> authorities;

    @Transient
    private FileInfo profileImage;
}
