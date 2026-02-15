package springdemo.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import springdemo.springdemo.model.Departement;
import springdemo.springdemo.model.Entreprise;
import springdemo.springdemo.repository.DepartementRepository;
import springdemo.springdemo.repository.EntrepriseRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private EntrepriseRepository entrepriseRepo;
    @Autowired private DepartementRepository deptRepo;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. On vérifie si l'entreprise existe déjà pour ne pas la recréer à chaque redémarrage
        if (entrepriseRepo.count() == 0) {
            
            // Création de l'entreprise unique
            Entreprise maBoite = new Entreprise("EvoCorp", "Abidjan, CIV");
            entrepriseRepo.save(maBoite);

            // 2. Création de plusieurs départements rattachés à cette même entreprise
            Departement it = new Departement(01L, "Informatique", maBoite);
            Departement rh = new Departement(02L, "Ressources Humaines", maBoite);
            

            // On les enregistre tous
            deptRepo.save(it);
            deptRepo.save(rh);
            

            System.out.println(">>> Structure de l'entreprise et départements initialisés !");
        }
    }
}