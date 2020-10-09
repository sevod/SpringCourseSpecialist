package ru.specialist.DAO;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CourseRowMapper implements RowMapper<Course>{
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setLength(resultSet.getInt("length"));
        course.setTitle(resultSet.getString("title"));
        course.setDescription(resultSet.getString("description"));
        return course;
    }
}

public class JdbcCourseDAO implements CourseDAO {
    private static final String SQL_SELECT_COURSE =
            "SELECT id, title, length, description FROM courses";

    private static final String SQL_SELECT_COURSE_BY_ID =
            SQL_SELECT_COURSE + " WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Course findById(int id) {
         return (Course)getJdbcTemplate().queryForObject(SQL_SELECT_COURSE_BY_ID, new Object[]{id},
                 new CourseRowMapper());
    }

    public List<Course> findAll() {
        //manual map
        List<Course> courses = new ArrayList<Course>();

//        List<Map<String, Object>> rows =
//        getJdbcTemplate().queryForList(SQL_SELECT_COURSE);
//
//        for (Map<String, Object> row : rows) {
//            Course course = new Course();
//            course.setId((Integer)(row.get("id")));
//            course.setLength((Integer)(row.get("length")));
//            course.setTitle((String) (row.get("title")));
//            course.setDescription((String) (row.get("description")));
//            courses.add(course);
//        }
        //courses = getJdbcTemplate().query(SQL_SELECT_COURSE, new BeanPropertyRowMapper(Course.class));
        courses = getJdbcTemplate().query(SQL_SELECT_COURSE, new CourseRowMapper());
        return courses;
    }
}
