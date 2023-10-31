package com.cstore.dao.user.token;

import com.cstore.model.user.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenDaoImpl implements TokenDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Token> findAllValidTokensByUserId(
        Long userId
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"token\" " +
                     "WHERE (\"user_id\", \"expired\", \"revoked\") = (?, false, false);";

        return jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, userId),
            new BeanPropertyRowMapper<>(Token.class)
        );
    }

    @Override
    public Optional<Token> findByToken(
        String content
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"token\" " +
                     "WHERE \"content\" = ?;";

        return Optional.ofNullable(
            jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Token.class),
                content
            )
        );
    }
}
