package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dietitian")
@Getter
@Setter
public class Dietitian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="e_mail")
    private String e_mail;

    @Column(name="specialization")
    private String specialization;

    @OneToMany(mappedBy = "dietitian", cascade = CascadeType.ALL)
    private List<Consultation> consultations = new ArrayList<>();
}
