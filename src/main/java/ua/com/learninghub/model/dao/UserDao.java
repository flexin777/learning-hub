package ua.com.learninghub.model.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import ua.com.learninghub.model.entities.User;

import javax.persistence.*;
import java.util.List;

/**
 * Created by vasax32 on 15.07.14.
 */
public class UserDao {
    private static EntityManagerFactory entityManagerFactory = HibernateUtil.buildEntityManagerFactory();

    public List<User> selectAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT usr FROM User usr");
        List<User> students = query.getResultList();
        entityManager.close();
        return students;
    }

    public User selectById(int id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT usr FROM User usr WHERE usr.idUser = :u_id");
        query.setParameter("u_id",id);
        User user = (User)query.getSingleResult();
        entityManager.close();
        return user;
    }

    public int update(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User userUpd = (User) entityManager.find(User.class, user.getIdUser());

        if (userUpd == null) return 0;

        entityManager.getTransaction().begin();
        userUpd.setEmail(user.getEmail());
        userUpd.setLogin(user.getLogin());
        userUpd.setPass(user.getPass());
        userUpd.setMoney(user.getMoney());
        userUpd.setCategory(user.getCategory());
        entityManager.getTransaction().commit();
        entityManager.close();
        return 1;
    }

    public boolean insert(User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            return false;
        }
        finally{
            entityManager.close();
        }
        return true;
    }

    public User findByLoginPass(String login, String pass){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(
                "SELECT usr FROM User usr WHERE usr.login = :u_log AND usr.pass = :u_pass");
        query.setParameter("u_log", login);
        query.setParameter("u_pass", pass);
        User user = null;
        try {
            user = (User) query.getSingleResult();
            if(!user.getLogin().equals(login))
                return null;
        } catch (NoResultException e){
            return null;
        }
        finally {
            entityManager.close();
        }
        return user;
    }
}
