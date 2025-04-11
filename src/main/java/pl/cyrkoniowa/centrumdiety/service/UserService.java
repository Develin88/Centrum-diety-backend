package pl.cyrkoniowa.centrumdiety.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.cyrkoniowa.centrumdiety.entity.Account;

public interface UserService extends UserDetailsService {
    Account findByUserName(String userName);
}
