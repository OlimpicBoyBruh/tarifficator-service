package ru.neoflex.jd.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.client.ProfileClient;
import ru.neoflex.jd.dto.UserInfoDto;
import ru.neoflex.jd.model.UserDetailsImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ProfileClient profileClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserInfoDto userInfoDto;
        try {
            userInfoDto = profileClient.getUserInfo(username);
            log.info("User found success {}", userInfoDto.getUsername());
        } catch (FeignException exception) {
            throw new UsernameNotFoundException("Error fetching user info for username: " + username);
        }

        return new UserDetailsImpl(userInfoDto);
    }
}
