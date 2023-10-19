package com.cstore.dao.user;

import com.cstore.model.user.RegisteredUser;
import com.cstore.model.user.User;

import java.util.Optional;

public interface UserDao {
    Optional<RegisteredUser> findRegUserById(Long id);

    Optional<RegisteredUser> findRegUserByEmail(String email);

    User saveUser(User user);

    RegisteredUser saveRegisteredUser(RegisteredUser registeredUser);
}
