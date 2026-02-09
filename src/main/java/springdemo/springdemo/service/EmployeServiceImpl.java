package springdemo.springdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springdemo.springdemo.model.Employe;
import springdemo.springdemo.repository.EmployeRepository;

@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;

    public EmployeServiceImpl(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe findOne(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
    }

    @Override
    public Employe findByMatricule(Integer matricule) {
        return employeRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
    }

    @Override
    public Employe save(Employe employe) {
        // Sécurité : pas de doublon métier
        if (employeRepository.existsByMatricule(employe.getMatricule())) {
            throw new RuntimeException("Matricule déjà utilisé");
        }
        return employeRepository.save(employe);
    }

    @Override
    public Employe update(Long id, Employe employe) {
        Employe existing = findOne(id);

        existing.setNom(employe.getNom());
        existing.setMatricule(employe.getMatricule());

        return employeRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        employeRepository.deleteById(id);
    }
}
