package pl.cyrkoniowa.centrumdiety.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.cyrkoniowa.centrumdiety.dao.RoleDao;
import pl.cyrkoniowa.centrumdiety.dao.AccountDao;
import pl.cyrkoniowa.centrumdiety.entity.Role;
import pl.cyrkoniowa.centrumdiety.entity.Account;
import pl.cyrkoniowa.centrumdiety.service.AccountService;
import pl.cyrkoniowa.centrumdiety.dto.UserRegistrationDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    private RoleDao roleDao;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.accountDao = accountDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
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

}
