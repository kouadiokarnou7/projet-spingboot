package springdemo.springdemo.service;

import java.util.List;
import springdemo.springdemo.model.Employe;

public interface EmployeService {

    List<Employe> findAll();

    Employe findOne(Long id);

    Employe findByMatricule(Integer matricule);

    Employe save(Employe employe);

    Employe update(Long id, Employe employe);

    void delete(Long id);
}
