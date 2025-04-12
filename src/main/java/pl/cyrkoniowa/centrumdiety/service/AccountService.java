package pl.cyrkoniowa.centrumdiety.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.user.WebUser;

public interface AccountService extends UserDetailsService {
    Account findByUserName(String userName);

    void save(WebUser webUser);
}
