package springdemo.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdemo.springdemo.model.Departement;
import java.util.List;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
    // Utile pour lister les départements d'une boîte précise
    List<Departement> findByEntrepriseId(Long entrepriseId);
}