package pl.cyrkoniowa.centrumdiety.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="recipe")
@Getter
@Setter
public class    Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="ingredients")
    private String ingredients;

    @Column(name="preparation_description")
    private String preparationDescription;

    @Column(name="calories_amount")
    private int caloriesAmount;

    @Column(name="glycemic_load")
    private String glycemicLoad;

    @Column(name="protein_amount")
    private BigDecimal proteinAmount;

    @Column(name="fats_amount")
    private BigDecimal fatsAmount;

    @Column(name="carbs_amount")
    private BigDecimal carbsAmount;

    @Column(name="preparation_time")
    private int preparationTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<DietElement> dietElements = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredientsList = new ArrayList<>();

    @ManyToMany(mappedBy = "recipes")
    private List<Diet> diets = new ArrayList<>();
}
