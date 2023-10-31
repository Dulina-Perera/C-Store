package com.cstore.dao.user.token;

import com.cstore.model.user.Token;
import com.cstore.model.user.User;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface TokenDao {
    List<Token> findAllValidTokensByUserId(Long userId) throws DataAccessException;

    Optional<Token> findByContent(String content) throws DataAccessException;


    Token save(Token token) throws DataAccessException;

    void revokeAllTokens(User user) throws DataAccessException;

    Token update(Token token) throws DataAccessException;
}
