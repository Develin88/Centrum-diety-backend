package pl.cyrkoniowa.centrumdiety.dao;

import pl.cyrkoniowa.centrumdiety.entity.Account;
import java.util.List;

public interface AccountDao {
    /**
     * Znajduje konto użytkownika na podstawie nazwy użytkownika.
     *
     * @param userName nazwa użytkownika do wyszukania
     * @return obiekt Account jeśli znaleziono, null w przeciwnym przypadku
     */
    Account findByUserName(String userName);

    /**
     * Zapisuje konto użytkownika w bazie danych.
     *
     * @param account obiekt konta użytkownika do zapisania
     */
    void save(Account account);

    /**
     * Pobiera listę wszystkich kont użytkowników z rolą Pacjent.
     *
     * @return lista obiektów Account z rolą Pacjent
     */
    List<Account> findAllPatients();

    /**
     * Pobiera listę wszystkich kont użytkowników z rolą Dietetyk.
     *
     * @return lista obiektów Account z rolą Dietetyk
     */
    List<Account> findAllDietitians();
}
