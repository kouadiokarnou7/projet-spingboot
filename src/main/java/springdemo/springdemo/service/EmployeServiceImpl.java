package springdemo.springdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdemo.springdemo.model.Employe;
import springdemo.springdemo.repository.EmployeRepository;


import java.util.List;

@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;

    public EmployeServiceImpl(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    // ACCÈS : EMPLOYÉ ET ADMIN
    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    // ACCÈS : EMPLOYÉ ET ADMIN (Recherche filtrée)
    @Override
    public List<Employe> searchByNom(String nom) {
        return employeRepository.findByNomContainingIgnoreCase(nom);
    }


    @Override
    public List <Employe> searchByPrenom(String prenom) {
        return employeRepository.findByPrenomContainingIgnoreCase(prenom);
    }

    @Override
    public List<Employe> searchByDepartementId(Long departementId) {
        return employeRepository.findByDepartementId(departementId);
    }

    @Override
    public Employe findOne(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable avec l'ID : " + id));
    }

    @Override
    public Employe findByMatricule(Integer matricule) {
        return employeRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Matricule " + matricule + " inexistant"));
    }

        
    // ACCÈS : ADMIN UNIQUEMENT (avec conditions)
    @Override
    public Employe save(Employe employe) {
        // CONDITION 1 : Vérifier si l'entreprise et le département sont fournis
        if (employe.getEntreprise() == null || employe.getDepartement() == null) {
            throw new RuntimeException("Erreur : Un employé doit être lié à une entreprise et un département.");
        }

        // CONDITION 2 : Vérifier si le matricule existe déjà en base
        if (employeRepository.existsByMatricule(employe.getMatricule())) {
            throw new RuntimeException("Le matricule " + employe.getMatricule() + " est déjà utilisé.");
        }

        return employeRepository.save(employe);
    }

    // ACCÈS : ADMIN UNIQUEMENT
    @Override
    public Employe update(Long id, Employe employe) {
        Employe existing = findOne(id);

        // Mise à jour de tous les champs selon le diagramme
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        existing.setFonction(employe.getFonction());
        existing.setMatricule(employe.getMatricule());
        
        // Mise à jour des relations (Clés étrangères)
        existing.setDepartement(employe.getDepartement());
        existing.setEntreprise(employe.getEntreprise());

        return employeRepository.save(existing);
    }

    // ACCÈS : ADMIN UNIQUEMENT
    @Override
    public void delete(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : l'employé n'existe pas.");
        }
        employeRepository.deleteById(id);
    }
}