package com.cstore.dao.user.contact;

import com.cstore.model.user.UserContact;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserContactDao {
    List<String> findAllByUserId(Long userId) throws DataAccessException;

    UserContact save(UserContact userContact);
}
