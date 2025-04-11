package pl.cyrkoniowa.centrumdiety.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.cyrkoniowa.centrumdiety.dao.UserDao;
import pl.cyrkoniowa.centrumdiety.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public User findByUserName(String theUserName) {
        //pobranie użytkownika z bazy po nazwie na podstawie nazwy uzytkownika
        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName and enabled=true ", User.class);
        theQuery.setParameter("uName", theUserName);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
        }

        return theUser;
    }
}
