package ru.specialist.DAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;



@Service("jpaCourseService")
@Repository
@Transactional
public class JPACourseDAO implements CourseDAO {
    private static final Log LOG  = LogFactory.getLog(JPACourseDAO.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public Course findById(int id) {
        return em.find(Course.class, id);
        /*
        TypedQuery<Course> query = em.createQuery("select c from Course c where c.id=:id", Course.class);
        query.setParameter("id", id);
        return query.getSingleResult();

         */
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        //return em.createQuery("select c from Course c", Course.class).getResultList();
        return em.createNamedQuery("Course.findAll", Course.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> findByTitle(String title) {
        TypedQuery<Course> query = em.createQuery("select c from Course c where c.title LIKE :title", Course.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }

    @Override
    public void insert(Course course) {
        em.persist(course);
        LOG.info("Course insert with id: " + course.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public void update(Course course) {
        if (course.getId() != 0 && em.find(Course.class, course.getId()) != null)
            em.merge(course);
        LOG.info("Course updated with id: " + course.getId());
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(Course.class, id));

        LOG.info("Course removed with id: " + id);
    }
}
