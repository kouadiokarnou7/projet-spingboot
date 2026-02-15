package springdemo.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springdemo.springdemo.model.Entreprise;

@Repository

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}