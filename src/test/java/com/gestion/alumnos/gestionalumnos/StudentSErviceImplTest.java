package com.gestion.alumnos.gestionalumnos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.gestion.alumnos.gestionalumnos.entities.Student;
import com.gestion.alumnos.gestionalumnos.repositories.StudentRepository;
import com.gestion.alumnos.gestionalumnos.services.StudentServiceImpl;

@SpringBootTest
public class StudentSErviceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testFindByFirstNameAndLastName(){
        Student student= new Student();

        student.setFirstName("Juan");
        student.setLastName("Perez");

        when(studentRepository.findByFirstNameAndLastName("Juan","Perez")).thenReturn(Optional.of(student));
        Optional<Student> foundStudent= studentService.findByFirstNameAndLastName("Juan", "Perez");
        assertTrue(foundStudent.isPresent());
        assertEquals("Juan", foundStudent.get().getFirstName());
        assertEquals("Perez", foundStudent.get().getLastName());
    }

    @Test
    void testSaveOrUpdateStudent(){
        Student student= new Student();
        student.setId(1L);
        student.setFirstName("Juan");
        student.setLastName("Perez");

        when(studentRepository.save(student)).thenReturn(student);

        Student saveStudent= studentService.saveOrUpdateStudent(student);
        assertEquals("Juan", saveStudent.getFirstName());
        assertEquals("Perez", saveStudent.getLastName());
    }
    
    @Test
    void testGetAllStudents(){
        Student student1= new Student();
        student1.setFirstName("Juan");
        student1.setLastName("Perez");

        Student student2= new Student();
        student2.setFirstName("Melisa");
        student2.setLastName("Rodriguez");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students= studentService.getAllStudents();
        assertEquals(2, students.size());
    }

    @Test
    void testGetStudentById(){
        Student student= new Student();
        student.setId(1L);
        student.setFirstName("Juan");
        student.setLastName("Perez");


        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> foundStudent= studentService.getStudentById(1L);
        assertTrue(foundStudent.isPresent());
        assertEquals("Juan", foundStudent.get().getFirstName());
        assertEquals("Perez", foundStudent.get().getLastName());
    }

    @Test
    void testDeleteStudent(){
        studentService.deleteStudent(1L);
        verify(studentRepository).deleteById(1L);
    }
}
