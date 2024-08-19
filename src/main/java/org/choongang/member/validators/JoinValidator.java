package org.choongang.member.validators;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.global.validators.MobileValidator;
import org.choongang.global.validators.PasswordValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component //=기본 스캔 대상
@RequiredArgsConstructor
public class JoinValidator implements Validator, PasswordValidator, MobileValidator {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //기본 검증 후 추가 검증 수행
        if(errors.hasErrors()) { //이미 기본 검증에서 실패한 경우 추가, 검증 진행할 필요 없이 바로 리턴
            return;
        }

        /**
         * 앞에서 리턴하기 때문에? 이미 값이 있다고 가정하고 추가 검증할 부분만 수행함
         * 1. 이미 가입된 회원인지 체크
         * 2. 비밀번호, 비밀번호 확인 일치 여부
         * 3. 비밀번호 복잡성 체크
         * 4. 전화번호 형식 체크
         */

        RequestJoin form = (RequestJoin) target;
        String email = form.getEmail();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        String mobile = form.getMobile();

        // 1. 이미 가입된 회원인지 체크
        if (memberRepository.exists(email)) {
            errors.rejectValue("email", "Duplicated");
        }


        //2. 비밀번호, 비밀번호 확인 일치 여부
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "Mismatch.password");
            //이 자체로 코드로 사용하기 때문에 연동 가능
        }

        //3. 비밀번호 복잡성 체크->알파벳 대소문자, 숫자, 특수문자 각 1개 이상 사용
        if (!alphaCheck(password, false) || !numberCheck(password) || !specialCharsCheck(password)) {
            errors.rejectValue("password", "Complexity");
            //이 자체로 코드로 사용하기 때문에 연동 가능
        }

        // 4. 전화번호 형식 체크
        if(!mobileCheck(mobile)) {
            errors.rejectValue("mobile", "Mobile");
        }
    }
}
