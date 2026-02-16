package springdemo.springdemo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springdemo.springdemo.model.Employe;
import springdemo.springdemo.service.EmployeService;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public Employe findOne(@PathVariable Long id) {
        return employeService.findOne(id);
    }

    @GetMapping("/matricule/{matricule}")
    public Employe findByMatricule(@PathVariable Integer matricule) {
        return employeService.findByMatricule(matricule);
    }
    /* 
    @PostMapping
    public Employe create(@RequestBody Employe employe) {
        return employeService.save(employe);
    }

    @PutMapping("/{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe employe) {
        return employeService.update(id, employe);
    } */
        // --- AJOUT (CREATE) ---
    @PostMapping
    public ResponseEntity<Employe> create(@RequestBody Employe employe) {
        // On appelle save, qui vérifie que le matricule n'existe pas déjà
        Employe nouveauEmploye = employeService.save(employe);
        return ResponseEntity.ok(nouveauEmploye);
    }

    // --- MODIFICATION (UPDATE) ---
    @PutMapping("/{id}")
    public ResponseEntity<Employe> update(@PathVariable Long id, @RequestBody Employe employe) {
        // IMPORTANT : On appelle update, PAS save !
        // La méthode update en interne gère le fait que l'employé existe déjà
        Employe employeModifie = employeService.update(id, employe);
        return ResponseEntity.ok(employeModifie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeService.delete(id);
    }
}
