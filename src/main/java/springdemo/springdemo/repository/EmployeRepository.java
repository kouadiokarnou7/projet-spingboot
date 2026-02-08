package springdemo.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdemo.springdemo.model.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Integer> {
}
