package com.cstore.dao.user.token;

import com.cstore.model.user.Token;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface TokenDao {
    List<Token> findAllValidTokensByUserId(Long userId) throws DataAccessException;

    Optional<Token> findByToken(String content) throws DataAccessException;
}
