package com.vijay.event_registration_system.repositories;

import com.vijay.event_registration_system.domain.User;
import com.vijay.event_registration_system.exception.EtAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.mindrot.jbcrypt.BCrypt;


import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO USERS(USER_ID,USERNAME,EMAIL,ROLE,PASSWORD) VALUES( NEXTVAL('USER_SEQ'),?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM USERS WHERE EMAIL=?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID,USERNAME,EMAIL,ROLE,PASSWORD "+ "FROM USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, USERNAME, PASSWORD , ROLE, EMAIL "+" FROM USERS WHERE EMAIL = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String username, String email, String role, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection ->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,username);
                ps.setString(2,email);
                ps.setString(3,role);
                ps.setString(4,hashedPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch(Exception e){
            throw new EtAuthException(("Invalid details, Failed to create account"));
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try{
            User user= jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(password,user.getPassword()))
                throw new EtAuthException("Invalid Email/Password"+user.getPassword());
            return user;
        } catch(EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid Email/ Password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId},userRowMapper);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
    return new User(rs.getInt("USER_ID"),
            rs.getString("USERNAME"),
            rs.getString("EMAIL"),
            rs.getString("PASSWORD"),
            rs.getString("ROLE"));
    });
}
