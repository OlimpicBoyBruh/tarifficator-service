package ru.neoflex.jd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.jd.dto.ProfileDtoRequest;
import ru.neoflex.jd.dto.ProfileDtoResponse;
import ru.neoflex.jd.dto.UserInfoDto;
import ru.neoflex.jd.service.ProfileRepositoryService;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController implements ProfileControllerSwagger {
    private final ProfileRepositoryService profileRepositoryService;

    @PostMapping("/create")
    public void createProfile(@RequestHeader("x-Source") String application,
                              @Valid @RequestBody ProfileDtoRequest profileDtoRequest) {
        log.info("Invoke createProfile method. profileDto: {}, application: {}", profileDtoRequest, application);
        profileRepositoryService.createProfile(profileDtoRequest, application);
        log.info("Method createProfile successfully finished.");
    }

    @GetMapping("/get/{profileId}")
    public ProfileDtoResponse getProfileById(@PathVariable("profileId") String accountId) {
        log.info("Invoke getProfileById method. profileId: {}", accountId);
        ProfileDtoResponse profileDtoResponse = profileRepositoryService.getProfileById(UUID.fromString(accountId));
        log.info("Method getProfileById return profileDto: {}", profileDtoResponse);
        return profileDtoResponse;
    }


    @GetMapping("/search")
    public List<ProfileDtoRequest> searchProfile(@ModelAttribute ProfileDtoRequest profileDtoRequest) {
        log.info("Invoke searchProfile method. profileDto: {}", profileDtoRequest);
        List<ProfileDtoRequest> profileDtoRequestList = profileRepositoryService.searchProfile(profileDtoRequest);
        log.info("Method searchProfile return find profileDtoList: {}", profileDtoRequestList);
        return profileDtoRequestList;
    }

    @GetMapping("/user-info/{username}")
    public UserInfoDto getUserInfo(@PathVariable("username") String username) {
        log.info("Invoke getUserInfo method. username: {}", username);
        UserInfoDto userInfoDto = profileRepositoryService.getUserInfo(username);
        log.info("Method getUserInfo return userInfoDto: {}", userInfoDto);
        return userInfoDto;
    }
}
