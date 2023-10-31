package com.cstore.domain.authentication;

import com.cstore.dao.user.UserDao;
import com.cstore.dao.user.address.UserAddressDao;
import com.cstore.dao.user.contact.UserContactDao;
import com.cstore.dao.user.token.TokenDao;
import com.cstore.dto.UserAddressDto;
import com.cstore.exception.NoSuchUserException;
import com.cstore.model.user.*;
import com.cstore.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao userDao;
    private final UserAddressDao userAddressDao;
    private final UserContactDao userContactDao;
    private final TokenDao tokenDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    private void persistContent(
        User user,
        String content
    ) throws DataAccessException {
        Token token = Token
            .builder()
            .userId(user.getUserId())
            .content(content)
            .revoked(false)
            .expired(false)
            .build();
        tokenDao.save(token);
    }

    public Jwt register(
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

        String content = jwtService.generateTokenWithoutClaims(regUser);
        persistContent(user, content);

        return Jwt
            .builder()
            .jwt(content)
            .build();
    }

    public Jwt authenticate(
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

        String content = jwtService.generateTokenWithoutClaims(regUser);
        tokenDao.revokeAllTokens(regUser.getUser());
        persistContent(regUser.getUser(), content);

        return Jwt
            .builder()
            .jwt(content)
            .build();
    }
}
