package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dieta")
@Getter
@Setter
public class Dieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nazwa")
    private String nazwa;

    @ManyToMany
    @JoinTable(
            name = "przepis_dieta",
            joinColumns = @JoinColumn(name = "dieta_id"),
            inverseJoinColumns = @JoinColumn(name = "przepis_id")
    )
    private List<Przepis> przepisy = new ArrayList<>();

    @ManyToMany(mappedBy = "diety")
    private List<Pacjent> pacjenci = new ArrayList<>();
}
