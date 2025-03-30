package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dietetyk")
@Getter
@Setter
public class Dietetyk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="imie")
    private String imie;

    @Column(name="nazwisko")
    private String nazwisko;

    @Column(name="telefon")
    private String numerTelefonu;

    @Column(name="e_mail")
    private String e_mail;

    @Column(name="specjalizacja")
    private String specjalizacja;

    @OneToMany(mappedBy = "dietetyk", cascade = CascadeType.ALL)
    private List<Konsultacja> konsultacje = new ArrayList<>();
}
