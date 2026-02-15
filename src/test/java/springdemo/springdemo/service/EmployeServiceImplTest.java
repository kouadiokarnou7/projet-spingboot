package springdemo.springdemo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import springdemo.springdemo.model.Employe;
import springdemo.springdemo.repository.EmployeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeServiceImplTest {

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private EmployeServiceImpl employeService;

    private Employe e1;
    private Employe e2;

    @BeforeEach
    /* 
    void setUp() {
        e1 = new Employe();
        e1.setMatricule(1);
        e1.setNom("Alice");

        e2 = new Employe();
        e2.setMatricule(2);
        e2.setNom("Bob");
    }
        */

    @Test
    @DisplayName("findAll should return all employees")
    void findAll_returnsAll() {
        when(employeRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Employe> result = employeService.findAll();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getNom());
        verify(employeRepository).findAll();
    }

    @Test
    @DisplayName("findOne should return employee when found")
    void findOne_found() {
        when(employeRepository.findById(1L)).thenReturn(Optional.of(e1));

        Employe found = employeService.findOne(1L);

        assertEquals("Alice", found.getNom());
    }

    @Test
    @DisplayName("findOne should throw when not found")
    void findOne_notFound() {
        when(employeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeService.findOne(99L));
    }

    @Test
    @DisplayName("save should persist employee")
    void save_persists() {
        when(employeRepository.save(e1)).thenReturn(e1);

        Employe saved = employeService.save(e1);

        assertEquals("Alice", saved.getNom());
        verify(employeRepository).save(e1);
    }

    @Test
    @DisplayName("update should modify fields")
    void update_updatesAndPersists() {
        when(employeRepository.findById(1L)).thenReturn(Optional.of(e1));
        when(employeRepository.save(any(Employe.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Employe patch = new Employe();
        patch.setMatricule(5);
        patch.setNom("Alice Updated");

        Employe updated = employeService.update(1L, patch);

        assertEquals(5, updated.getMatricule());
        assertEquals("Alice Updated", updated.getNom());
        verify(employeRepository).save(e1);
    }

    @Test
    @DisplayName("delete should delete by id")
    void delete_deletes() {
        doNothing().when(employeRepository).deleteById(1L);

        employeService.delete(1L);

        verify(employeRepository).deleteById(1L);
    }
}
