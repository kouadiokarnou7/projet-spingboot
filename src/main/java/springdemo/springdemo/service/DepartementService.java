package springdemo.springdemo.service;
import java.util.List;
import springdemo.springdemo.model.Departement;


public interface  DepartementService {

         Departement save (Departement departement);
         List<Departement> findAll();
          Departement update (Long id , Departement departement ); 
          Departement findOne (Long id);
    
}
