package pl.cyrkoniowa.centrumdiety.dao.impl;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.cyrkoniowa.centrumdiety.dao.RoleDao;
import pl.cyrkoniowa.centrumdiety.entity.Role;
import jakarta.persistence.EntityManager;

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    public RoleDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    public Role findRoleByName(String theRoleName) {
        //pobranie roli z bazy po nazwie
        TypedQuery<Role> theQuery = entityManager.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", theRoleName);
        Role theRole = null;
        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }
        return theRole;
    }

}
