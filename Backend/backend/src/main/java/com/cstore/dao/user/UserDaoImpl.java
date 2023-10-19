package com.cstore.dao.user;

import com.cstore.model.user.RegisteredUser;
import com.cstore.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<RegisteredUser> findRegUserById(Long id) {
        try {
            String sql = "SELECT * " +
                         "FROM \"registered_user\" " +
                         "WHERE \"user_id\" = ?";

            RegisteredUser registeredUser = jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(RegisteredUser.class),
                id
            );
            return Optional.ofNullable(registeredUser);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RegisteredUser> findRegUserByEmail(String email) {
        try {
            String sql = "SELECT * " +
                         "FROM \"registered_user\" " +
                         "WHERE \"email\" = ?";

            RegisteredUser registeredUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(RegisteredUser.class), email);
            return Optional.ofNullable(registeredUser);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }

    @Override
    public User saveUser(User user) {
        String sql = "INSERT INTO \"user\" (\"role\") " +
                     "VALUES(?) " +
                     "RETURNING \"user_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getRole().toString());
                return ps;
            },
            keyHolder
        );

        Number generatedUserId = keyHolder.getKey();

        if (generatedUserId != null) {
            user.setUserId(generatedUserId.longValue());
        }

        return user;
    }

    @Override
    public RegisteredUser saveRegisteredUser(RegisteredUser registeredUser) {
        String sql = "INSERT INTO \"registered_user\" VALUES(?, ?, ?, ?, ?);";

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setLong(1, registeredUser.getUserId());
                ps.setString(2, registeredUser.getEmail());
                ps.setString(3, registeredUser.getPassword());
                ps.setString(4, registeredUser.getFirstName());
                ps.setString(5, registeredUser.getLastName());

                return ps;
            }
        );

        return registeredUser;
    }
}
