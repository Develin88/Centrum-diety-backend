package pl.cyrkoniowa.centrumdiety.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="konsultacja")
@Getter
@Setter
public class Konsultacja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="termin_konsultacji")
    private LocalDateTime terminKonsultacji;

    @Column(name="temat")
    private String temat;

    @ManyToOne
    @JoinColumn(name = "pacjent_id", nullable = false)
    private Pacjent pacjent;

    @ManyToOne
    @JoinColumn(name = "dietetyk_id", nullable = false)
    private Dietetyk dietetyk;
}
