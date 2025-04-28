package pl.cyrkoniowa.centrumdiety.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.cyrkoniowa.centrumdiety.dao.IngredientDao;
import pl.cyrkoniowa.centrumdiety.entity.Ingredient;

import java.util.List;

@Repository
public class IngredientDaoImpl implements IngredientDao {
    private final EntityManager entityManager;

    /**
     * Konstruktor klasy IngredientDaoImpl.
     *
     * @param theEntityManager menedżer encji używany do operacji bazodanowych
     */
    public IngredientDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej składniki.
     *
     * @return lista obiektów Ingredient
     */
    @Override
    public List<Ingredient> findAllIngredients(int pageNumber, int pageSize, String sortBy, String order) {
        StringBuilder queryString = new StringBuilder("select i from Ingredient i ");
        if (sortBy != null && !sortBy.isEmpty()) {
            queryString.append(" order by i.")
                    .append(sortBy)
                    .append(" ");
            if (order != null && order.equalsIgnoreCase("asc")) {
                queryString.append("asc");
            } else {
                queryString.append("desc");
            }
        } else {
            queryString.append(" order by i.id desc");
        }
        TypedQuery<Ingredient> query = entityManager.createQuery(queryString.toString(), Ingredient.class);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody zliczającej składniki.
     *
     * @return liczba obiektów Ingredient
     */
    @Override
    public long countAllIngredients() {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(i) from Ingredient i",Long.class);
        return countQuery.getSingleResult();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej składniki dla filtra z paginacją.
     *
     * @return lista obiektów Ingredient
     */
    @Override
    public List<Ingredient> findIngredientsByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order) {
        StringBuilder queryString = new StringBuilder("select i from Ingredient i where LOWER(i.name) like :textToSearch");
        if (sortBy != null && !sortBy.isEmpty()) {
            queryString.append(" order by i.")
                    .append(sortBy)
                    .append(" ");
            if (order != null && order.equalsIgnoreCase("asc")) {
                queryString.append("asc");
            } else {
                queryString.append("desc");
            }
        } else {
            queryString.append(" order by i.id desc");
        }
        TypedQuery<Ingredient> query = entityManager.createQuery(queryString.toString(), Ingredient.class);
        query.setParameter("textToSearch", "%" + textToSearch.toLowerCase() + "%");
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody zliczającej składniki dla filtra.
     *
     * @return ilość obiektów Ingredient
     */
    @Override
    public long countIngredientsByText(String textToSearch) {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(i) from Ingredient i  where LOWER(i.name) like :textToSearch",Long.class);
        countQuery.setParameter("textToSearch", "%"+textToSearch.toLowerCase()+"%");
        return countQuery.getSingleResult();
    }
}
