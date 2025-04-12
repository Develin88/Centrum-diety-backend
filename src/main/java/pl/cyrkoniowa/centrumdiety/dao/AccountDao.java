package pl.cyrkoniowa.centrumdiety.dao;

import pl.cyrkoniowa.centrumdiety.entity.Account;

public interface AccountDao {
    Account findByUserName(String userName);

    void save(Account account);
}
