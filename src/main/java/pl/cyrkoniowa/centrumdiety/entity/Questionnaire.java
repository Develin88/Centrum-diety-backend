package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "questionnaire")
@Getter
@Setter
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="height")
    private int height;

//    @OneToOne(mappedBy = "ankieta")
//    private Pacjent pacjent;
}
