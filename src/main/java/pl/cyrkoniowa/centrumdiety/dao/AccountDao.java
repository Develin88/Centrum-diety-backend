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
    List<Account> findAllPatients(int pageNumber, int pageSize);

    /**
     * Zlicza ilość kont użytkowników z rolą Pacjent
     *
     * @return liczba obiektów Account z rolą Pacjent
     */
    long countAllPatients();

    /**
     * Pobiera listę kont użytkowników z rolą Pacjent spełniających filtr wraz ze stronicowaniem.
     *
     * @return lista obiektów Account z rolą Pacjent
     */
    List<Account> findPatientsByText(String textToSearch,int pageNumber, int pageSize);

    /**
     * Zlicza ilość kont użytkowników z rolą Pacjent spełniających filtr
     *
     * @return liczba obiektów Account z rolą Pacjent
     */
    long countPatientsByText(String textToSearch);

    /**
     * Pobiera listę wszystkich kont użytkowników z rolą Dietetyk.
     *
     * @return lista obiektów Account z rolą Dietetyk
     */
    List<Account> findAllDietitians(int pageNumber, int pageSize);

    /**
     * Zlicza ilość kont użytkowników z rolą Dietetyk
     *
     * @return liczba obiektów Account z rolą Dietetyk
     */
    long countAllDietitians();

    /**
     * Pobiera listę kont użytkowników z rolą Dietetyk spełniających filtr wraz ze stronicowaniem.
     *
     * @return lista obiektów Account z rolą Dietetyk
     */
    List<Account> findDietitiansByText(String textToSearch,int pageNumber, int pageSize);

    /**
     * Zlicza ilość kont użytkowników z rolą Dietetyk spełniających filtr
     *
     * @return liczba obiektów Account z rolą Dietetyk
     */
    long countDietitiansByText(String textToSearch);
}
