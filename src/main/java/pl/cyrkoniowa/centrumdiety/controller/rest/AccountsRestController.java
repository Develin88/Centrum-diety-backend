package pl.cyrkoniowa.centrumdiety.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cyrkoniowa.centrumdiety.dto.AccountDTO;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.service.AccountService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/accounts")
public class AccountsRestController {
    private final AccountService accountService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Konstruktor klasy AccountsRestController.
     *
     * @param accountService serwis obsługujący operacje na kontach użytkowników
     */
    @Autowired
    public AccountsRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Pobranie użytkowników z paginacją oraz filtrowaniem.
     *
     * @param textToSearch tekst do filtrowania użytkowników
     * @param page numer strony
     * @param size rozmiar strony
     */
    @GetMapping("/findDietitians")
    public Page<AccountDTO> findDietitians(@RequestParam(required = false) String textToSearch,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return accountService.findDietitiansByText(textToSearch, page, size);
    }

    /**
     * Pobranie użytkowników z paginacją oraz filtrowaniem.
     *
     * @param textToSearch tekst do filtrowania użytkowników
     * @param page numer strony
     * @param size rozmiar strony
     */
    @GetMapping("/findPatients")
    public Page<AccountDTO> findPatients(@RequestParam(required = false) String textToSearch,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return accountService.findPatientsByText(textToSearch, page, size);
    }

    /**
     * Promuje użytkowników z roli Pacjenta na rolę Dietetyka
     *
     * @param userNames nazwy użytkownika do awansowania
     * @return Komunikat o powodzeniu operacji
     */
    @PostMapping("/promoteAccountsToDietitians")
    public String promoteAccountsToDietitian(@RequestBody List<String> userNames) {
        try {
            accountService.promotePatientsToDietitians(userNames);
//            redirectAttributes.addFlashAttribute("successMessage", "Użytkownik " + userName + " został pomyślnie promowany na dietetyka.");
        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Nie udało się promować użytkownika: " + e.getMessage());
        }
//        return "redirect:/admin-dashboard";
        return "Promoted accounts to Dietitian";
    }

    /**
     * Degraduje użytkowników z roli Dietetyka do roli Pacjenta
     *
     * @param userNames nazwy użytkowników osób do zdegradowania
     * @return przekierowanie do panelu administratora
     */
    @PostMapping("/demoteAccountsToPatients")
    public String demoteAccountsToPatients(@RequestBody List<String> userNames) {
        try {
            accountService.demoteDietitiansToPatients(userNames);
           // redirectAttributes.addFlashAttribute("successMessage", "Użytkownik " + userName + " został pomyślnie zdegradowany do pacjenta.");
        } catch (Exception e) {
           // redirectAttributes.addFlashAttribute("errorMessage", "Nie udało się zdegradować użytkownika: " + e.getMessage());
        }
        return "Demoted accounts to Patients";
    }

}
