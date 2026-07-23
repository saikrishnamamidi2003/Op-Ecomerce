package com.alacriti.merchant.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    private final JdbcTemplate jdbcTemplate;

    public HealthService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getDatabaseVersion(){
        return jdbcTemplate.queryForObject(
                "select version()",
                String.class
        );
    }
}
