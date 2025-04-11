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
            name = "przepis_dieta",
            joinColumns = @JoinColumn(name = "dieta_id"),
            inverseJoinColumns = @JoinColumn(name = "przepis_id")
    )
    private List<Recipe> przepisy = new ArrayList<>();

    @ManyToMany(mappedBy = "diety")
    private List<Patient> pacjenci = new ArrayList<>();
}
