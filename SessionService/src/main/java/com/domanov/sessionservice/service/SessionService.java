package com.domanov.sessionservice.service;

import com.domanov.sessionservice.dto.UserResponse;
import com.domanov.sessionservice.model.User;
import com.domanov.sessionservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SessionService")
public class SessionService {

    @Autowired
    UserRepository userRepository;

    public UserResponse getUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        UserResponse userResponse = new UserResponse();
        userResponse.setLogin(user.getLogin());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setRole(user.getRole().toString());
        userResponse.setUser_uid(user.getUser_uid());
        userResponse.setTelephoneNumber(user.getTelephoneNumber());
        return userResponse;
    }
}
