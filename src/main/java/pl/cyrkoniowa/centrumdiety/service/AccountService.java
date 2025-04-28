package pl.cyrkoniowa.centrumdiety.service;


import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.cyrkoniowa.centrumdiety.dto.AccountDto;
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
     * Pobiera listę kont użytkowników z rolą Pacjent spełniających filtr wraz ze stronicowaniem.
     *
     * @param textToSearch tekst do wyszukania
     * @param pageNumber numer strony
     * @param pageSize rozmiar strony
     * @return lista obiektów Account z rolą Pacjent
     */
    Page<AccountDto> findPatientsByText(String textToSearch, int pageNumber, int pageSize);

    /**
     * Pobiera listę kont użytkowników z rolą Dietetyk spełniających filtr wraz ze stronicowaniem.
     *
     * @param textToSearch tekst do wyszukania
     * @param pageNumber numer strony
     * @param pageSize rozmiar strony
     * @return lista obiektów Account z rolą Dietetyk
     */
    Page<AccountDto> findDietitiansByText(String textToSearch, int pageNumber, int pageSize);

    /**
     * Promuje użytkownika z rolą Pacjent do roli Dietetyk.
     * Usuwa rolę Pacjent i dodaje rolę Dietetyk.
     *
     * @param userNames nazwy użytkowników do promowania
     */
    void promotePatientsToDietitians(List<String> userNames);

    /**
     * Degraduje użytkowników z rolą Dietetyk do roli Pacjent.
     * Usuwa rolę Dietetyk i dodaje rolę Pacjent.
     *
     * @param userNames nazwy użytkowników do degradacji
     */
    void demoteDietitiansToPatients(List<String> userNames);
}
