package pl.cyrkoniowa.centrumdiety.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.cyrkoniowa.centrumdiety.dao.RecipeDao;
import pl.cyrkoniowa.centrumdiety.entity.Ingredient;
import pl.cyrkoniowa.centrumdiety.entity.Recipe;

import java.util.List;

@Repository
public class RecipeDaoImpl implements RecipeDao {
    private final EntityManager entityManager;

    /**
     * Konstruktor klasy RecipeDaoImpl.
     *
     * @param theEntityManager menedżer encji używany do operacji bazodanowych
     */
    public RecipeDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej przepisy.
     *
     * @return lista obiektów Recipe
     */
    @Override
    public List<Recipe> findAllRecipes(int pageNumber, int pageSize, String sortBy, String order) {
        StringBuilder queryString = new StringBuilder("select r from Recipe r ");
        if (sortBy != null && !sortBy.isEmpty()) {
            queryString.append(" order by r.")
                    .append(sortBy)
                    .append(" ");
            if (order != null && order.equalsIgnoreCase("asc")) {
                queryString.append("asc");
            } else {
                queryString.append("desc");
            }
        } else {
            queryString.append(" order by r.id desc");
        }
        TypedQuery<Recipe> query = entityManager.createQuery(queryString.toString(), Recipe.class);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody zliczającej przepisy.
     *
     * @return lista obiektów Recipe
     */
    @Override
    public long countAllRecipes() {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(r) from Recipe r",Long.class);
        return countQuery.getSingleResult();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody pobierającej przepisy dla filtra z paginacją.
     *
     * @return lista obiektów Recipe
     */
    @Override
    public List<Recipe> findRecipesByText(String textToSearch, int pageNumber, int pageSize, String sortBy, String order) {
        StringBuilder queryString = new StringBuilder("select r from Recipe r where LOWER(r.name) like :textToSearch");
        if (sortBy != null && !sortBy.isEmpty()) {
            queryString.append(" order by r.")
                    .append(sortBy)
                    .append(" ");
            if (order != null && order.equalsIgnoreCase("asc")) {
                queryString.append("asc");
            } else {
                queryString.append("desc");
            }
        } else {
            queryString.append(" order by r.id desc");
        }
        TypedQuery<Recipe> query = entityManager.createQuery(queryString.toString(), Recipe.class);
        query.setParameter("textToSearch", "%" + textToSearch.toLowerCase() + "%");
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody zliczającej przepisy dla filtra.
     *
     * @return ilość obiektów Recipe
     */
    @Override
    public long countRecipesByText(String textToSearch) {
        TypedQuery<Long> countQuery = entityManager.createQuery(
                "select count(r) from Recipe r  where LOWER(r.name) like :textToSearch",Long.class);
        countQuery.setParameter("textToSearch", "%"+textToSearch.toLowerCase()+"%");
        return countQuery.getSingleResult();
    }

    /**
     * {@inheritDoc}
     * Implementacja metody znajdującej przepis w bazie danych na podstawie nazwy.
     *
     * @return obiekt składnika Recipe
     */
    @Override
    public Recipe findRecipeByName(String name) {
        TypedQuery<Recipe> theQuery = entityManager.createQuery("from Recipe where name=:name", Recipe.class);
        theQuery.setParameter("name", name);
        Recipe recipe = null;
        try {
            recipe = theQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return recipe;
    }

    /**
     * {@inheritDoc}
     * Implementacja metody zapisującej przepis w bazie danych.
     * Używa operacji merge do aktualizacji istniejącego lub utworzenia nowego przepisu.
     *
     * @param recipe obiekt przepisu do zapisania
     */
    @Override
    @Transactional
    public void save(Recipe recipe) {
        entityManager.merge(recipe);
    }
}
