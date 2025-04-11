package pl.cyrkoniowa.centrumdiety.dao;

import pl.cyrkoniowa.centrumdiety.entity.User;

public interface UserDao {
    User findByUserName(String userName);
}
