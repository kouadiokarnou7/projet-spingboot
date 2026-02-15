package springdemo.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springdemo.springdemo.model.Departement;
import springdemo.springdemo.service.DepartementService;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    // --- ACCÈS PUBLIC / EMPLOYÉ ---

    @GetMapping
    public List<Departement> getAll() {
        return departementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departement> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(departementService.findOne(id));
    }

    // --- ACCÈS ADMIN UNIQUEMENT ---

    @PostMapping
    public Departement create(@RequestBody Departement departement) {
        return departementService.save(departement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departement> update(@PathVariable Long id, @RequestBody Departement departement) {
        return ResponseEntity.ok(departementService.update(id, departement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Si tu as ajouté la méthode delete dans ton service
        // departementService.delete(id); 
        return ResponseEntity.noContent().build();
    }
}