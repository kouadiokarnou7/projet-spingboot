package springdemo.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdemo.springdemo.model.Employe;

import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Optional<Employe> findByMatricule(Integer matricule);

    boolean existsByMatricule(Integer matricule);
}
