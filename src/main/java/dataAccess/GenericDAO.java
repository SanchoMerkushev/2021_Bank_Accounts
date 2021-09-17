package dataAccess;

import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

// realisation of Create, Read, Update, Delete methods
public class GenericDAO<T extends EntityWithId> {
    // type of genetic class
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")  // turn off warning of unchecked cast
    public GenericDAO() {
        this.persistentClass =
                (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T findById(long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(persistentClass, id);
    }

    public List<T> loadAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(persistentClass);
        criteria.from(persistentClass);
        List<T> data = session.createQuery(criteria).getResultList();
        session.close();
        return data;
    }

    public boolean save(T entity) {
        if (entity.receivePrimaryKey() >= 0) { return false; }
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean update(T entity) {
        if (findById(entity.receivePrimaryKey()) == null) { return false; }
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public void delete(T entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    public boolean deleteById(long id) {
        boolean result = false;
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Object persistentInstance = session.load(persistentClass, id);
        if (persistentInstance != null) {
            session.delete(persistentInstance);
            result = true;
        }
        try {
            session.getTransaction().commit();
        }catch (PersistenceException e){
            result = false;
        }
        session.close();
        return result;
    }
}
