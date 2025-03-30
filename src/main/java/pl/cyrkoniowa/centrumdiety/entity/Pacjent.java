package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pacjent")
@Getter
@Setter
public class Pacjent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="login")
    private String login;

    @Column(name="e_mail")
    private String e_mail;

    @Column(name="adres")
    private String adres;

    @Column(name="data_rejestracji")
    private LocalDateTime dataRejestracji;

    @Column(name="plec")
    private String plec;

    @Column(name="haslo")
    private String haslo;

    @Column(name="telefon")
    private String numerTelefonu;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ankieta_id", referencedColumnName = "id")
    private Ankieta ankieta;

    @OneToMany(mappedBy = "pacjent", cascade = CascadeType.ALL)
    private List<Konsultacja> konsultacje = new ArrayList<>();

    @OneToMany(mappedBy = "pacjent", cascade = CascadeType.ALL)
    private List<ElementDiety> elementyDiety = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pacjent_dieta",
            joinColumns = @JoinColumn(name = "pacjent_id"),
            inverseJoinColumns = @JoinColumn(name = "dieta_id")
    )
    private List<Dieta> diety = new ArrayList<>();
}

