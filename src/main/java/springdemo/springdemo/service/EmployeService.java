package springdemo.springdemo.service;

import java.util.List;

import springdemo.springdemo.model.Employe;

public interface EmployeService {
    
    List<Employe> findAll();
    
    Employe findOne(Integer id);
    
    Employe save(Employe employee);
    
    Employe update(Integer id, Employe employee);
    
    void delete(Integer id);
}