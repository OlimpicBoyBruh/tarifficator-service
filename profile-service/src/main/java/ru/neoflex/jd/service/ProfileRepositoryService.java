package ru.neoflex.jd.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.neoflex.jd.dto.ProfileDtoRequest;
import ru.neoflex.jd.dto.ProfileDtoResponse;
import ru.neoflex.jd.dto.UserInfoDto;
import ru.neoflex.jd.entity.Profile;
import ru.neoflex.jd.mapping.ProfileMapper;
import ru.neoflex.jd.repository.ProfileRepository;
import ru.neoflex.jd.valid.ValidateProfileDto;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ProfileRepositoryService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final ValidateProfileDto validateProfileDto;
    private final PasswordEncoder passwordEncoder;

    public Profile createProfile(ProfileDtoRequest profileDtoRequest, String application) {
        if (profileDtoRequest.getId() == null) {
            profileDtoRequest.setId(UUID.randomUUID());
        }
        profileDtoRequest.setPassword(passwordEncoder.encode(profileDtoRequest.getPassword()));
        validateProfileDto.validate(profileDtoRequest, application);
        log.info("Invoke ProfileRepositoryService, method createProfile, profileDto {}, application: {}",
                profileDtoRequest, application);

        Profile profile = profileRepository.save(profileMapper.toEntity(profileDtoRequest));

        log.info("Profile successfully created.");
        return profile;
    }

    public ProfileDtoResponse getProfileById(UUID id) {
        log.info("Invoke ProfileRepositoryService, method getProfileById, id: {}", id);
        ProfileDtoResponse profileDtoResponse = profileMapper.toDto(profileRepository.findById(id).orElseThrow());
        log.info("Profile successfully found.");
        return profileDtoResponse;
    }

    public List<ProfileDtoRequest> searchProfile(ProfileDtoRequest profileDtoRequest) {
        log.info("Invoke ProfileRepositoryService, method searchProfile, profileDto: {}", profileDtoRequest);
        List<ProfileDtoRequest> profiles = profileRepository.findAll(Example.of(profileMapper.toEntity(profileDtoRequest),
                ExampleMatcher.matching().withIgnoreNullValues())).stream().map(profileMapper::toDtoRequest).toList();
        log.info("Profiles successfully found profiles: {}", profiles);
        return profiles;
    }

    public UserInfoDto getUserInfo(String username) {
        log.info("Invoke ProfileRepositoryService, method getUserInfo, username: {}", username);
        Profile profile = profileRepository.findByUsername(username);
        if (profile == null) {
            log.info("User not found.");
            throw new EntityNotFoundException();
        }

        UserInfoDto userInfoDto = new UserInfoDto(profile.getUsername(), profile.getPassword().getPassword());
        log.info("User successfully found {}", userInfoDto);
        return userInfoDto;
    }


}
