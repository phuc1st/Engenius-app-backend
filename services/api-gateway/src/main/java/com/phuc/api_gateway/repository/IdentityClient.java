package com.phuc.api_gateway.repository;

import com.phuc.api_gateway.dto.response.ApiResponse;
import com.phuc.api_gateway.dto.response.IntrospectResponse;
import com.phuc.api_gateway.dto.request.IntrospectRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@HttpExchange("lb://IDENTITY-SERVICE") // Quan trọng!
public interface IdentityClient {
    @PostExchange(url = "/identity/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
