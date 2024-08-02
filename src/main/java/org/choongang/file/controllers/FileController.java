package org.choongang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.services.FileUploadService;
import org.choongang.global.exceptions.RestExceptionProcessor;
import org.choongang.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController //주로 JS를 통해 처리
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController implements RestExceptionProcessor { //JSON 형태 오류 확인

    private final FileUploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<JSONData> upload(@RequestPart("file") MultipartFile[] files,
                           @RequestParam(name = "gid", required = false) String gid,
                           @RequestParam(name = "location", required = false) String location) {
    //multipart 중요한 개념이니 꼭 공부하기!

        List<FileInfo> items = uploadService.upload(files, gid, location);
        HttpStatus status = HttpStatus.CREATED;
        JSONData data = new JSONData(items);
        data.setStatus(status);


        return ResponseEntity.status(status).body(data);
    }
}
