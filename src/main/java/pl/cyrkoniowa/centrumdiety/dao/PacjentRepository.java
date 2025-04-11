package pl.cyrkoniowa.centrumdiety.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.cyrkoniowa.centrumdiety.entity.Patient;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "pacjenci", path = "pacjenci")
public interface PacjentRepository extends JpaRepository<Patient, Long> {
    Patient findByLogin(String login);

    List<Patient> findByLoginContaining(String fragment);
}
