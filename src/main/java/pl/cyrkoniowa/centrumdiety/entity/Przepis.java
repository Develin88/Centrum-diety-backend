package pl.cyrkoniowa.centrumdiety.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="przepis")
@Getter
@Setter
public class Przepis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nazwa_przepisu")
    private String nazwaPrzepisu;

    @Column(name="skladniki")
    private String skladniki;

    @Column(name="opis_przygotowania")
    private String opisPrzygotowania;

    @OneToMany(mappedBy = "przepis", cascade = CascadeType.ALL)
    private List<ElementDiety> elementyDiety = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "przepis_skladnik",
            joinColumns = @JoinColumn(name = "przepis_id"),
            inverseJoinColumns = @JoinColumn(name = "skladnik_id")
    )
    private List<Skladnik> listaSkladnikow = new ArrayList<>();

    @ManyToMany(mappedBy = "przepisy")
    private List<Dieta> diety = new ArrayList<>();
}
