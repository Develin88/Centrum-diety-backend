package pl.cyrkoniowa.centrumdiety.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="pacjent")
@Data
public class Pacjent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="login")
    private String login;
}
