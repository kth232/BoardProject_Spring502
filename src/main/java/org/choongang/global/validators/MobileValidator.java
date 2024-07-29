package org.choongang.global.validators;

public interface MobileValidator {
    default boolean mobileCheck (String mobile) {
        /**
         * 01[016]-0000-/000-0000
         * 01[016]-d{3, 4}-d{4}
         * 010.1111.1111
         * 010-1111-1111
         * 010 1111 1111
         * 01011111111
         * 1. 숫자만 남긴다
         * 2. 패턴을 만든다
         * 3. 체크
         * ex) 01011111111111111->매칭됨
         * -> $를 붙여서 끝나는 패턴의 자리수가 4자리가 되도록 함
         * matches()는 처음부터 끝까지 체크하기 때문
         */

        mobile = mobile.replaceAll("\\D", "");
        String pattern = "01[016]\\d{3,4}\\d{4}$";
        //띄어쓰기 금지

        return mobile.matches(pattern);
    }
}
