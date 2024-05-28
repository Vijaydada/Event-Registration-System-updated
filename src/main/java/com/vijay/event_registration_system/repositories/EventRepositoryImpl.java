package com.vijay.event_registration_system.repositories;

import com.vijay.event_registration_system.domain.Event;
import com.vijay.event_registration_system.exception.EtBadRequestException;
import com.vijay.event_registration_system.exception.EtResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class EventRepositoryImpl implements EventRepository {

    private static final Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);

    private static final String SQL_FIND_BY_ID = "SELECT E.event_id, E.name, E.date, E.location, E.user_id " +
            "FROM EVENTS E WHERE E.event_id = ? AND E.user_id = ?";

    private static final String SQL_CREATE = "INSERT INTO EVENTS(event_id, name, date, location, user_id) " +
            "VALUES(NEXTVAL('event_seq'), ?, CURRENT_DATE, ?, ?)";

    private static final String SQL_FIND_ALL_BY_USER = "SELECT event_id, name, date, location, user_id " +
            "FROM EVENTS WHERE user_id = ?";

    private static final String SQL_UPDATE_EVENT = "UPDATE EVENTS SET name = ?, location = ? WHERE event_id = ? AND user_id = ?";

    private static final String SQL_DELETE_EVENT = "DELETE FROM EVENTS WHERE event_id = ? AND user_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Event> findAll(Integer userId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_BY_USER, new Object[]{userId}, eventRowMapper);
        } catch (Exception e) {
            logger.error("Failed to find events for user ID {}: {}", userId, e.getMessage());
            throw new EtResourceNotFoundException("Events not found for user");
        }
    }

    @Override
    public Event findById(Integer userId, Integer eventId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{eventId, userId}, eventRowMapper);
        } catch (Exception e) {
            logger.error("Failed to find event with ID {} for user ID {}: {}", eventId, userId, e.getMessage());
            throw new EtResourceNotFoundException("Event not found");
        }
    }

    @Override
    public Integer create(Integer userId, String name, String location) throws EtBadRequestException {
        try {
            logger.info("Creating event for user ID: {}, name: {}, location: {}", userId, name, location);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, location);
                ps.setInt(3, userId);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("event_id");
        } catch (Exception e) {
            logger.error("Failed to create event for user ID {}: {}", userId, e.getMessage());
            throw new EtBadRequestException("Invalid request to create event");
        }
    }

    @Override
    public void updateEvent(Integer userId, Integer eventId, Event event) throws EtResourceNotFoundException {
        try {
            int updatedRows = jdbcTemplate.update(SQL_UPDATE_EVENT, event.getName(), event.getLocation(), eventId, userId);
            if (updatedRows == 0) {
                throw new EtResourceNotFoundException("Event not found or user unauthorized to update event");
            }
        } catch (Exception e) {
            logger.error("Failed to update event with ID {} for user ID {}: {}", eventId, userId, e.getMessage());
            throw new EtResourceNotFoundException("Failed to update event");
        }
    }

    @Override
    public void removeById(Integer userId, Integer eventId) {
        try {
            int deletedRows = jdbcTemplate.update(SQL_DELETE_EVENT, eventId, userId);
            if (deletedRows == 0) {
                throw new EtResourceNotFoundException("Event not found or user unauthorized to delete event");
            }
        } catch (Exception e) {
            logger.error("Failed to delete event with ID {} for user ID {}: {}", eventId, userId, e.getMessage());
            throw new EtResourceNotFoundException("Failed to delete event");
        }
    }

    private RowMapper<Event> eventRowMapper = (rs, rowNum) -> new Event(
            rs.getInt("event_id"),
            rs.getString("name"),
            rs.getDate("date"),
            rs.getString("location"),
            rs.getInt("user_id")
    );
}
