package pl.cyrkoniowa.centrumdiety.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.cyrkoniowa.centrumdiety.entity.Pacjent;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "pacjenci", path = "pacjenci")
public interface PacjentRepository extends JpaRepository<Pacjent, Long> {
    Pacjent findByLogin(String login);

    List<Pacjent> findByLoginContaining(String fragment);
}
