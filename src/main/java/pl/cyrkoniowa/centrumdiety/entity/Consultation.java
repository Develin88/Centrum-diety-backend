package pl.cyrkoniowa.centrumdiety.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="consultation")
@Getter
@Setter
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="consultation_date")
    private LocalDateTime terminKonsultacji;

    @Column(name="subject")
    private String subject;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id", nullable = false)
    private Dietitian dietitian;
}
