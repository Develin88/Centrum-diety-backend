package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ingredient")
@Getter
@Setter
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="calories_amount")
    private int caloriesAmount;

    @Column(name="glycemic_index")
    private BigDecimal glycemicIndex;

    @Column(name="protein_amount")
    private BigDecimal proteinAmount;

    @Column(name="fats_amount")
    private BigDecimal fatsAmount;

    @Column(name="carbs_amount")
    private BigDecimal carbsAmount;

    @ManyToMany(mappedBy = "ingredientsList")
    private List<Recipe> recipes = new ArrayList<>();
}
