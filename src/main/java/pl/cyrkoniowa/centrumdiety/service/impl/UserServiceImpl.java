package pl.cyrkoniowa.centrumdiety.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.cyrkoniowa.centrumdiety.dao.UserDao;
import pl.cyrkoniowa.centrumdiety.entity.Role;
import pl.cyrkoniowa.centrumdiety.entity.User;
import pl.cyrkoniowa.centrumdiety.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}
