package pl.cyrkoniowa.centrumdiety.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.dto.UserRegistrationDto;

import java.util.List;

/**
 * Serwis odpowiedzialny za operacje związane z kontami użytkowników.
 * Rozszerza UserDetailsService do obsługi uwierzytelniania Spring Security.
 */
public interface AccountService extends UserDetailsService {
    /**
     * Znajduje konto użytkownika na podstawie nazwy użytkownika.
     *
     * @param userName nazwa użytkownika do wyszukania
     * @return obiekt Account jeśli znaleziono, null w przeciwnym przypadku
     */
    Account findByUserName(String userName);

    /**
     * Zapisuje nowe konto użytkownika na podstawie danych z formularza rejestracji.
     *
     * @param userRegistrationDto obiekt zawierający dane rejestracyjne użytkownika
     */
    void save(UserRegistrationDto userRegistrationDto);

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

    /**
     * Promuje użytkownika z rolą Pacjent do roli Dietetyk.
     * Usuwa rolę Pacjent i dodaje rolę Dietetyk.
     *
     * @param userName nazwa użytkownika do promowania
     */
    void promotePatientToDietitian(String userName);

    /**
     * Degraduje użytkownika z rolą Dietetyk do roli Pacjent.
     * Usuwa rolę Dietetyk i dodaje rolę Pacjent.
     *
     * @param userName nazwa użytkownika do degradacji
     */
    void demoteDietitianToPatient(String userName);
}
