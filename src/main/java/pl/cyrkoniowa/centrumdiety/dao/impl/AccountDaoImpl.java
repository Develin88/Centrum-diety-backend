package pl.cyrkoniowa.centrumdiety.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import pl.cyrkoniowa.centrumdiety.dao.AccountDao;
import pl.cyrkoniowa.centrumdiety.entity.Account;

@Repository
public class AccountDaoImpl implements AccountDao {
    private final EntityManager entityManager;

    public AccountDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public Account findByUserName(String theUserName) {
        //Pobranie użytkownika z bazy po nazwie na podstawie nazwy uzytkownika
        TypedQuery<Account> theQuery = entityManager.createQuery("from Account where userName=:uName and enabled=true ", Account.class);
        theQuery.setParameter("uName", theUserName);
        Account account = null;
        try {
            account = theQuery.getSingleResult();
        } catch (Exception e) {
        }

        return account;
    }

    @Override
    @Transactional
    public void save(Account account) {
        // Tworzenie usera
        entityManager.merge(account);
    }

}
