package pl.cyrkoniowa.centrumdiety.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cyrkoniowa.centrumdiety.dao.RoleDao;
import pl.cyrkoniowa.centrumdiety.dao.AccountDao;
import pl.cyrkoniowa.centrumdiety.dto.AccountDTO;
import pl.cyrkoniowa.centrumdiety.entity.Role;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.security.Roles;
import pl.cyrkoniowa.centrumdiety.service.AccountService;
import pl.cyrkoniowa.centrumdiety.dto.UserRegistrationDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementacja serwisu AccountService odpowiedzialnego za operacje na kontach użytkowników.
 * Obsługuje uwierzytelnianie, rejestrację, zarządzanie rolami i inne operacje na kontach.
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    private final RoleDao roleDao;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Konstruktor klasy AccountServiceImpl.
     *
     * @param accountDao obiekt dostępu do danych kont użytkowników
     * @param roleDao obiekt dostępu do danych ról
     * @param passwordEncoder enkoder haseł używany do szyfrowania haseł użytkowników
     */
    @Autowired
    public AccountServiceImpl(AccountDao accountDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.accountDao = accountDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Implementacja metody z interfejsu UserDetailsService.
     * Ładuje dane użytkownika na podstawie nazwy użytkownika dla procesu uwierzytelniania.
     *
     * @param userName nazwa użytkownika do wyszukania
     * @return obiekt UserDetails zawierający dane użytkownika potrzebne do uwierzytelnienia
     * @throws UsernameNotFoundException gdy użytkownik o podanej nazwie nie zostanie znaleziony
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account user = accountDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Metoda pomocnicza konwertująca kolekcję ról na kolekcję uprawnień Spring Security.
     *
     * @param roles kolekcja ról użytkownika
     * @return kolekcja obiektów GrantedAuthority reprezentujących uprawnienia użytkownika
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * Implementacja metody znajdującej konto użytkownika na podstawie nazwy użytkownika.
     *
     * @param userName nazwa użytkownika do wyszukania
     * @return obiekt Account jeśli znaleziono, null w przeciwnym przypadku
     */
    @Override
    public Account findByUserName(String userName) {
        return accountDao.findByUserName(userName);
    }

    /**
     * {@inheritDoc}
     * Implementacja metody zapisującej nowe konto użytkownika na podstawie danych z formularza rejestracji.
     * Tworzy nowe konto, ustawia dane użytkownika, szyfruje hasło i przypisuje domyślną rolę PATIENT.
     *
     * @param userRegistrationDto obiekt zawierający dane rejestracyjne użytkownika
     */
    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        Account account = new Account();

        // assign user details to the user object
        account.setUserName(userRegistrationDto.getUserName());
        account.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        account.setFirstName(userRegistrationDto.getFirstName());
        account.setLastName(userRegistrationDto.getLastName());
        account.setEmail(userRegistrationDto.getEmail());
        account.setEnabled(true);

        // give user default role of "employee"
        account.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_PATIENT")));

        // save user in the database
        accountDao.save(account);
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej listę kont użytkowników z rolą Pacjent spełniających filtr wraz ze stronicowaniem.
     *
     * @param textToSearch tekst do wyszukania
     * @param pageNumber numer strony
     * @param pageSize rozmiar strony
     * @return lista obiektów Account z rolą Pacjent
     */
    @Override
    public Page<AccountDTO> findPatientsByText(String textToSearch, int pageNumber, int pageSize) {
        List<AccountDTO> accountDtos;
        long totalRecords;

        if(textToSearch==null || textToSearch.isEmpty()){
            List<Account> users = accountDao.findAllPatients(pageNumber, pageSize);
            accountDtos = users.stream()
                    .map(AccountDTO::new)
                    .toList();
            totalRecords = accountDao.countAllPatients();
        }else{
            List<Account> users = accountDao.findPatientsByText(textToSearch, pageNumber, pageSize);
            accountDtos = users.stream()
                    .map(AccountDTO::new)
                    .toList();
            totalRecords = accountDao.countPatientsByText(textToSearch);
        }
        return new PageImpl<>(accountDtos, PageRequest.of(pageNumber, pageSize), totalRecords);
    }


    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej listę kont użytkowników z rolą Dietetyk spełniających filtr wraz ze stronicowaniem.
     *
     * @param textToSearch tekst do wyszukania
     * @param pageNumber numer strony
     * @param pageSize rozmiar strony
     * @return lista obiektów Account z rolą Dietetyk
     */
    @Override
    public Page<AccountDTO> findDietitiansByText(String textToSearch, int pageNumber, int pageSize) {
        List<AccountDTO> accountDtos;
        long totalRecords;

        if(textToSearch==null || textToSearch.isEmpty()){
            List<Account> accounts = accountDao.findAllDietitians(pageNumber, pageSize);
            accountDtos = accounts.stream()
                    .map(AccountDTO::new)
                    .toList();
            totalRecords = accountDao.countAllDietitians();
        }else{
            List<Account> accounts = accountDao.findDietitiansByText(textToSearch, pageNumber, pageSize);
            accountDtos = accounts.stream()
                    .map(AccountDTO::new)
                    .toList();
            totalRecords = accountDao.countDietitiansByText(textToSearch);
        }
        return new PageImpl<>(accountDtos, PageRequest.of(pageNumber, pageSize), totalRecords);
    }



    /**
     * {@inheritDoc}
     * Implementacja metody promującej użytkownika z rolą Pacjent do roli Dietetyk.
     * Usuwa rolę Pacjent i dodaje rolę Dietetyk dla wskazanego użytkownika.
     *
     * @param userName nazwa użytkownika do promowania
     */
    @Override
    @Transactional
    public void promotePatientToDietitian(String userName) {
        Account account = accountDao.findByUserName(userName);
        if (account != null) {
            // Sprawdzenie czy użytkownik na rolę Pacjent
            boolean isPatient = account.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(Roles.PATIENT.getRoleNameWithPrefix()));

            if (isPatient) {
                // Pobranie roli pacjent
                Role dietitianRole = roleDao.findRoleByName(Roles.DIETITIAN.getRoleNameWithPrefix());
                Role patientRole = roleDao.findRoleByName(Roles.PATIENT.getRoleNameWithPrefix());

                if (dietitianRole != null && patientRole != null) {
                    // Lista ról bez roli pacjent
                    List<Role> updatedRoles = account.getRoles().stream()
                            .filter(role -> !role.getName().equals(Roles.PATIENT.getRoleNameWithPrefix()))
                            .collect(Collectors.toList());

                    // Dodanie roli dietetyk
                    updatedRoles.add(dietitianRole);

                    // Aktualizacja ról dla użytkownika
                    account.setRoles(updatedRoles);

                    // Zapisanie użytkownika
                    accountDao.save(account);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * Implementacja metody degradującej użytkownika z rolą Dietetyk do roli Pacjent.
     * Usuwa rolę Dietetyk i dodaje rolę Pacjent dla wskazanego użytkownika.
     *
     * @param userName nazwa użytkownika do degradacji
     */
    @Override
    @Transactional
    public void demoteDietitianToPatient(String userName) {
        Account account = accountDao.findByUserName(userName);
        if (account != null) {
            // Check if the user has the DIETITIAN role
            boolean isDietitian = account.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(Roles.DIETITIAN.getRoleNameWithPrefix()));

            if (isDietitian) {
                // Get the PATIENT role
                Role patientRole = roleDao.findRoleByName(Roles.PATIENT.getRoleNameWithPrefix());

                if (patientRole != null) {
                    // Create a new collection with all roles except DIETITIAN
                    List<Role> updatedRoles = account.getRoles().stream()
                            .filter(role -> !role.getName().equals(Roles.DIETITIAN.getRoleNameWithPrefix()))
                            .collect(Collectors.toList());

                    // Add the PATIENT role
                    updatedRoles.add(patientRole);

                    // Update the account with the new roles
                    account.setRoles(updatedRoles);

                    // Save the updated account
                    accountDao.save(account);
                }
            }
        }
    }
}
