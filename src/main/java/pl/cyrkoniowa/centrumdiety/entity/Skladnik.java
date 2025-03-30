package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="skladnik")
@Getter
@Setter
public class Skladnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nazwa_skladnika")
    private String nazwaSkladnika;

    @ManyToMany(mappedBy = "listaSkladnikow")
    private List<Przepis> przepisy = new ArrayList<>();
}
