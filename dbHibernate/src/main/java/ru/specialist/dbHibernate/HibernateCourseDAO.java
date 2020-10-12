package ru.specialist.dbHibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

@Transactional
@Repository("courseDao")
public class HibernateCourseDAO implements CourseDAO {
    private static final Log LOG = LogFactory.getLog(HibernateCourseDAO.class);

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly=true)
    public Course findById(int id) {
        return (Course) getSessionFactory().getCurrentSession().byId(Course.class).load(id);
    }

    @Transactional(readOnly=true)
    public List<Course> findAll() {
        return getSessionFactory().getCurrentSession().createQuery("from Course c").list();
    }
}
