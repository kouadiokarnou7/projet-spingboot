package springdemo.springdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springdemo.springdemo.model.Employe;
import springdemo.springdemo.repository.EmployeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceImplTest {

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private EmployeServiceImpl employeService;

    private Employe e1;
    private Employe e2;

    @BeforeEach
    void setUp() {
        e1 = new Employe();
        e1.setMatricule(1);
        e1.setNom("Alice");
        e2 = new Employe();
        e2.setMatricule(2);
        e2.setNom("Bob");
    }

    @Test
    @DisplayName("findAll should return all employees from repository")
    void findAll_returnsAll() {
        when(employeRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Employe> result = employeService.findAll();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getNom());
        verify(employeRepository).findAll();
    }

    @Test
    @DisplayName("findOne should return employee when found, otherwise throw")
    void findOne_foundOrThrow() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(e1));

        Employe found = employeService.findOne(1);

        assertEquals("Alice", found.getNom());

        when(employeRepository.findById(99)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeService.findOne(99));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    @DisplayName("save should validate matricule not null and nom not empty")
    void save_validation() {
        Employe invalid1 = new Employe();
        invalid1.setNom("NoMatricule");
        invalid1.setMatricule(null);
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> employeService.save(invalid1));
        assertTrue(ex1.getMessage().contains("Matricule"));

        Employe invalid2 = new Employe();
        invalid2.setMatricule(10);
        invalid2.setNom("");
        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> employeService.save(invalid2));
        assertTrue(ex2.getMessage().contains("Nom"));
    }

    @Test
    @DisplayName("save should persist when valid")
    void save_persists() {
        when(employeRepository.save(e1)).thenReturn(e1);
        Employe saved = employeService.save(e1);
        assertEquals("Alice", saved.getNom());
        verify(employeRepository).save(e1);
    }

    @Test
    @DisplayName("update should copy provided fields and persist")
    void update_updatesAndPersists() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(e1));
        when(employeRepository.save(any(Employe.class))).thenAnswer(inv -> inv.getArgument(0));

        Employe patch = new Employe();
        patch.setMatricule(5);
        patch.setNom("Alice Updated");

        Employe updated = employeService.update(1, patch);

        assertEquals(5, updated.getmatricule());
        assertEquals("Alice Updated", updated.getNom());
        verify(employeRepository).save(e1);
    }

    @Test
    @DisplayName("delete should verify existence then delete")
    void delete_verifiesAndDeletes() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(e1));
        doNothing().when(employeRepository).deleteById(1);

        employeService.delete(1);

        verify(employeRepository).deleteById(1);
    }
}
