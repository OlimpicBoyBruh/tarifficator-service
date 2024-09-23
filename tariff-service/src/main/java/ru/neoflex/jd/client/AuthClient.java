package ru.neoflex.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "auth-service", url = "${integration.service.auth.base-url}")
public interface AuthClient {
    @GetMapping(value = "${integration.service.auth.method.valid}")
    Boolean validateToken(@RequestHeader("Authorization") String token);
}
