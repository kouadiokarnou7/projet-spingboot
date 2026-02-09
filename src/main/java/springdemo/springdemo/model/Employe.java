package springdemo.springdemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employe")
public class Employe {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // La VRAIE clé primaire (auto-incrémentée)

    private Integer matricule; // Ton identifiant métier (celui que tu tapes : 300)
    private String nom;

    // Getters et setters corrigés
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMatricule() { return matricule; }
    public void setMatricule(Integer matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
}
