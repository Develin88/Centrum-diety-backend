package pl.cyrkoniowa.centrumdiety.dao;

import pl.cyrkoniowa.centrumdiety.entity.Role;

public interface RoleDao {
    public Role findRoleByName(String theRoleName);

}
