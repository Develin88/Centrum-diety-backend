package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ankieta")
@Getter
@Setter
public class Ankieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="wzrost")
    private int wzrost;

//    @OneToOne(mappedBy = "ankieta")
//    private Pacjent pacjent;
}
