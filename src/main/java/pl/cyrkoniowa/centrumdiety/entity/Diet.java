package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="diet")
@Getter
@Setter
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String nazwa;

    @ManyToMany
    @JoinTable(
            name = "recipe_diet",
            joinColumns = @JoinColumn(name = "diet_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipes = new ArrayList<>();

    @ManyToMany(mappedBy = "diets")
    private List<Patient> patients = new ArrayList<>();
}
