package pl.cyrkoniowa.centrumdiety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.cyrkoniowa.centrumdiety.entity.Pacjent;

@SpringBootApplication
@EnableJpaRepositories
public class CentrumDietyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CentrumDietyApplication.class, args);
    }
}
