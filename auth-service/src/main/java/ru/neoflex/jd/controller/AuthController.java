package ru.neoflex.jd.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.jd.dto.JwtRequest;
import ru.neoflex.jd.util.JwtTokenUtil;
import ru.neoflex.jd.util.PasswordVerification;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsServiceImpl;
    private final PasswordVerification verification;

    @PostMapping("/auth")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
        verification.verify(jwtRequest.getPassword(), userDetails.getPassword());
        return ResponseEntity.ok(jwtTokenUtil.generateToken(userDetails));
    }

    @GetMapping("/auth/verify")
    public Boolean test(@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        log.info(token);
        return jwtTokenUtil.validateToken(token.substring(7));


    }
}
