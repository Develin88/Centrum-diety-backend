package pl.cyrkoniowa.centrumdiety.dao.impl;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.cyrkoniowa.centrumdiety.dao.RoleDao;
import pl.cyrkoniowa.centrumdiety.entity.Role;
import jakarta.persistence.EntityManager;

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    /**
     * Konstruktor klasy RoleDaoImpl.
     *
     * @param theEntityManager menedżer encji używany do operacji bazodanowych
     */
    public RoleDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej rolę z bazy danych na podstawie jej nazwy.
     *
     * @param theRoleName nazwa roli do znalezienia
     * @return obiekt Role jeśli znaleziono, null w przypadku błędu lub braku wyników
     */
    @Override
    public Role findRoleByName(String theRoleName) {
        //Pobranie roli z bazy po nazwie
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
