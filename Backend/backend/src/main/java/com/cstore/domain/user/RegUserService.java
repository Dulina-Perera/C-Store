package com.cstore.domain.user;

import com.cstore.dao.user.UserDao;
import com.cstore.dao.user.address.UserAddressDao;
import com.cstore.dao.user.contact.UserContactDao;
import com.cstore.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegUserService {
    private final UserAddressDao userAddressDao;
    private final UserContactDao userContactDao;
    private final UserDao userDao;

    public RegUser getAllUserDetails(
        Long userId
    ) {
        Optional<com.cstore.model.user.RegUser> regUserOptional = userDao.findRegUserById(userId);

        if (regUserOptional.isEmpty()) {
            throw new NoSuchUserException("No registered user with id " + userId + " found.");
        }

        RegUser regUser = RegUser
            .builder()
            .userId(userId)
            .firstName(regUserOptional.get().getFirstName())
            .lastName(regUserOptional.get().getLastName())
            .build();


        regUser.setTelephoneNumbers(userContactDao.findAllByUserId(userId));

        regUser.setAddresses(userAddressDao.findAllByUserId(userId));

        return regUser;
    }
}
