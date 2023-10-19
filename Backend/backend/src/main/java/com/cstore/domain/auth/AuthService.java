package com.cstore.domain.auth;

import com.cstore.dao.user.UserDao;
import com.cstore.dao.user.address.UserAddressDao;
import com.cstore.dao.user.contact.UserContactDao;
import com.cstore.dto.UserAddressDto;
import com.cstore.exception.NoSuchUserException;
import com.cstore.model.user.*;
import com.cstore.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final UserAddressDao userAddressDao;
    private final UserContactDao userContactDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Jwt register(RegisterRequest request) {
        User user = new User();

        user.setRole(Role.REG_CUST);
        user = userDao.saveUser(user);

        RegisteredUser regUser = RegisteredUser
            .builder()
            .userId(user.getUserId())
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

            userAddress.setUser(user);
            userAddressDao.save(userAddress);
        }

        for (Integer telephoneNumber : request.getTelephoneNumbers()) {
            UserContactId userContactId = UserContactId
                .builder()
                .userId(user.getUserId())
                .telephoneNumber(telephoneNumber)
                .build();

            userContactDao.save(new UserContact(userContactId, user));
        }

        String jwt = jwtService.buildToken(regUser);
        return Jwt
            .builder()
            .token(jwt)
            .build();
    }

    public Jwt authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        RegisteredUser regUser = userDao
            .findRegUserByEmail(request.getEmail())
            .orElseThrow(() -> new NoSuchUserException("User with email " + request.getEmail() + "not found."));

        String jwt = jwtService.buildToken(regUser);
        return Jwt
            .builder()
            .token(jwt)
            .build();
    }
}
