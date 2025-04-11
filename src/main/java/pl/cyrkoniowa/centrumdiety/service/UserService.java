package pl.cyrkoniowa.centrumdiety.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import pl.cyrkoniowa.centrumdiety.entity.User;

public interface UserService extends UserDetailsService {
    public User findByUserName(String username);
}
