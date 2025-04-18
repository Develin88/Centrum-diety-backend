package pl.cyrkoniowa.centrumdiety.dao;

import pl.cyrkoniowa.centrumdiety.entity.Role;

public interface RoleDao {
    /**
     * Znajduje rolę na podstawie jej nazwy.
     *
     * @param theRoleName nazwa roli do znalezienia
     * @return obiekt Role jeśli znaleziono, null w przeciwnym przypadku
     */
    public Role findRoleByName(String theRoleName);

}
