package org.choongang.file.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.global.entities.BaseMemberEntity;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo extends BaseMemberEntity { //간단한 회원 정보 필요함

    @Id
    @GeneratedValue
    private Long seq; //서버에 업로드될 파일 이름-seq.확장자

    @Column(length=45, nullable = false)
    private String gid = UUID.randomUUID().toString(); //그룹 아이디, 유니크 아이디 랜덤으로 생성

    @Column(length=45)
    private String location; //그룹 안에 세부 위치

    @Column(length=80, nullable = false)
    private String fileName; //파일명

    @Column(length=30) //확장자가 없는 경우도 있음
    private String extension; //파일 확장자

    @Column(length=80)
    private String contentType; //파일 형식

    private boolean done; //그룹 작업 완료 여부, ex) 글 작성 중 취소하는 경우

    @Transient
    private String fileUrl; //파일 접근 URL

    @Transient
    private String filePath; //파일 업로드 경로
}
