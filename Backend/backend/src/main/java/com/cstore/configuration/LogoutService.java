package com.cstore.configuration;

import com.cstore.dao.user.token.TokenDao;
import com.cstore.model.user.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {
    private final TokenDao tokenDao;

    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws DataAccessException {
        final String authHeader = request.getHeader("Authorization");
        final String content;

        if (
            StringUtils.isEmpty(authHeader) ||
                !StringUtils.startsWith(authHeader, "Bearer ")
        ) {
            return;
        } else {
            content = authHeader.substring(7);
            Token token = tokenDao.findByContent(content)
                .orElse(null);

            if (token != null) {
                token.setExpired(true);
                token.setRevoked(true);

                tokenDao.update(token);
            }
        }
    }
}
