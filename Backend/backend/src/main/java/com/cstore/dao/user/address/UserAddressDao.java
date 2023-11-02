package com.cstore.dao.user.address;

import com.cstore.domain.user.Address;
import com.cstore.model.user.UserAddress;

import java.util.List;

public interface UserAddressDao {
    List<Address> findAllByUserId(Long userId);

    UserAddress save(UserAddress userAddress);
}
