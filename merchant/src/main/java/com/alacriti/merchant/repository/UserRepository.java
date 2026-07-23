package com.alacriti.merchant.repository;

import com.alacriti.merchant.dto.UserRequest;
import com.alacriti.merchant.mapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alacriti.merchant.entity.User;


import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public int save(UserRequest request){
        String sql = """
                Insert INTO users(
                first_name,
                last_name,
                email,
                password,
                phone
                )
                VALUES(
                ?, ?, ?, ?, ?
                )
                """;
        return jdbcTemplate.update(
                sql,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone()
        );

    }

    public List<User> findAll(){
        String sql = """
                select *
                From users
                ORDER BY id
                """;
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public boolean existsByEmail(String email) {

        String sql = """
            SELECT COUNT(*)
            FROM users
            WHERE email = ?
            """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email
        );

        return count != null && count > 0;
    }

    public User findById(Long id) {

        String sql = """
            SELECT *
            FROM users
            WHERE id = ?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                new UserRowMapper(),
                id
        );
    }
}
