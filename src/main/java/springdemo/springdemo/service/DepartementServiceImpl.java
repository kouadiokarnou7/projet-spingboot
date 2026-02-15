package springdemo.springdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springdemo.springdemo.model.Departement;
import springdemo.springdemo.repository.DepartementRepository;

@Service
@Transactional
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @Override
    public Departement save(Departement departement) {
        return departementRepository.save(departement);
    }

    @Override
    public List<Departement> findAll() {
        return departementRepository.findAll();
    }

    @Override
    public Departement findOne(Long id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département introuvable avec l'id : " + id));
    }

    @Override
    public Departement update(Long id, Departement departement) {

        Departement existingDept = findOne(id);
        existingDept.setNom(departement.getNom());

        if (departement.getEntreprise() != null) {
            existingDept.setEntreprise(departement.getEntreprise());
        }

        return departementRepository.save(existingDept);
    }
}
