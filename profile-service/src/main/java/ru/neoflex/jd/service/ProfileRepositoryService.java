package ru.neoflex.jd.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.neoflex.jd.dto.ProfileDto;
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

    public Profile createProfile(ProfileDto profileDto, String application) {
        if (profileDto.getId() != null) {
            profileDto.setId(UUID.randomUUID());
        }
        profileDto.setPassword(passwordEncoder.encode(profileDto.getPassword()));
        validateProfileDto.validate(profileDto, application);
        log.info("Invoke ProfileRepositoryService, method createProfile, profileDto {}, application: {}",
                profileDto, application);

        Profile profile = profileRepository.save(profileMapper.toEntity(profileDto));

        log.info("Profile successfully created.");
        return profile;
    }

    public ProfileDto getProfileById(UUID id) {
        log.info("Invoke ProfileRepositoryService, method getProfileById, id: {}", id);
        ProfileDto profileDto = profileMapper.toDto(profileRepository.findById(id).orElseThrow());
        log.info("Profile successfully found.");
        return profileDto;
    }

    public List<ProfileDto> searchProfile(ProfileDto profileDto) {
        log.info("Invoke ProfileRepositoryService, method searchProfile, profileDto: {}", profileDto);
        List<ProfileDto> profiles = profileRepository.findAll(Example.of(profileMapper.toEntity(profileDto),
                ExampleMatcher.matching().withIgnoreNullValues())).stream().map(profileMapper::toDto).toList();
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

        UserInfoDto userInfoDto = new UserInfoDto(profile.getUsername(), profile.getPassword());
        log.info("User successfully found {}", userInfoDto);
        return userInfoDto;
    }


}
