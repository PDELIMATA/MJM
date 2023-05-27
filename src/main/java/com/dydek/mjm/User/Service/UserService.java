package com.dydek.mjm.User.Service;

import org.springframework.stereotype.Service;

public interface UserService {
    void createNewUser(String username, String nonEncodedPassword);
}
