package org.choongang.file.services;

import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.repositories.FileInfoRepository;
import org.choongang.global.configs.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class) //파일 프로퍼티 설정
public class FileUploadService {

    private final FileInfoRepository fileInfoRepository;
    private final FileProperties properties;


    public List<FileInfo> upload(MultipartFile[] files, String gid, String location) {
        /**
         * 1. 파일 정보 저장
         * 2. 파일을 서버로 이동
         * 3. 이미지면 썸네일 생성
         * 4. 업로드한 파일목록 변환
         */

        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString(); //gid 없으면 기본값으로 넣어줌

        List<FileInfo> uploadFiles = new ArrayList<>(); //업로드 성공한 파일들 정보 저장할 리스트

        //1. 파일 정보 저장
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename(); //업로드한 파일의 원래 이름
            String contentType = file.getContentType(); //파일 형식
            String extension = fileName.substring(fileName.lastIndexOf("."));

            FileInfo fileInfo = FileInfo.builder()
                    .gid(gid)
                    .location(location)
                    .fileName(fileName)
                    .extension(extension)
                    .contentType(contentType)
                    .build();

            fileInfoRepository.saveAndFlush(fileInfo);

            //2. 파일 서버 이동
            long seq = fileInfo.getSeq();
            String uploadDir = properties.getPath() + "/" + (seq % 10L);
            File dir = new File(uploadDir);

            if (!dir.exists() || !dir.isDirectory()) {
                //dir이 없거나 디렉토리가 아니라면 새로운 디렉토리 생성
                dir.mkdirs();
            }

            String uploadPath = uploadDir + "/" + seq + extension;
            try {
                file.transferTo(new File(uploadPath));
                uploadFiles.add(fileInfo); //업로드 성공 파일 정보


            } catch (IOException e) {
                e.printStackTrace();
                //파일 이동 실패 시 정보 삭제
                fileInfoRepository.delete(fileInfo);
                fileInfoRepository.flush();
            }
        }

        return uploadFiles; //업로드 성공한 파일들 모아서 반환함
    }
}
