package org.choongang.Member.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.Member.constants.Authority;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AuthoritiesId.class)
public class Authorities {

    @Id
    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩
    private Member member;

    @Id
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Authority authority;
}
