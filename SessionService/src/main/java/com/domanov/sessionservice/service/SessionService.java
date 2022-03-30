package com.domanov.sessionservice.service;

import com.domanov.sessionservice.dto.AuthResponse;
import com.domanov.sessionservice.dto.RegistrationRequest;
import com.domanov.sessionservice.dto.UserResponse;
import com.domanov.sessionservice.model.User;
import com.domanov.sessionservice.repository.UserRepository;
import com.domanov.sessionservice.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("SessionService")
public class SessionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AuthResponse getAuth(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setLogin(user.getLogin());
            userResponse.setName(user.getName());
            userResponse.setSurname(user.getSurname());
            userResponse.setRole(user.getRole().toString());
            userResponse.setUser_uid(user.getUser_uid());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwtTokenUtil.generateToken(userResponse));
            return authResponse;
        }
        return new AuthResponse();
    }

    public AuthResponse registration(RegistrationRequest registrationRequest) {
        registrationRequest.getLogin();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        boolean isPasswordMatches = passwordEncoder.matches(registrationRequest.getPassword(), hashedPassword);

        boolean isPasswordMatches2 = passwordEncoder.matches("12355uu", hashedPassword);
        return new AuthResponse();
    }
}
