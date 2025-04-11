package pl.cyrkoniowa.centrumdiety.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="recipe")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="ingredients")
    private String ingredients;

    @Column(name="preparation_description")
    private String preparationDescription;

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
