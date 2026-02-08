package springdemo.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.springdemo.model.Employe;
import springdemo.springdemo.repository.EmployeRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeServiceImpl implements EmployeService {
    
    @Autowired
    private EmployeRepository employeRepository;
    
    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }
    
    @Override
    public Employe findOne(Integer matricule) {
        Optional<Employe> employe = employeRepository.findById(matricule);
        return employe.orElseThrow(() -> 
            new RuntimeException("Employé not found with matricule: " + matricule)
        );
    }
    
    @Override
    public Employe save(Employe employe) {
        // Validation métier
        if (employe.getmatricule() == null) {
            throw new RuntimeException("Matricule is required");
        }
        if (employe.getNom() == null || employe.getNom().isEmpty()) {
            throw new RuntimeException("Nom is required");
        }
        
        // Note: Repository currently does not expose a findBymatricule method.
        // Uniqueness check would normally be done via a query method or unique constraint.
        return employeRepository.save(employe);
    }
    
    @Override
    public Employe update(Integer id, Employe employeDetails) {
        Employe employe = findOne(id);
        
        if (employeDetails.getmatricule() != null) {
            employe.setMatricule(employeDetails.getmatricule());
        }
        
        if (employeDetails.getNom() != null && !employeDetails.getNom().isEmpty()) {
            employe.setNom(employeDetails.getNom());
        }
        
        return employeRepository.save(employe);
    }
    
    @Override
    public void delete(Integer id) {
        findOne(id); // Vérifier que l'employé existe
        employeRepository.deleteById(id);
    }
}