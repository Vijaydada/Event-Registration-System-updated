package com.vijay.event_registration_system.repositories;

import com.vijay.event_registration_system.domain.Registration;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RegistrationRepositoryImpl implements RegitrationRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_CREATE = "INSERT INTO REGISTRATION(register_id, event_id, user_id) " +
            "VALUES(NEXTVAL('regis_seq'), ?, ?)";

    private static final String SQL_FIND_BY_ID = "SELECT register_id, event_id, user_id " +
            "FROM REGISTRATION WHERE register_id = ?";

    private static final String SQL_FIND_ALL_BY_USER = "SELECT register_id, event_id, user_id " +
            "FROM REGISTRATION WHERE user_id = ?";

    private static final String SQL_REMOVE_BY_ID = "DELETE FROM REGISTRATION WHERE register_id = ?";
    private static final String SQL_REMOVE_BY_EVENT_ID = "DELETE FROM REGISTRATION WHERE event_id = ?";

    private RowMapper<Registration> registrationRowMapper = ((rs, rowNum) -> new Registration(
            rs.getInt("register_id"),
            rs.getInt("event_id"),
            rs.getInt("user_id")
    ));

    @Override
    public List<Registration> findAll(int userId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_BY_USER, new Object[]{userId}, registrationRowMapper);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Registrations not found for user");
        }
    }

    @Override
    public Registration findById(int registrationId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{registrationId}, registrationRowMapper);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Registration not found");
        }
    }

    @Override
    public Integer createRegistration(int eventId, int userId) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, eventId);
                ps.setInt(2, userId);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("register_id");
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request to create registration");
        }
    }

    @Override
    public void removeRegistrationById(int registerId) throws EtResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_REMOVE_BY_EVENT_ID, new Object[]{registerId});
        if (count == 0)
            throw new EtResourceNotFoundException("Registration not found");
    }

    @Override
    public void removeRegistrationByEventId(int eventId) throws EtResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_REMOVE_BY_ID, new Object[]{eventId});
        if (count == 0)
            throw new EtResourceNotFoundException("Registration not found");
    }

}
