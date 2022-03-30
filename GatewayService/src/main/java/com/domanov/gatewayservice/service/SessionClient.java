package com.domanov.gatewayservice.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "session", url = "http://localhost:8084/api/v1")
public interface SessionClient {
}
