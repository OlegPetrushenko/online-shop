package ait.cohort63.online_shop.security;

import ait.cohort63.online_shop.security.service.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserDetailsService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, String> refreshStorage;

    public AuthService(UserDetailsService userService, TokenService tokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.refreshStorage = new HashMap<>();
    }
}
