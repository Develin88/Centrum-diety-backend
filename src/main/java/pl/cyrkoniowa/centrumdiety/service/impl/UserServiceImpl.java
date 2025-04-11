package pl.cyrkoniowa.centrumdiety.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.cyrkoniowa.centrumdiety.dao.RoleDao;
import pl.cyrkoniowa.centrumdiety.dao.AccountDao;
import pl.cyrkoniowa.centrumdiety.entity.Role;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private AccountDao accountDao;

    private RoleDao roleDao;

    @Autowired
    public UserServiceImpl(AccountDao accountDao, RoleDao roleDao) {
        this.accountDao = accountDao;
        this.roleDao = roleDao;
    }

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

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public Account findByUserName(String userName) {
        return accountDao.findByUserName(userName);
    }
}
