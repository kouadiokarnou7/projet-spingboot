package springdemo.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springdemo.springdemo.model.Employe;
import springdemo.springdemo.service.EmployeService;
import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public Employe findOne(@PathVariable Integer id) {
        return employeService.findOne(id);
    }

    @PostMapping
    public Employe create(@RequestBody Employe employe) {
        return employeService.save(employe);
    }

    @PutMapping("/{id}")
    public Employe update(@PathVariable Integer id, @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        employeService.delete(id);
    }
}