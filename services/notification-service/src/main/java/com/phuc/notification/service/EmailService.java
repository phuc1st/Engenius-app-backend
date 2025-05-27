package com.phuc.notification.service;

import com.phuc.notification.dto.request.EmailRequest;
import com.phuc.notification.dto.request.SendEmailRequest;
import com.phuc.notification.dto.request.Sender;
import com.phuc.notification.dto.response.EmailResponse;
import com.phuc.notification.exception.AppException;
import com.phuc.notification.exception.ErrorCode;
import com.phuc.notification.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    String apiKey = "key";

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("FFFF")
                        .email("phucnvt.22itb@vku.udn.vn")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
