package com.cstore.domain.user.authentication;

import com.cstore.dao.user.UserDao;
import com.cstore.dao.user.address.UserAddressDao;
import com.cstore.dao.user.contact.UserContactDao;
import com.cstore.dto.UserAddressDto;
import com.cstore.model.user.*;
import com.cstore.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao userDao;
    private final UserAddressDao userAddressDao;
    private final UserContactDao userContactDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        User user = new User();

        user.setRole(Role.REG_CUST);
        user = userDao.saveUser(user);

        RegisteredUser registeredUser = RegisteredUser
            .builder()
            .userId(user.getUserId())
            .firstName(signUpRequest.getFirstName())
            .lastName(signUpRequest.getLastName())
            .email(signUpRequest.getEmail())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .enabled(true)
            .locked(false)
            .build();

        registeredUser = userDao.saveRegisteredUser(registeredUser);

        for (UserAddressDto userAddressDto : signUpRequest.getAddresses()) {
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

        for (Integer telephoneNumber : signUpRequest.getTelephoneNumbers()) {
            UserContactId userContactId = new UserContactId();

            userContactId.setUserId(user.getUserId());
            userContactId.setTelephoneNumber(telephoneNumber);

            userContactDao.save(new UserContact(userContactId, user));
        }

        String jwt = jwtService.generateToken(registeredUser);
        return JwtAuthenticationResponse
            .builder()
            .token(jwt)
            .build();
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword()
            )
        );

        RegisteredUser user = userDao
            .findRegUserByEmail(signInRequest.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email."));

        if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            String jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse
                .builder()
                .token(jwt)
                .build();
        } else {
            throw new IllegalArgumentException("Invalid password.");
        }
    }
}
