package com.cstore.dao.user.address;

import com.cstore.domain.user.Address;
import com.cstore.model.user.UserAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserAddressDaoImpl implements UserAddressDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Address> findAllByUserId(
        Long userId
    ) {
        String sql = "SELECT * " +
                     "FROM \"user_address\" " +
                     "WHERE \"user_id\" = ?;";

        return jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, userId),
            new BeanPropertyRowMapper<>(Address.class)
        );
    }

    @Override
    public UserAddress save(UserAddress userAddress) {
        String sql = "INSERT INTO \"user_address\" (\"user_id\", \"street_number\", \"street_name\", \"city\", \"zipcode\") VALUES(?, ?, ?, ?, ?) RETURNING \"address_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setLong(1, userAddress.getUser().getUserId());
                ps.setString(2, userAddress.getStreetNumber());
                ps.setString(3, userAddress.getStreetName());
                ps.setString(4, userAddress.getCity());
                ps.setInt(5, userAddress.getZipcode());

                return ps;
            },
            keyHolder
        );

        Number generatedAddressId = keyHolder.getKey();

        if (generatedAddressId != null) {
            userAddress.setAddressId(generatedAddressId.longValue());
        }

        return userAddress;
    }
}
