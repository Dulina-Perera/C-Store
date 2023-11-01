package com.cstore.domain.authentication;

import com.cstore.dao.user.UserDao;
import com.cstore.dao.user.address.UserAddressDao;
import com.cstore.dao.user.contact.UserContactDao;
import com.cstore.dao.user.token.TokenDao;
import com.cstore.dto.UserAddressDto;
import com.cstore.exception.NoSuchUserException;
import com.cstore.model.user.*;
import com.cstore.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserDao userDao;
    private final UserAddressDao userAddressDao;
    private final UserContactDao userContactDao;
    private final TokenDao tokenDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    private final static String USER_NOT_FOUND_MSG = "User with user_id %s not found!";


    private static AuthenticationResponse buildToken(
        String accessToken,
        String refreshToken
    ) {
        return AuthenticationResponse
            .builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    private void persistContent(
        User user,
        String accessToken
    ) throws DataAccessException {
        Token token = Token
            .builder()
            .userId(user.getUserId())
            .content(accessToken)
            .revoked(false)
            .expired(false)
            .build();
        tokenDao.save(token);
    }


    public AuthenticationResponse register(
        RegistrationRequest request
    ) throws DataAccessException {
        User user = new User();

        user.setRole(Role.REG_CUST);
        user = userDao.saveUser(user);

        RegUser regUser = RegUser
            .builder()
            .user(user)
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .enabled(true)
            .locked(false)
            .build();

        regUser = userDao.saveRegUser(regUser);

        for (UserAddressDto userAddressDto : request.getAddresses()) {
            UserAddress userAddress = UserAddress
                .builder()
                .user(user)
                .streetNumber(userAddressDto.getStreetNumber())
                .streetName(userAddressDto.getStreetName())
                .city(userAddressDto.getCity())
                .zipcode(userAddressDto.getZipcode())
                .build();

            userAddressDao.save(userAddress);
        }

        for (String telephoneNumber : request.getTelephoneNumbers()) {
            UserContactId userContactId = UserContactId
                .builder()
                .userId(user.getUserId())
                .telephoneNumber(telephoneNumber)
                .build();

            userContactDao.save(new UserContact(userContactId, user));
        }

        String accessToken = jwtService.generateAccessTokenWithoutClaims(regUser);
        String refreshToken = jwtService.generateRefreshToken(regUser);
        persistContent(user, accessToken);

        return buildToken(accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(
        AuthenticationRequest request
    ) {
        Optional<RegUser> regUserOptional = userDao.findRegUserByEmail(request.getEmail());

        if (regUserOptional.isEmpty()) {
            throw new NoSuchUserException("User with email " + request.getEmail() + "not found.");
        }
        RegUser regUser = regUserOptional.get();

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                regUser.getUser().getUserId(),
                request.getPassword()
            )
        );

        String accessToken = jwtService.generateAccessTokenWithoutClaims(regUser);
        String refreshToken = jwtService.generateRefreshToken(regUser);
        tokenDao.revokeAllTokens(regUser.getUser());
        persistContent(regUser.getUser(), accessToken);

        return buildToken(accessToken, refreshToken);
    }

    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String subject;

        if (
            StringUtils.isEmpty(authHeader) ||
            !StringUtils.startsWith(authHeader, "Bearer ")
        ) {
            return;
        }
        refreshToken = authHeader.substring(7);
        log.debug("JWT: {}", refreshToken);

        subject = jwtService.extractSubject(refreshToken);

        if (
            StringUtils.isNotEmpty(subject)
        ) {
            RegUser regUser = userDao
                .findRegUserById(Long.parseLong(subject))
                .orElseThrow(() -> new UsernameNotFoundException(
                    String.format(USER_NOT_FOUND_MSG, Long.parseLong(subject))
                ));

            if (jwtService.isTokenValid(refreshToken, regUser)) {
                log.debug("User: {}", regUser);

                String accessToken = jwtService.generateAccessTokenWithoutClaims(regUser);
                tokenDao.revokeAllTokens(regUser.getUser());
                persistContent(regUser.getUser(), accessToken);

                AuthenticationResponse authenticationResponse = AuthenticationResponse
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

                new ObjectMapper()
                    .writeValue(
                        response.getOutputStream(),
                        authenticationResponse
                    );
            }
        }
    }
}
