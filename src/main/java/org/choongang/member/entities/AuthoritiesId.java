package org.choongang.member.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.choongang.member.constants.Authority;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthoritiesId {
    private Member member;
    private Authority authority;
}
