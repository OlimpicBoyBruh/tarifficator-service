package ru.neoflex.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.neoflex.jd.dto.UserInfoDto;

@FeignClient(name = "profile-service", url = "${integration.service.profile.base-url}")
public interface ProfileClient {

    @GetMapping(value = "${integration.service.profile.method.get-user-info}")
    UserInfoDto getUserInfo(@PathVariable("username") String username);
}
