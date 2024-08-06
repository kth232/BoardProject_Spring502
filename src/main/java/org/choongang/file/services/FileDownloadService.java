package org.choongang.file.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.exceptions.FileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void download(Long seq) {
        FileInfo data = infoService.get(seq);

        String filePath = data.getFilePath(); //엔티티 파일 경로 바로 사용 가능
        //2차 가공을 하는 이유..

        String fileName = new String(data.getFileName().getBytes(), StandardCharsets.ISO_8859_1);
        //2바이트 형태로 바꿔야함, 글자 깨짐 방지<-한글은 3바이트

        File file = new File(filePath);

        if(!file.exists()) {
            throw new FileNotFoundException();
        }

        String contentType = data.getContentType();
        contentType = StringUtils.hasText(contentType) ? contentType : "application/octet-stream";

        try(FileInputStream fis = new FileInputStream(filePath)) {
            BufferedInputStream bis = new BufferedInputStream(fis);

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType(contentType);
            response.setIntHeader("Expires", 0); // 만료시간 X
            response.setHeader("Cache-Control", "must-revalidate");
            response.setContentLengthLong(file.length());

            OutputStream out = response.getOutputStream();
            out.write(bis.readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
