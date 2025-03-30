package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="element_diety")
@Getter
@Setter
public class ElementDiety {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="data")
    private LocalDateTime data;

    @Column(name="rodzaj_posilku")
    private String rodzajPosilku;

    @ManyToOne
    @JoinColumn(name = "pacjent_id", nullable = false)
    private Pacjent pacjent;

    @ManyToOne
    @JoinColumn(name = "przepis_id", nullable = false)
    private Przepis przepis;
}
