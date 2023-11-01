package com.cstore.dao.user.token;

import com.cstore.model.user.Token;
import com.cstore.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
    public Optional<Token> findByContent(
        String content
    ) throws DataAccessException {
        String sql = "SELECT * " +
                     "FROM \"token\" " +
                     "WHERE \"content\" = ?;";

        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Token.class),
                    content
                )
            );
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    @Override
    public Token save(
        Token token
    ) throws DataAccessException {
        String sql = "INSERT INTO \"token\" " +
                     "VALUES (?, ?, ?, ?);";

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);

                ps.setLong(1, token.getUserId());
                ps.setString(2, token.getContent());
                ps.setBoolean(3, token.getExpired());
                ps.setBoolean(4, token.getRevoked());

                return ps;
            }
        );

        return token;
    }

    @Override
    public void revokeAllTokens(
        User user
    ) throws DataAccessException {
        String sql = "UPDATE \"token\" " +
                     "SET \"revoked\" = true " +
                     "WHERE \"user_id\" = ?;";

        jdbcTemplate.update(
            sql,
            ps -> ps.setLong(1, user.getUserId())
        );
    }

    @Override
    public Token update(
        Token token
    ) throws DataAccessException {
        String sql = "UPDATE \"token\" " +
                     "SET \"expired\" = ?, \"revoked\" = ? " +
                     "WHERE \"user_id\" = ?;";

        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);

                ps.setBoolean(1, token.getExpired());
                ps.setBoolean(2, token.getRevoked());
                ps.setLong(3, token.getUserId());

                return ps;
            }
        );

        return token;
    }
}
