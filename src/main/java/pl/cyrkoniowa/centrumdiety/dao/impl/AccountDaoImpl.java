package pl.cyrkoniowa.centrumdiety.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import pl.cyrkoniowa.centrumdiety.dao.AccountDao;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.security.Roles;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {
    private final EntityManager entityManager;

    /**
     * Konstruktor klasy AccountDaoImpl.
     *
     * @param theEntityManager menedżer encji używany do operacji bazodanowych
     */
    public AccountDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej użytkownika z bazy danych na podstawie nazwy użytkownika.
     * Zwraca tylko aktywnych użytkowników (enabled=true).
     *
     * @param theUserName nazwa użytkownika do wyszukania
     * @return obiekt Account jeśli znaleziono, null w przypadku błędu lub braku wyników
     */
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

    /**
     * {@inheritDoc}
     * Implementacja metody zapisującej konto użytkownika w bazie danych.
     * Używa operacji merge do aktualizacji istniejącego lub utworzenia nowego konta.
     *
     * @param account obiekt konta użytkownika do zapisania
     */
    @Override
    @Transactional
    public void save(Account account) {
        // Tworzenie usera
        entityManager.merge(account);
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej wszystkich aktywnych użytkowników z rolą PACJENT.
     *
     * @return lista obiektów Account z rolą Pacjent
     */
    @Override
    public List<Account> findAllPatients(int pageNumber, int pageSize) {
        // Pobranie wszystkich użytkowników z rolą PACJENT
        TypedQuery<Account> query = entityManager.createQuery(
                "select a from Account a join a.roles r where r.name=:roleName and a.enabled=true order by a.id desc", Account.class);
        query.setParameter("roleName", Roles.PATIENT.getRoleNameWithPrefix());
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long countAllPatients() {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(a) from Account a join a.roles r where r.name=:roleName and a.enabled=true",Long.class);
        countQuery.setParameter("roleName", Roles.PATIENT.getRoleNameWithPrefix());
        return countQuery.getSingleResult();
    }

    @Override
    public List<Account> findPatientsByText(String textToSearch, int pageNumber, int pageSize) {
        TypedQuery<Account> query = entityManager.createQuery(
                "select a from Account a join a.roles r where (LOWER(a.userName) like :textToSearch OR LOWER(a.firstName) like :textToSearch OR LOWER(a.lastName) like :textToSearch OR LOWER(a.email) like :textToSearch) AND r.name=:roleName and a.enabled=true order by a.id desc", Account.class);
        query.setParameter("roleName", Roles.PATIENT.getRoleNameWithPrefix());
        query.setParameter("textToSearch", "%"+textToSearch.toLowerCase()+"%");

        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long countPatientsByText(String textToSearch) {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(a) from Account a join a.roles r where (LOWER(a.userName) like :textToSearch OR LOWER(a.firstName) like :textToSearch OR LOWER(a.lastName) like :textToSearch OR LOWER(a.email) like :textToSearch) AND r.name=:roleName and a.enabled=true",Long.class);
        countQuery.setParameter("roleName", Roles.PATIENT.getRoleNameWithPrefix());
        countQuery.setParameter("textToSearch", "%"+textToSearch.toLowerCase()+"%");
        return countQuery.getSingleResult();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej wszystkich aktywnych użytkowników z rolą DIETETYK.
     *
     * @return lista obiektów Account z rolą Dietetyk
     */
    @Override
    public List<Account> findAllDietitians(int pageNumber, int pageSize) {
        // Pobranie wszystkich użytkowników z rolą DIETETYK
        TypedQuery<Account> query = entityManager.createQuery(
                "select a from Account a join a.roles r where r.name=:roleName and a.enabled=true order by a.id desc", Account.class);
        query.setParameter("roleName", Roles.DIETITIAN.getRoleNameWithPrefix());
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public long countAllDietitians() {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(a) from Account a join a.roles r where r.name=:roleName and a.enabled=true",Long.class);
        countQuery.setParameter("roleName", Roles.DIETITIAN.getRoleNameWithPrefix());
        return countQuery.getSingleResult();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej aktywnych użytkowników z rolą DIETETYK dla filtra z paginacją.
     *
     * @return lista obiektów Account z rolą Dietetyk
     */
    @Override
    public List<Account> findDietitiansByText(String textToSearch, int pageNumber, int pageSize) {
        TypedQuery<Account> query = entityManager.createQuery(
                "select a from Account a join a.roles r where (LOWER(a.userName) like :textToSearch OR LOWER(a.firstName) like :textToSearch OR LOWER(a.lastName) like :textToSearch OR LOWER(a.email) like :textToSearch) AND r.name=:roleName and a.enabled=true order by a.id desc", Account.class);
        query.setParameter("roleName", Roles.DIETITIAN.getRoleNameWithPrefix());
        query.setParameter("textToSearch", "%"+textToSearch.toLowerCase()+"%");

        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej liczbę aktywnych użytkowników z rolą DIETETYK dla filtra.
     *
     * @return liczba obiektów Account z rolą Dietetyk dla filtra
     */
    @Override
    public long countDietitiansByText(String textToSearch) {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(a) from Account a join a.roles r where (LOWER(a.userName) like :textToSearch OR LOWER(a.firstName) like :textToSearch OR LOWER(a.lastName) like :textToSearch OR LOWER(a.email) like :textToSearch) AND r.name=:roleName and a.enabled=true",Long.class);
        countQuery.setParameter("roleName", Roles.DIETITIAN.getRoleNameWithPrefix());
        countQuery.setParameter("textToSearch", "%"+textToSearch.toLowerCase()+"%");
        return countQuery.getSingleResult();
    }
}
