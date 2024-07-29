package org.choongang.global.validators;

public interface PasswordValidator { //범용 검증 수행, 인터페이스로 정의하면 수정 간편함(필요할 때마다 추가)
    //예외 던지는 것은 필수 사항이 아님->boolean으로 간단하게 체크할 수도 있다
    //복잡성 가감
    /**
     * 대소문자 복잡성 체크
     * @param password
     * @param caseInsensitive -false: 대소문자 각각 1개 이상 포함 / trye: 대소문자 구분 x
     * @return
     */
    default boolean alphaCheck(String password, boolean caseInsensitive) {

        if (caseInsensitive) { //대소문자 구분없이 1개 이상(+) 알파벳 체크
            return password.matches(".*[a-zA-Z]+.*");
        }

        return password.matches(".*[a-z]+.*") && password.matches(".*[A-Z]+.*");
    }

    /**
     * 숫자 복잡성 체크
     * @param password
     * @return
     */
    default boolean numberCheck(String password) {
        return password.matches(".*\\d+.*");
        //\d+: 숫자 1개 이상/ .*로 감쌈: 해당 패턴 문자가 포함되도록 함
    }

    /**
     * 특수문자 복잡성 체크
     * @param password
     * @return
     */
    default boolean specialCharsCheck(String password) {
        //[]<-문자 클래스, 알파벳 대소문자, 숫자, 한글 배제(^)하면 ->특수문자
        String pattern = ".*[^0-9a-zA-Zㄱ-ㅎ가-힣]+.*";

        return password.matches(pattern);
    }

}
