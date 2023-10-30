package com.cstore.dao.user.contact;

import com.cstore.model.user.UserContact;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserContactDaoImpl implements UserContactDao {
    private final JdbcTemplate jdbcTemplate;

    public UserContactDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserContact save(UserContact userContact) {
        String sql = "INSERT INTO \"user_contact\" (\"user_id\", \"telephone_number\") " +
                     "VALUES(?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setLong(1, userContact.getUserContactId().getUserId());
                ps.setString(2, userContact.getUserContactId().getTelephoneNumber());

                return ps;
            }
        );

        return userContact;
    }
}
