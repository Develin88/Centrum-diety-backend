package pl.cyrkoniowa.centrumdiety.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.cyrkoniowa.centrumdiety.dto.AccountDTO;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountsRestController {
    private final AccountService accountService;

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
     * @param accountService serwis obsługujący operacje na kontach użytkowników
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
     * @param accountService serwis obsługujący operacje na kontach użytkowników
     */
    @GetMapping("/findPatients")
    public Page<AccountDTO> findPatients(@RequestParam(required = false) String textToSearch,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return accountService.findPatientsByText(textToSearch, page, size);
    }

}
