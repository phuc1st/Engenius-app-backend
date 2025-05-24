package com.phuc.learn_service.repository.httpclient;

import com.phuc.learn_service.configuration.AuthenticationRequestInterceptor;
import com.phuc.learn_service.configuration.FeignConfiguration;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.dto.response.FileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "FILE-SERVICE",
        configuration = { AuthenticationRequestInterceptor.class, FeignConfiguration.class })
public interface FileClient {
    @PostMapping(value = "/file/media/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FileResponse> uploadMedia(@RequestPart("file") MultipartFile file);

    @DeleteMapping("/file/media/{fileName}")
    void deleteFile(@PathVariable("fileName") String fileName);
}
