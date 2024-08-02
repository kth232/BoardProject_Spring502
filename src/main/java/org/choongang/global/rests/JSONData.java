package org.choongang.global.rests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull; //lombok 패키지로 추가
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@RequiredArgsConstructor //기본 생성자
public class JSONData { //응답할 형식을 지정함
    private HttpStatus status = HttpStatus.OK;
    private boolean success =true;
    private Object message; //전부 수용할 수 있도록 object 형으로 수정<-일반 예외는 string으로, 커맨드 객체는 map형태로 가공되서 넘어감

    @NonNull //교체 가능하도록 설정
    private Object data; //생성자 매개변수로 추가
    //서비스 입장에서는 계속 값이 달라지는 것은 안좋음->데이터 처리 번거로움
}