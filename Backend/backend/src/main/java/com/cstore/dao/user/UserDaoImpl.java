package com.cstore.dao.user;

import com.cstore.model.user.RegUser;
import com.cstore.model.user.RegisteredUser;
import com.cstore.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
    private final JdbcTemplate templ;

    @Override
    public Optional<User> findUserById(Long id)
        throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"user\" " +
                     "WHERE \"user_id\" = ?";

        User user = templ.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(User.class),
            id
        );

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<RegUser> findRegUserById(Long id)
        throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"registered_user\" " +
                     "WHERE \"user_id\" = ?";

        try {
            RegUser regUser = templ.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(RegUser.class),
                id
            );

            if (regUser != null) {
                regUser.setUser(findUserById(id).get());
            }

            return Optional.ofNullable(regUser);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email)
        throws DataAccessException {
        String sql = "SELECT u.* " +
                     "FROM \"user\" AS u NATURAL RIGHT OUTER JOIN \"registered_user\" AS ru " +
                     "WHERE \"email\" = ?;";

        return Optional.ofNullable(templ.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(User.class),
            email
        ));
    }

    @Override
    public Optional<RegUser> findRegUserByEmail(
        String email
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"registered_user\" " +
                     "WHERE \"email\" = ?";

        try {
            RegUser regUser = templ.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(RegUser.class),
                    email
            );

            if (regUser != null) {
                regUser.setUser(findUserByEmail(regUser.getEmail()).get());
            }

            return Optional.ofNullable(regUser);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    @Override
    public User saveUser(User user) {
        String sql = "INSERT INTO \"user\" (\"role\") " +
                     "VALUES(?) " +
                     "RETURNING \"user_id\";";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        templ.update(
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
    public RegUser saveRegUser(
        RegUser regUser
    ) {
        String sql = "INSERT INTO \"registered_user\" " +
                     "VALUES(?, ?, ?, ?, ?, ?, ?);";

        templ.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setLong(1, regUser.getUser().getUserId());
                ps.setString(2, regUser.getEmail());
                ps.setString(3, regUser.getPassword());
                ps.setString(4, regUser.getFirstName());
                ps.setString(5, regUser.getLastName());
                ps.setBoolean(6, regUser.getLocked());
                ps.setBoolean(7, regUser.getEnabled());

                return ps;
            }
        );

        return regUser;
    }
}
