package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToMany(mappedBy = "ingredientsList")
    private List<Recipe> recipes = new ArrayList<>();
}
