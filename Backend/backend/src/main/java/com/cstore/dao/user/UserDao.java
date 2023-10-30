package com.cstore.dao.user;

import com.cstore.model.user.RegUser;
import com.cstore.model.user.RegisteredUser;
import com.cstore.model.user.User;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

public interface UserDao {
    Optional<User> findUserById(Long id) throws DataAccessException;

    Optional<RegUser> findRegUserById(Long id);

    Optional<User> findUserByEmail(String email) throws DataAccessException;

    Optional<RegUser> findRegUserByEmail(String email) throws DataAccessException;

    User saveUser(User user);

    RegUser saveRegUser(RegUser registeredUser);
}
